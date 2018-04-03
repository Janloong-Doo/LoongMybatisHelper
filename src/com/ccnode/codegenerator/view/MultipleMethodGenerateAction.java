//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.genmethodxml.GenMethodResult;
import com.ccnode.codegenerator.genmethodxml.GenMethodXmlInvoker;
import com.ccnode.codegenerator.log.Log;
import com.ccnode.codegenerator.log.LogFactory;
import com.ccnode.codegenerator.util.IconUtils;
import com.ccnode.codegenerator.validate.PPValidator;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.CodeInsightUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MultipleMethodGenerateAction extends AnAction {
    private static Log log = LogFactory.getLogger(MultipleMethodGenerateAction.class);

    public MultipleMethodGenerateAction() {
        super((String)null, (String)null, IconUtils.useSmall());
    }

    public void actionPerformed(AnActionEvent e) {
        boolean validate = PPValidator.validate(e.getProject());
        if (validate) {
            Stopwatch started = Stopwatch.createStarted();
            VirtualFileManager.getInstance().syncRefresh();
            ApplicationManager.getApplication().saveAll();
            Editor editor = (Editor)e.getData(PlatformDataKeys.EDITOR);
            Caret currentCaret = editor.getCaretModel().getCurrentCaret();
            if (currentCaret.getSelectionStart() != currentCaret.getSelectionEnd()) {
                Document document = editor.getDocument();
                String selectedText = currentCaret.getSelectedText();
                List<String> methodNames = extractMethodNames(selectedText);
                if (!methodNames.isEmpty()) {
                    PsiFile data = (PsiFile)e.getData(PlatformDataKeys.PSI_FILE);
                    GenMethodXmlInvoker genMethodXmlInvoker = new GenMethodXmlInvoker(methodNames, editor.getProject(), new TextRange(currentCaret.getSelectionStart(), currentCaret.getSelectionEnd()), data);
                    GenMethodResult result = genMethodXmlInvoker.invoke();
                    if (result != null) {
                        if (result.isHasCursor()) {
                            CodeInsightUtil.positionCursor(editor.getProject(), result.getCursorFile(), result.getCursorElement());
                        }

                        StringBuilder methodNameBuilder = new StringBuilder();
                        Iterator var13 = methodNames.iterator();

                        while(var13.hasNext()) {
                            String s = (String)var13.next();
                            methodNameBuilder.append(s + " ,");
                        }

                        methodNameBuilder.deleteCharAt(methodNameBuilder.length() - 1);
                        long elapsed = started.elapsed(TimeUnit.MILLISECONDS);
                        log.info("generate dao multiple xml use with time in mill second is:" + elapsed + " and the method name is:" + methodNameBuilder.toString() + " used database is:" + DatabaseComponenent.currentDatabase());
                    }
                }
            }
        }
    }

    @NotNull
    private static List<String> extractMethodNames(String selectedText) {
        List<String> methodList = Lists.newArrayList();
        String mm = "";

        for(int i = 0; i < selectedText.length(); ++i) {
            char c = selectedText.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                mm = mm + c;
            } else {
                if (mm.length() > 0) {
                    methodList.add(mm);
                }

                mm = "";
            }
        }

        if (StringUtils.isNotBlank(mm)) {
            methodList.add(mm);
        }

        if (methodList == null) {
            $$$reportNull$$$0(0);
        }

        return methodList;
    }

    public void update(AnActionEvent e) {
        PsiFile data = (PsiFile)e.getData(PlatformDataKeys.PSI_FILE);
        if (data != null && data.isWritable() && data instanceof PsiJavaFile) {
            Editor editor = (Editor)e.getData(PlatformDataKeys.EDITOR);
            String selectedText = editor.getCaretModel().getCurrentCaret().getSelectedText();
            if (StringUtils.isBlank(selectedText)) {
                e.getPresentation().setVisible(false);
            }

        } else {
            e.getPresentation().setVisible(false);
        }
    }
}
