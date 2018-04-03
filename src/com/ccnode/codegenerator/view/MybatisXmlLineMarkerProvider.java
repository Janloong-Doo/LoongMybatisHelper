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
import com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class MybatisXmlLineMarkerProvider extends RelatedItemLineMarkerProvider {
    private static Set<String> tagNameSet = new HashSet<String>() {
        {
            this.add("select");
            this.add("insert");
            this.add("update");
            this.add("delete");
        }
    };

    public MybatisXmlLineMarkerProvider() {
    }

    protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element == null) {
            $$$reportNull$$$0(0);
        }

        if (element instanceof XmlTag) {
            XmlTag theTag = (XmlTag)element;
            PsiFile psiFile = element.getContainingFile();
            if (psiFile instanceof XmlFile) {
                XmlFile xmlFile = (XmlFile)psiFile;
                if ("mapper".equals(xmlFile.getRootTag().getName())) {
                    XmlTag tag = (XmlTag)element;
                    if (tagNameSet.contains(tag.getName())) {
                        PsiElement findedMethod = MyPsiXmlUtils.findMethodOfXmlTag(tag);
                        if (findedMethod != null) {
                            result.add(NavigationGutterIconBuilder.create(IconUtils.mapperxmlIcon()).setAlignment(Alignment.CENTER).setTarget(findedMethod).setTooltipTitle("navigation to mapper class").createLineMarkerInfo(theTag.getAttribute("id")));
                        }
                    }
                }
            }
        }
    }
}
