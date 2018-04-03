//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiNonJavaFileReferenceProcessor;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class PsiSearchUtils {
    public PsiSearchUtils() {
    }

    @NotNull
    public static List<XmlFile> searchMapperXml(@NotNull Project project, Module moduleForPsiElement, final String srcClassQualifiedName) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }

        if (moduleForPsiElement == null) {
            ArrayList var10000 = new ArrayList();
            if (var10000 == null) {
                $$$reportNull$$$0(1);
            }

            return var10000;
        } else {
            PsiSearchHelper searchService = (PsiSearchHelper)ServiceManager.getService(project, PsiSearchHelper.class);
            final List<XmlFile> xmlFiles = new ArrayList();
            searchService.processUsagesInNonJavaFiles("mapper", new PsiNonJavaFileReferenceProcessor() {
                public boolean process(PsiFile file, int startOffset, int endOffset) {
                    if (file instanceof XmlFile) {
                        XmlFile xmlFile = (XmlFile)file;
                        if (xmlFile.getRootTag() != null) {
                            XmlAttribute namespace = xmlFile.getRootTag().getAttribute("namespace");
                            if (namespace != null && namespace.getValue().equals(srcClassQualifiedName)) {
                                xmlFiles.add(xmlFile);
                                return false;
                            }
                        }
                    }

                    return true;
                }
            }, GlobalSearchScope.moduleScope(moduleForPsiElement));
            if (xmlFiles == null) {
                $$$reportNull$$$0(2);
            }

            return xmlFiles;
        }
    }
}
