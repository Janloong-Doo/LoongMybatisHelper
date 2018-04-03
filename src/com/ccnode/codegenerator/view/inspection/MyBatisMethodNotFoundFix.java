//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.inspection;

import com.ccnode.codegenerator.util.PsiClassUtil;
import com.ccnode.codegenerator.util.PsiDocumentUtils;
import com.ccnode.codegenerator.util.PsiSearchUtils;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.List;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class MyBatisMethodNotFoundFix implements LocalQuickFix {
    public MyBatisMethodNotFoundFix() {
    }

    @Nls
    @NotNull
    public String getFamilyName() {
        if ("create mybatis xml" == null) {
            $$$reportNull$$$0(0);
        }

        return "create mybatis xml";
    }

    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        if (project == null) {
            $$$reportNull$$$0(1);
        }

        if (descriptor == null) {
            $$$reportNull$$$0(2);
        }

        PsiElement psiElement = descriptor.getPsiElement();
        if (psiElement instanceof PsiMethod) {
            PsiMethod method = (PsiMethod)psiElement;
            PsiClass currentClass = (PsiClass)PsiTreeUtil.getParentOfType(method, PsiClass.class);
            String qualifiedName = currentClass.getQualifiedName();
            Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(method);
            List<XmlFile> xmlFiles = PsiSearchUtils.searchMapperXml(project, moduleForPsiElement, qualifiedName);
            if (xmlFiles.size() == 1) {
                XmlFile thexml = (XmlFile)xmlFiles.get(0);
                XmlTag rootTag = thexml.getRootTag();
                if (rootTag != null) {
                    rootTag.addSubTag(this.generateTag(rootTag, method), false);
                    PsiDocumentManager manager = PsiDocumentManager.getInstance(project);
                    Document document = manager.getDocument(thexml);
                    PsiDocumentUtils.commitAndSaveDocument(manager, document);
                    XmlTag[] subTags = rootTag.getSubTags();
                    XmlTag subTag = subTags[subTags.length - 1];
                    OpenFileDescriptor fileDescriptor = new OpenFileDescriptor(project, thexml.getVirtualFile(), subTag.getValue().getTextRange().getStartOffset() + 1);
                    FileEditorManager.getInstance(project).openTextEditor(fileDescriptor, true);
                }
            }
        }
    }

    private XmlTag generateTag(XmlTag rootTag, PsiMethod method) {
        XmlTag childTag = rootTag.createChildTag("select", "", "\n\n", false);
        childTag.setAttribute("id", method.getName());
        String value = PsiClassUtil.extractRealType(method.getReturnType());
        if (value != null) {
            childTag.setAttribute("resultType", value);
        }

        return childTag;
    }
}
