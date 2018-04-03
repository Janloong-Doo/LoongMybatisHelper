//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.dialog.MapperUtil;
import com.ccnode.codegenerator.reference.PsiJavaMethodReference;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MyBatisJavaToXmlPsiReferenceContributor extends PsiReferenceContributor {
    private static Set<String> tagNames = new HashSet<String>() {
        {
            this.add("update");
            this.add("insert");
            this.add("select");
            this.add("delete");
        }
    };

    public MyBatisJavaToXmlPsiReferenceContributor() {
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
                    if (!"id".equals(name)) {
                        var10000 = PsiReference.EMPTY_ARRAY;
                        if (PsiReference.EMPTY_ARRAY == null) {
                            $$$reportNull$$$0(3);
                        }

                        return var10000;
                    } else {
                        XmlTag selectTag = parent.getParent();
                        if (selectTag == null) {
                            var10000 = PsiReference.EMPTY_ARRAY;
                            if (PsiReference.EMPTY_ARRAY == null) {
                                $$$reportNull$$$0(4);
                            }

                            return var10000;
                        } else if (!MyBatisJavaToXmlPsiReferenceContributor.tagNames.contains(selectTag.getName())) {
                            var10000 = PsiReference.EMPTY_ARRAY;
                            if (PsiReference.EMPTY_ARRAY == null) {
                                $$$reportNull$$$0(5);
                            }

                            return var10000;
                        } else if (!(selectTag.getParent() instanceof XmlTag)) {
                            var10000 = PsiReference.EMPTY_ARRAY;
                            if (PsiReference.EMPTY_ARRAY == null) {
                                $$$reportNull$$$0(6);
                            }

                            return var10000;
                        } else {
                            XmlTag rootParent = (XmlTag)selectTag.getParent();
                            XmlAttribute namespaceAttribute = rootParent.getAttribute("namespace");
                            if (namespaceAttribute == null) {
                                var10000 = PsiReference.EMPTY_ARRAY;
                                if (PsiReference.EMPTY_ARRAY == null) {
                                    $$$reportNull$$$0(7);
                                }

                                return var10000;
                            } else {
                                String namespace = namespaceAttribute.getValue();
                                if (StringUtils.isEmpty(namespace)) {
                                    var10000 = PsiReference.EMPTY_ARRAY;
                                    if (PsiReference.EMPTY_ARRAY == null) {
                                        $$$reportNull$$$0(8);
                                    }

                                    return var10000;
                                } else {
                                    PsiShortNamesCache shortNamesCache = PsiShortNamesCache.getInstance(element.getProject());
                                    Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(element);
                                    if (moduleForPsiElement == null) {
                                        var10000 = PsiReference.EMPTY_ARRAY;
                                        if (PsiReference.EMPTY_ARRAY == null) {
                                            $$$reportNull$$$0(9);
                                        }

                                        return var10000;
                                    } else {
                                        PsiClass[] classesByName = shortNamesCache.getClassesByName(MapperUtil.extractClassShortName(namespace), GlobalSearchScope.moduleScope(moduleForPsiElement));
                                        PsiClass findedClass = null;
                                        PsiClass[] var14 = classesByName;
                                        int var15 = classesByName.length;

                                        for(int var16 = 0; var16 < var15; ++var16) {
                                            PsiClass psiClass = var14[var16];
                                            if (psiClass.getQualifiedName().equals(namespace)) {
                                                findedClass = psiClass;
                                                break;
                                            }
                                        }

                                        if (findedClass == null) {
                                            var10000 = PsiReference.EMPTY_ARRAY;
                                            if (PsiReference.EMPTY_ARRAY == null) {
                                                $$$reportNull$$$0(10);
                                            }

                                            return var10000;
                                        } else {
                                            String value = parent.getValue();
                                            PsiMethod findMethod = null;
                                            PsiMethod[] methods = findedClass.getMethods();
                                            PsiMethod[] var24 = methods;
                                            int var18 = methods.length;

                                            for(int var19 = 0; var19 < var18; ++var19) {
                                                PsiMethod method = var24[var19];
                                                if (method.getName().equals(value)) {
                                                    findMethod = method;
                                                }
                                            }

                                            if (findMethod != null) {
                                                var10000 = new PsiReference[]{new PsiJavaMethodReference((XmlAttributeValue)element, findMethod.getNameIdentifier())};
                                                if (var10000 == null) {
                                                    $$$reportNull$$$0(11);
                                                }

                                                return var10000;
                                            } else {
                                                var10000 = PsiReference.EMPTY_ARRAY;
                                                if (PsiReference.EMPTY_ARRAY == null) {
                                                    $$$reportNull$$$0(12);
                                                }

                                                return var10000;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
