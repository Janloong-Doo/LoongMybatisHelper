//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.util.IconUtils;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiNonJavaFileReferenceProcessor;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class MybatisJavaLineMarkerProvider extends RelatedItemLineMarkerProvider {
    public MybatisJavaLineMarkerProvider() {
    }

    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element == null) {
            $$$reportNull$$$0(0);
        }

        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod)element;
            PsiClass containingClass = method.getContainingClass();
            if (!containingClass.isInterface()) {
                return;
            }

            Project project = element.getProject();
            String qualifiedName = containingClass.getQualifiedName();
            PsiElement methodElement = null;
            Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(method);
            if (moduleForPsiElement == null) {
                return;
            }

            PsiFile[] filesByName = FilenameIndex.getFilesByName(project, containingClass.getName() + ".xml", GlobalSearchScope.moduleScope(moduleForPsiElement));
            if (filesByName.length == 0) {
                methodElement = this.handleWithFileNotFound(method, project, qualifiedName, result);
            } else {
                PsiFile[] var10 = filesByName;
                int var11 = filesByName.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    PsiFile file = var10[var12];
                    if (file instanceof XmlFile) {
                        XmlFile xmlFile = (XmlFile)file;
                        XmlAttribute namespace = xmlFile.getRootTag().getAttribute("namespace");
                        if (namespace != null && namespace.getValue().equals(qualifiedName)) {
                            PsiElement psiElement = MyPsiXmlUtils.findTagForMethodName(xmlFile, method.getName());
                            if (psiElement != null) {
                                methodElement = psiElement;
                                break;
                            }
                        }
                    }
                }

                if (methodElement == null) {
                    methodElement = this.handleWithFileNotFound(method, project, qualifiedName, result);
                }
            }

            if (methodElement != null) {
                result.add(NavigationGutterIconBuilder.create(IconUtils.mapperIcon()).setAlignment(Alignment.CENTER).setTarget(methodElement).setTooltipTitle("navigation to mapper xml").createLineMarkerInfo(method.getNameIdentifier()));
            }
        }

    }

    private PsiElement handleWithFileNotFound(@NotNull PsiMethod method, Project project, final String qualifiedName, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (method == null) {
            $$$reportNull$$$0(1);
        }

        PsiSearchHelper searchService = (PsiSearchHelper)ServiceManager.getService(project, PsiSearchHelper.class);
        final List<XmlFile> xmlFiles = new ArrayList();
        Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(method);
        if (moduleForPsiElement == null) {
            return null;
        } else {
            searchService.processUsagesInNonJavaFiles("mapper", new PsiNonJavaFileReferenceProcessor() {
                public boolean process(PsiFile file, int startOffset, int endOffset) {
                    if (file instanceof XmlFile) {
                        XmlFile xmlFile = (XmlFile)file;
                        if (xmlFile.getRootTag() != null) {
                            XmlAttribute namespace = xmlFile.getRootTag().getAttribute("namespace");
                            if (namespace != null && namespace.getValue().equals(qualifiedName)) {
                                xmlFiles.add(xmlFile);
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }, GlobalSearchScope.moduleScope(moduleForPsiElement));
            return xmlFiles.size() == 0 ? null : MyPsiXmlUtils.findTagForMethodName((XmlFile)xmlFiles.get(0), method.getName());
        }
    }
}
