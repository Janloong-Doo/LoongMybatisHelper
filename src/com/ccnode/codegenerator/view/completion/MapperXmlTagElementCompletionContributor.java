//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import com.ccnode.codegenerator.constants.MyBatisXmlConstants;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapperXmlTagElementCompletionContributor extends CompletionContributor {
    public MapperXmlTagElementCompletionContributor() {
    }

    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters == null) {
            $$$reportNull$$$0(0);
        }

        if (result == null) {
            $$$reportNull$$$0(1);
        }

        PsiElement element = parameters.getPosition();
        PsiElement parent = element.getParent();
        if (parent != null && parent instanceof XmlAttributeValue) {
            XmlAttributeValue attributeValue = (XmlAttributeValue)parent;
            PsiElement parent1 = attributeValue.getParent();
            if (parent1 instanceof XmlAttribute) {
                XmlAttribute attribute = (XmlAttribute)parent1;
                String name = attribute.getName();
                String startText = parameters.getOriginalPosition().getText();
                if (name.equals("property")) {
                    this.handleWithPropertyComplete(result, element, attribute, startText);
                } else if (name.equals("refid")) {
                    this.handleWithRefidComplete(parameters, result, attribute, startText);
                } else if (name.equals("resultMap")) {
                    this.handleWithResultMap(parameters, result, attribute, startText);
                } else if (name.equals("test")) {
                    this.hendleWithTestComplete(parameters, result, element, attribute, startText);
                } else if (name.equals("id")) {
                    this.handleWithMethodNameId(parameters, result, element, attribute, startText);
                } else if (name.equals("keyProperty")) {
                    this.handleWithKeyPropertyComplete(parameters, result, attribute, startText);
                }

            }
        }
    }

    private void handleWithKeyPropertyComplete(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result, XmlAttribute attribute, String startText) {
        if (parameters == null) {
            $$$reportNull$$$0(2);
        }

        if (result == null) {
            $$$reportNull$$$0(3);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (tag.getName().equals("insert") || tag.getName().equals("update")) {
                String tagId = tag.getAttributeValue("id");
                if (!StringUtils.isBlank(tagId)) {
                    XmlFile xmlFile = (XmlFile)parameters.getOriginalFile();
                    String namespace = MyPsiXmlUtils.findCurrentXmlFileNameSpace(xmlFile);
                    PsiClass namespaceClass = PsiClassUtil.findClassOfQuatifiedType(xmlFile, namespace);
                    if (namespaceClass != null) {
                        PsiMethod findMethod = PsiClassUtil.getClassMethodByMethodName(namespaceClass, tagId);
                        if (findMethod != null) {
                            List<String> lookUpResult = PsiClassUtil.extractMyBatisParam(findMethod);
                            Iterator var12 = lookUpResult.iterator();

                            while(true) {
                                String s;
                                do {
                                    if (!var12.hasNext()) {
                                        return;
                                    }

                                    s = (String)var12.next();
                                } while(!"\"".equals(startText) && !s.startsWith(startText));

                                result.addElement(LookupElementBuilder.create(s));
                            }
                        }
                    }
                }
            }
        }
    }

    private void hendleWithTestComplete(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result, PsiElement element, XmlAttribute attribute, String startText) {
        if (parameters == null) {
            $$$reportNull$$$0(4);
        }

        if (result == null) {
            $$$reportNull$$$0(5);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (tag.getName().equals("if")) {
                PsiFile originalFile = parameters.getOriginalFile();
                if (originalFile instanceof XmlFile) {
                    String lastWord = this.findLastWord(startText);
                    if (lastWord != null) {
                        XmlFile xmlFile = (XmlFile)originalFile;
                        XmlTag rootTag = xmlFile.getRootTag();
                        if (rootTag != null && rootTag.getName().equals("mapper")) {
                            String namespace = rootTag.getAttributeValue("namespace");
                            if (!StringUtils.isBlank(namespace)) {
                                PsiClass classOfQuatifiedType = PsiClassUtil.findClassOfQuatifiedType(element, namespace);
                                if (classOfQuatifiedType != null) {
                                    String interfaceMethodName = MyPsiXmlUtils.findCurrentElementIntefaceMethodName(element);
                                    if (!StringUtils.isBlank(interfaceMethodName)) {
                                        PsiMethod findedMethod = PsiClassUtil.getClassMethodByMethodName(classOfQuatifiedType, interfaceMethodName);
                                        if (findedMethod != null) {
                                            List<String> myBatisParams = PsiClassUtil.extractMyBatisParam(findedMethod);
                                            myBatisParams.add("null");
                                            myBatisParams.add("and");
                                            Iterator var16 = myBatisParams.iterator();

                                            while(var16.hasNext()) {
                                                String myBatisParam = (String)var16.next();
                                                if (myBatisParam.startsWith(lastWord)) {
                                                    result.addElement(LookupElementBuilder.create(startText + myBatisParam.substring(lastWord.length())));
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private String findLastWord(String startText) {
        if (StringUtils.isBlank(startText)) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();

            for(int i = startText.length() - 1; i >= 0 && (Character.isLetterOrDigit(startText.charAt(i)) || startText.charAt(i) == '.'); --i) {
                builder.append(startText.charAt(i));
            }

            String s = builder.toString();
            return s.length() == 0 ? null : StringUtils.reverse(s);
        }
    }

    private void handleWithMethodNameId(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result, PsiElement element, XmlAttribute attribute, String startText) {
        if (parameters == null) {
            $$$reportNull$$$0(6);
        }

        if (result == null) {
            $$$reportNull$$$0(7);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (MyBatisXmlConstants.mapperMethodSet.contains(tag.getName())) {
                PsiFile originalFile = parameters.getOriginalFile();
                if (originalFile instanceof XmlFile) {
                    XmlFile xmlFil = (XmlFile)originalFile;
                    XmlTag rootTag = xmlFil.getRootTag();
                    if (rootTag != null) {
                        String namespace = rootTag.getAttributeValue("namespace");
                        if (!StringUtils.isBlank(namespace)) {
                            PsiClass classOfQuatifiedType = PsiClassUtil.findClassOfQuatifiedType(element, namespace);
                            if (classOfQuatifiedType != null && classOfQuatifiedType.isInterface()) {
                                PsiMethod[] allMethods = classOfQuatifiedType.getMethods();
                                List<String> methodNames = Lists.newArrayList();
                                PsiMethod[] var14 = allMethods;
                                int var15 = allMethods.length;

                                for(int var16 = 0; var16 < var15; ++var16) {
                                    PsiMethod allMethod = var14[var16];
                                    methodNames.add(allMethod.getName());
                                }

                                Iterator var18 = methodNames.iterator();

                                while(true) {
                                    String methodName;
                                    do {
                                        if (!var18.hasNext()) {
                                            return;
                                        }

                                        methodName = (String)var18.next();
                                    } while(!"\"".equals(startText) && !methodName.startsWith(startText));

                                    result.addElement(LookupElementBuilder.create(methodName));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleWithResultMap(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result, XmlAttribute attribute, String startText) {
        if (parameters == null) {
            $$$reportNull$$$0(8);
        }

        if (result == null) {
            $$$reportNull$$$0(9);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (tag.getName().equals("select")) {
                PsiFile originalFile = parameters.getOriginalFile();
                if (originalFile instanceof XmlFile) {
                    XmlFile xmlFil = (XmlFile)originalFile;
                    XmlTag rootTag = xmlFil.getRootTag();
                    if (rootTag != null) {
                        XmlTag[] subTags = rootTag.getSubTags();
                        List<String> reslutMapIds = Lists.newArrayList();
                        XmlTag[] var11 = subTags;
                        int var12 = subTags.length;

                        for(int var13 = 0; var13 < var12; ++var13) {
                            XmlTag subTag = var11[var13];
                            if (subTag.getName().equals("resultMap")) {
                                String resultMapId = subTag.getAttributeValue("id");
                                if (StringUtils.isNotBlank(resultMapId)) {
                                    reslutMapIds.add(resultMapId);
                                }
                            }
                        }

                        Iterator var16 = reslutMapIds.iterator();

                        while(true) {
                            String resultMapId;
                            do {
                                if (!var16.hasNext()) {
                                    return;
                                }

                                resultMapId = (String)var16.next();
                            } while(!"\"".equals(startText) && !resultMapId.startsWith(startText));

                            result.addElement(LookupElementBuilder.create(resultMapId));
                        }
                    }
                }
            }
        }
    }

    private void handleWithRefidComplete(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result, XmlAttribute attribute, String startText) {
        if (parameters == null) {
            $$$reportNull$$$0(10);
        }

        if (result == null) {
            $$$reportNull$$$0(11);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (tag.getName().equals("include")) {
                PsiFile originalFile = parameters.getOriginalFile();
                if (originalFile instanceof XmlFile) {
                    XmlFile xmlFil = (XmlFile)originalFile;
                    XmlTag rootTag = xmlFil.getRootTag();
                    if (rootTag != null) {
                        XmlTag[] subTags = rootTag.getSubTags();
                        List<String> sqls = Lists.newArrayList();
                        XmlTag[] var11 = subTags;
                        int var12 = subTags.length;

                        for(int var13 = 0; var13 < var12; ++var13) {
                            XmlTag subTag = var11[var13];
                            if (subTag.getName().equals("sql")) {
                                String sqlId = subTag.getAttributeValue("id");
                                if (StringUtils.isNotBlank(sqlId)) {
                                    sqls.add(sqlId);
                                }
                            }
                        }

                        Iterator var16 = sqls.iterator();

                        while(true) {
                            String sql;
                            do {
                                if (!var16.hasNext()) {
                                    return;
                                }

                                sql = (String)var16.next();
                            } while(!"\"".equals(startText) && !sql.startsWith(startText));

                            result.addElement(LookupElementBuilder.create(sql));
                        }
                    }
                }
            }
        }
    }

    private void handleWithPropertyComplete(@NotNull CompletionResultSet result, PsiElement element, XmlAttribute attribute, String startText) {
        if (result == null) {
            $$$reportNull$$$0(12);
        }

        XmlTag tag = attribute.getParent();
        if (tag != null) {
            if (tag.getName().equals("result")) {
                PsiElement parent2 = tag.getParent();
                if (parent2 instanceof XmlTag) {
                    XmlTag resultMapTag = (XmlTag)parent2;
                    if (resultMapTag.getName().equals("resultMap")) {
                        String resultTypeValue = resultMapTag.getAttributeValue("type");
                        if (!StringUtils.isBlank(resultTypeValue)) {
                            PsiClass findedClass = PsiClassUtil.findClassOfQuatifiedType(element, resultTypeValue);
                            if (findedClass != null) {
                                List<String> props = PsiClassUtil.extractProps(findedClass);
                                Iterator var11 = props.iterator();

                                while(true) {
                                    String prop;
                                    do {
                                        if (!var11.hasNext()) {
                                            return;
                                        }

                                        prop = (String)var11.next();
                                    } while(!"\"".equals(startText) && !prop.startsWith(startText));

                                    result.addElement(LookupElementBuilder.create(prop));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
