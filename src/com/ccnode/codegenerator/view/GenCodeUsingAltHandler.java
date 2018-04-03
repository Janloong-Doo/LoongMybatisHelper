//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.database.ClassValidateResult;
import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.GenCodeDialog;
import com.ccnode.codegenerator.dialog.InsertDialogResult;
import com.ccnode.codegenerator.pojo.ClassInfo;
import com.ccnode.codegenerator.service.pojo.GenerateInsertCodeService;
import com.ccnode.codegenerator.validate.PPValidator;
import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.CodeInsightUtilBase;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;

public class GenCodeUsingAltHandler implements CodeInsightActionHandler {
    public GenCodeUsingAltHandler() {
    }

    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }

        if (editor == null) {
            $$$reportNull$$$0(1);
        }

        if (file == null) {
            $$$reportNull$$$0(2);
        }

        boolean validate = PPValidator.validate(project);
        if (validate) {
            if (CodeInsightUtilBase.prepareEditorForWrite(editor)) {
                if (FileDocumentManager.getInstance().requestWriting(editor.getDocument(), project)) {
                    PsiClass currentClass = OverrideImplementUtil.getContextClass(project, editor, file, false);
                    ClassValidateResult classValidateResult = DatabaseComponenent.currentHandler().getGenerateFileHandler().validateCurrentClass(currentClass);
                    if (!classValidateResult.getValid()) {
                        if (classValidateResult.getValid() != null && classValidateResult.getValidFields().size() != 0) {
                            List<PsiField> validFields1 = classValidateResult.getValidFields();
                            StringBuilder validBuilder = new StringBuilder();
                            validBuilder.append("\n the following are valid fields: ");
                            Iterator var9 = validFields1.iterator();

                            while(var9.hasNext()) {
                                PsiField psiField = (PsiField)var9.next();
                                validBuilder.append(psiField.getName() + ",");
                            }

                            int i = Messages.showOkCancelDialog(project, classValidateResult.getInvalidMessages() + validBuilder.toString() + "\n\n do you want just use with valid fields?", "some field not valid", (Icon)null);
                            if (i == 2) {
                                return;
                            }
                        } else {
                            Messages.showErrorDialog(project, classValidateResult.getInvalidMessages(), "validate fail");
                        }
                    }

                    VirtualFileManager.getInstance().syncRefresh();
                    ApplicationManager.getApplication().saveAll();
                    GenCodeDialog genCodeDialog = new GenCodeDialog(project, currentClass, classValidateResult.getValidFields());
                    boolean b = genCodeDialog.showAndGet();
                    if (b) {
                        ClassInfo info = new ClassInfo();
                        info.setQualifiedName(currentClass.getQualifiedName());
                        info.setName(currentClass.getName());
                        switch(genCodeDialog.getType()) {
                            case INSERT:
                                InsertDialogResult insertDialogResult = genCodeDialog.getInsertDialogResult();
                                insertDialogResult.setSrcClass(info);
                                GenerateInsertCodeService.generateInsert(insertDialogResult);
                                VirtualFileManager.getInstance().syncRefresh();
                                Messages.showMessageDialog(project, "generate files success", "success", Messages.getInformationIcon());
                                return;
                            case UPDATE:
                            default:
                        }
                    }
                }
            }
        }
    }

    public boolean startInWriteAction() {
        return false;
    }
}
