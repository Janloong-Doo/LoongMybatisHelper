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
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiNonJavaFileReferenceProcessor;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/** @deprecated */
@Deprecated
public class NewMybatisJavaToXmlMarkerProvider extends RelatedItemLineMarkerProvider {
    public NewMybatisJavaToXmlMarkerProvider() {
    }

    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element == null) {
            $$$reportNull$$$0(0);
        }

        if (element instanceof PsiClass) {
            PsiClass containingClass = (PsiClass)element;
            if (!containingClass.isInterface()) {
                return;
            }

            Project project = element.getProject();
            String qualifiedName = containingClass.getQualifiedName();
            XmlFile theXmlFile = null;
            PsiFile[] filesByName = PsiShortNamesCache.getInstance(project).getFilesByName(containingClass.getName() + ".xml");
            int var10;
            if (filesByName.length == 0) {
                theXmlFile = this.handleWithFileNotFound(containingClass, project, qualifiedName, result);
            } else {
                PsiFile[] var8 = filesByName;
                int var9 = filesByName.length;

                for(var10 = 0; var10 < var9; ++var10) {
                    PsiFile file = var8[var10];
                    if (file instanceof XmlFile) {
                        XmlFile xmlFile = (XmlFile)file;
                        XmlAttribute namespace = xmlFile.getRootTag().getAttribute("namespace");
                        if (namespace != null && namespace.getValue().equals(qualifiedName)) {
                            theXmlFile = xmlFile;
                        }
                    }
                }
            }

            if (theXmlFile != null) {
                result.add(NavigationGutterIconBuilder.create(IconUtils.useMyBatisIcon()).setAlignment(Alignment.CENTER).setTarget(theXmlFile).setTooltipTitle("navigation to mapper xml").createLineMarkerInfo(containingClass.getNameIdentifier()));
            }

            PsiMethod[] methods = containingClass.getMethods();
            PsiMethod[] var15 = methods;
            var10 = methods.length;

            for(int var16 = 0; var16 < var10; ++var16) {
                PsiMethod method = var15[var16];
                XmlTag tagForMethodName = MyPsiXmlUtils.findTagForMethodName(theXmlFile, method.getName());
                if (tagForMethodName != null) {
                    result.add(NavigationGutterIconBuilder.create(IconUtils.useMyBatisIcon()).setAlignment(Alignment.CENTER).setTarget(tagForMethodName).setTooltipTitle("navigation to mapper xml").createLineMarkerInfo(method.getNameIdentifier()));
                }
            }
        }

    }

    private XmlFile handleWithFileNotFound(PsiClass theClass, Project project, final String qualifiedName, Collection<? super RelatedItemLineMarkerInfo> result) {
        PsiSearchHelper searchService = (PsiSearchHelper)ServiceManager.getService(project, PsiSearchHelper.class);
        final List<XmlFile> xmlFiles = new ArrayList();
        Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(theClass);
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
            return xmlFiles.size() == 0 ? null : (XmlFile)xmlFiles.get(0);
        }
    }
}
