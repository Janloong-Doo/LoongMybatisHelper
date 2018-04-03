//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.inspection;

import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiSearchUtils;
import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MybatisMethodNotFindInXmlInspection extends BaseJavaLocalInspectionTool {
    private LocalQuickFix fix = new MyBatisMethodNotFoundFix();
    private static Set<String> mybatisAnnotations = new HashSet<String>() {
        {
            this.add("org.apache.ibatis.annotations.Select");
            this.add("org.apache.ibatis.annotations.Update");
            this.add("org.apache.ibatis.annotations.Delete");
            this.add("org.apache.ibatis.annotations.Insert");
        }
    };

    public MybatisMethodNotFindInXmlInspection() {
    }

    @Nullable
    public ProblemDescriptor[] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        if (method == null) {
            $$$reportNull$$$0(0);
        }

        if (manager == null) {
            $$$reportNull$$$0(1);
        }

        return new ProblemDescriptor[]{manager.createProblemDescriptor(method, "method not find in xml", this.fix, ProblemHighlightType.ERROR, true)};
    }

    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
        if (holder == null) {
            $$$reportNull$$$0(2);
        }

        if (session == null) {
            $$$reportNull$$$0(3);
        }

        JavaElementVisitor var10000 = new JavaElementVisitor() {
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                PsiClass currentClass = (PsiClass)PsiTreeUtil.getParentOfType(method, PsiClass.class);
                if (currentClass != null && currentClass.isInterface()) {
                    PsiModifierList modifierList;
                    if ("findByHeheAndUserName".equals(method.getName())) {
                        modifierList = method.getModifierList();
                    }

                    modifierList = method.getModifierList();
                    if (modifierList != null) {
                        PsiAnnotation[] annotations = modifierList.getApplicableAnnotations();
                        if (annotations.length > 0) {
                            PsiAnnotation[] var5 = annotations;
                            int var6 = annotations.length;

                            for(int var7 = 0; var7 < var6; ++var7) {
                                PsiAnnotation annotation = var5[var7];
                                if (MybatisMethodNotFindInXmlInspection.mybatisAnnotations.contains(annotation.getQualifiedName())) {
                                    return;
                                }
                            }
                        }
                    }

                    String qualifiedName = currentClass.getQualifiedName();
                    Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(method);
                    List<XmlFile> xmlFiles = PsiSearchUtils.searchMapperXml(holder.getProject(), moduleForPsiElement, qualifiedName);
                    if (xmlFiles.size() != 0 && xmlFiles.size() <= 1) {
                        XmlFile xmlFile = (XmlFile)xmlFiles.get(0);
                        PsiElement tagForMethodName = MyPsiXmlUtils.findTagForMethodName(xmlFile, method.getName());
                        if (tagForMethodName == null) {
                            holder.registerProblem(method, "mybatis xml not exist", ProblemHighlightType.ERROR, new LocalQuickFix[]{MybatisMethodNotFindInXmlInspection.this.fix});
                        }

                    }
                }
            }
        };
        if (var10000 == null) {
            $$$reportNull$$$0(4);
        }

        return var10000;
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    public String getDisplayName() {
        if ("check method has corresponding xml" == null) {
            $$$reportNull$$$0(5);
        }

        return "check method has corresponding xml";
    }

    @Nls
    @NotNull
    public String getGroupDisplayName() {
        if ("MybatisCodeHelperCheck" == null) {
            $$$reportNull$$$0(6);
        }

        return "MybatisCodeHelperCheck";
    }
}
