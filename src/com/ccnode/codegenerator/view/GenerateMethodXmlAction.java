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
import com.ccnode.codegenerator.util.MethodNameUtil;
import com.ccnode.codegenerator.util.PsiElementUtil;
import com.ccnode.codegenerator.validate.PPValidator;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.CodeInsightUtil;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiAnonymousClass;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.IncorrectOperationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class GenerateMethodXmlAction extends PsiElementBaseIntentionAction {
    public static final String GENERATE_DAOXML = "generate daoxml";
    public static final String INSERT_INTO = "insert into";
    private static Log log = LogFactory.getLogger(GenerateMethodXmlAction.class);

    public GenerateMethodXmlAction() {
    }

    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        if (project == null) {
            $$$reportNull$$$0(0);
        }

        if (element == null) {
            $$$reportNull$$$0(1);
        }

        boolean validate = PPValidator.validate(project);
        if (validate) {
            Stopwatch started = Stopwatch.createStarted();
            VirtualFileManager.getInstance().syncRefresh();
            ApplicationManager.getApplication().saveAll();
            PsiElement parent = element.getParent();
            TextRange textRange = null;
            List<String> methodName = Lists.newArrayList();
            if (!(parent instanceof PsiMethod)) {
                if (parent instanceof PsiJavaCodeReferenceElement) {
                    String text = parent.getText();
                    methodName.add(text);
                    textRange = parent.getTextRange();
                } else if (element instanceof PsiWhiteSpace) {
                    PsiElement lastMatchedElement = this.findLastMatchedElement(element);
                    String text = lastMatchedElement.getText();
                    textRange = lastMatchedElement.getTextRange();
                    methodName.add(text);
                }

                GenMethodResult result = (new GenMethodXmlInvoker(methodName, project, textRange, element)).invoke();
                if (result != null) {
                    if (result.isHasCursor()) {
                        CodeInsightUtil.positionCursor(project, result.getCursorFile(), result.getCursorElement());
                    }

                    StringBuilder methodNameBuilder = new StringBuilder();
                    Iterator var11 = methodName.iterator();

                    while(var11.hasNext()) {
                        String s = (String)var11.next();
                        methodNameBuilder.append(s + " ,");
                    }

                    methodNameBuilder.deleteCharAt(methodNameBuilder.length() - 1);
                    long elapsed = started.elapsed(TimeUnit.MILLISECONDS);
                    log.info("generate dao xml use with time in mill second is:" + elapsed + " and the method name is:" + methodNameBuilder.toString() + " used database is:" + DatabaseComponenent.currentDatabase());
                }
            }
        }
    }

    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (project == null) {
            $$$reportNull$$$0(2);
        }

        if (element == null) {
            $$$reportNull$$$0(3);
        }

        if (!isAvailbleForElement(element)) {
            return false;
        } else {
            PsiClass containingClass = PsiElementUtil.getContainingClass(element);

            assert containingClass != null;

            PsiElement leftBrace = containingClass.getLBrace();
            if (leftBrace == null) {
                return false;
            } else if (element instanceof PsiMethod) {
                return false;
            } else {
                PsiElement parent = element.getParent();
                if (parent instanceof PsiMethod) {
                    return false;
                } else if (element instanceof PsiWhiteSpace) {
                    PsiElement element1 = this.findLastMatchedElement(element);
                    return element1 != null;
                } else {
                    if (parent instanceof PsiJavaCodeReferenceElement) {
                        PsiJavaCodeReferenceElement referenceElement = (PsiJavaCodeReferenceElement)parent;
                        String text = referenceElement.getText().toLowerCase();
                        if (MethodNameUtil.checkValidTextStarter(text).isValid()) {
                            return true;
                        }
                    }

                    return false;
                }
            }
        }
    }

    private PsiElement findLastMatchedElement(PsiElement element) {
        PsiElement prevSibling;
        for(prevSibling = element.getPrevSibling(); prevSibling != null && this.isIgnoreText(prevSibling.getText()); prevSibling = prevSibling.getPrevSibling()) {
            ;
        }

        if (prevSibling != null) {
            String lowerCase = prevSibling.getText().toLowerCase();
            if (MethodNameUtil.checkValidTextStarter(lowerCase).isValid()) {
                return prevSibling;
            }
        }

        return null;
    }

    private boolean isIgnoreText(String text) {
        return "".equals(text) || "\n".equals(text) || " ".equals(text);
    }

    @NotNull
    public String getText() {
        if ("generate daoxml" == null) {
            $$$reportNull$$$0(4);
        }

        return "generate daoxml";
    }

    public static boolean isAvailbleForElement(PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        } else {
            PsiClass containingClass = PsiElementUtil.getContainingClass(psiElement);
            if (containingClass == null) {
                return false;
            } else {
                Module srcMoudle = ModuleUtilCore.findModuleForPsiElement(containingClass);
                if (srcMoudle == null) {
                    return false;
                } else {
                    return !containingClass.isAnnotationType() && !(containingClass instanceof PsiAnonymousClass) && containingClass.isInterface();
                }
            }
        }
    }

    @Nls
    @NotNull
    public String getFamilyName() {
        if ("generate daoxml" == null) {
            $$$reportNull$$$0(5);
        }

        return "generate daoxml";
    }
}
