//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.inspection;

import com.ccnode.codegenerator.constants.MyBatisXmlConstants;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInsight.FileModificationService;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.htmlInspections.HtmlLocalInspectionTool;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class MybatisXmlNotUsedUsedInspection extends HtmlLocalInspectionTool {
    public MybatisXmlNotUsedUsedInspection() {
    }

    @Nls
    @NotNull
    public String getGroupDisplayName() {
        if ("MybatisCodeHelperCheck" == null) {
            $$$reportNull$$$0(0);
        }

        return "MybatisCodeHelperCheck";
    }

    @Nls
    @NotNull
    public String getDisplayName() {
        if ("check ununsed mybatis xml" == null) {
            $$$reportNull$$$0(1);
        }

        return "check ununsed mybatis xml";
    }

    @NonNls
    @NotNull
    public String getShortName() {
        if ("unUsedXml" == null) {
            $$$reportNull$$$0(2);
        }

        return "unUsedXml";
    }

    @NotNull
    public HighlightDisplayLevel getDefaultLevel() {
        HighlightDisplayLevel var10000 = HighlightDisplayLevel.ERROR;
        if (HighlightDisplayLevel.ERROR == null) {
            $$$reportNull$$$0(3);
        }

        return var10000;
    }

    protected void checkTag(@NotNull XmlTag tag, @NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (tag == null) {
            $$$reportNull$$$0(4);
        }

        if (holder == null) {
            $$$reportNull$$$0(5);
        }

        if (MyBatisXmlConstants.mapperMethodSet.contains(tag.getName())) {
            PsiMethod methodOfXmlTag = MyPsiXmlUtils.findMethodOfXmlTag(tag);
            if (methodOfXmlTag == null) {
                LocalQuickFix localQuickFix = new MybatisXmlNotUsedUsedInspection.MyLocalQuickFix();
                holder.registerProblem(tag, "mybatis xml is unused", ProblemHighlightType.LIKE_UNKNOWN_SYMBOL, new LocalQuickFix[]{localQuickFix});
            }

        }
    }

    private static class MyLocalQuickFix implements LocalQuickFix {
        private MyLocalQuickFix() {
        }

        @NotNull
        public String getFamilyName() {
            if ("delete current xml" == null) {
                $$$reportNull$$$0(0);
            }

            return "delete current xml";
        }

        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            if (project == null) {
                $$$reportNull$$$0(1);
            }

            if (descriptor == null) {
                $$$reportNull$$$0(2);
            }

            PsiElement psiElement = descriptor.getPsiElement();
            if (!(psiElement instanceof XmlTag)) {
                Messages.showErrorDialog("current element is not xml tag", "mybatis xml fix error");
            } else {
                final XmlTag myTag = (XmlTag)psiElement;
                if (FileModificationService.getInstance().prepareFileForWrite(myTag.getContainingFile())) {
                    (new WriteCommandAction(project, new PsiFile[0]) {
                        protected void run(@NotNull Result result) throws Throwable {
                            if (result == null) {
                                $$$reportNull$$$0(0);
                            }

                            myTag.delete();
                        }
                    }).execute();
                }
            }
        }
    }
}
