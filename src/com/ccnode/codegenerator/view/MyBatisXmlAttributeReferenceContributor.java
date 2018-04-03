//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.reference.PsiResultMapSqlReference;
import com.ccnode.codegenerator.reference.PsiXmlAttributeReference;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MyBatisXmlAttributeReferenceContributor extends PsiReferenceContributor {
    private static Map<String, String> refTagNameToDeclarationTagName = new HashMap<String, String>() {
        {
            this.put("resultMap", "resultMap");
            this.put("refid", "sql");
        }
    };
    private static Map<String, String> declareTagNameToUsageTagName = new HashMap<String, String>() {
        {
            this.put("resultMap", "select");
            this.put("sql", "include");
        }
    };
    private static Map<String, String> tagNameToAttributeName = new HashMap<String, String>() {
        {
            this.put("resultMap", "resultMap");
            this.put("sql", "refid");
        }
    };

    public MyBatisXmlAttributeReferenceContributor() {
    }

    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        if (registrar == null) {
            $$$reportNull$$$0(0);
        }

        registrar.registerReferenceProvider(XmlPatterns.xmlAttributeValue(), new PsiReferenceProvider() {
            @NotNull
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                if (element == null) {
                    $$$reportNull$$$0(0);
                }

                if (context == null) {
                    $$$reportNull$$$0(1);
                }

                PsiElement parent1 = element.getParent();
                PsiReference[] var10000;
                if (!(parent1 instanceof XmlAttribute)) {
                    var10000 = PsiReference.EMPTY_ARRAY;
                    if (PsiReference.EMPTY_ARRAY == null) {
                        $$$reportNull$$$0(2);
                    }

                    return var10000;
                } else {
                    XmlAttribute parent = (XmlAttribute)parent1;
                    String name = parent.getName();
                    XmlTag parentTag;
                    if (!MyBatisXmlAttributeReferenceContributor.refTagNameToDeclarationTagName.containsKey(name)) {
                        if ("id".equals(name)) {
                            parentTag = parent.getParent();
                            if (parentTag == null) {
                                var10000 = PsiReference.EMPTY_ARRAY;
                                if (PsiReference.EMPTY_ARRAY == null) {
                                    $$$reportNull$$$0(8);
                                }

                                return var10000;
                            } else if (!MyBatisXmlAttributeReferenceContributor.declareTagNameToUsageTagName.containsKey(parentTag.getName())) {
                                var10000 = PsiReference.EMPTY_ARRAY;
                                if (PsiReference.EMPTY_ARRAY == null) {
                                    $$$reportNull$$$0(9);
                                }

                                return var10000;
                            } else {
                                String tagIdValue = parent.getValue();
                                if (StringUtils.isBlank(tagIdValue)) {
                                    var10000 = PsiReference.EMPTY_ARRAY;
                                    if (PsiReference.EMPTY_ARRAY == null) {
                                        $$$reportNull$$$0(10);
                                    }

                                    return var10000;
                                } else {
                                    var10000 = new PsiReference[]{new PsiResultMapSqlReference((XmlAttributeValue)element, (XmlAttributeValue)element)};
                                    if (var10000 == null) {
                                        $$$reportNull$$$0(11);
                                    }

                                    return var10000;
                                }
                            }
                        } else {
                            var10000 = PsiReference.EMPTY_ARRAY;
                            if (PsiReference.EMPTY_ARRAY == null) {
                                $$$reportNull$$$0(12);
                            }

                            return var10000;
                        }
                    } else {
                        parentTag = parent.getParent();
                        XmlDocument xmlDocument = (XmlDocument)PsiTreeUtil.getParentOfType(parentTag, XmlDocument.class);
                        if (xmlDocument != null && xmlDocument.getRootTag() != null) {
                            XmlTag[] subTags = xmlDocument.getRootTag().getSubTags();
                            if (subTags.length == 0) {
                                var10000 = PsiReference.EMPTY_ARRAY;
                                if (PsiReference.EMPTY_ARRAY == null) {
                                    $$$reportNull$$$0(4);
                                }

                                return var10000;
                            } else {
                                String value = parent.getValue();
                                if (StringUtils.isBlank(value)) {
                                    var10000 = PsiReference.EMPTY_ARRAY;
                                    if (PsiReference.EMPTY_ARRAY == null) {
                                        $$$reportNull$$$0(5);
                                    }

                                    return var10000;
                                } else {
                                    XmlTag findSubTag = null;
                                    XmlTag[] var11 = subTags;
                                    int var12 = subTags.length;

                                    for(int var13 = 0; var13 < var12; ++var13) {
                                        XmlTag subTag = var11[var13];
                                        if (subTag.getName().equals(MyBatisXmlAttributeReferenceContributor.refTagNameToDeclarationTagName.get(name))) {
                                            String subTagValue = subTag.getAttributeValue("id");
                                            if (subTagValue != null && subTagValue.equals(value)) {
                                                findSubTag = subTag;
                                                break;
                                            }
                                        }
                                    }

                                    if (findSubTag == null) {
                                        var10000 = PsiReference.EMPTY_ARRAY;
                                        if (PsiReference.EMPTY_ARRAY == null) {
                                            $$$reportNull$$$0(6);
                                        }

                                        return var10000;
                                    } else {
                                        var10000 = new PsiReference[]{new PsiXmlAttributeReference((XmlAttributeValue)element, findSubTag.getAttribute("id").getValueElement())};
                                        if (var10000 == null) {
                                            $$$reportNull$$$0(7);
                                        }

                                        return var10000;
                                    }
                                }
                            }
                        } else {
                            var10000 = PsiReference.EMPTY_ARRAY;
                            if (PsiReference.EMPTY_ARRAY == null) {
                                $$$reportNull$$$0(3);
                            }

                            return var10000;
                        }
                    }
                }
            }
        });
    }
}
