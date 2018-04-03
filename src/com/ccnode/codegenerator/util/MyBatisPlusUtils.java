//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.pojo.DomainClassInfo;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MyBatisPlusUtils {
    public MyBatisPlusUtils() {
    }

    @Nullable
    public static String extractTableNameByAnnotation(DomainClassInfo domainClassInfo) {
        String tableName = null;
        PsiModifierList modifierList = domainClassInfo.getDomainClass().getModifierList();
        PsiAnnotation[] annotations;
        PsiAnnotation[] var4;
        int var5;
        int var6;
        PsiAnnotation annotation;
        PsiNameValuePair[] attributes;
        PsiNameValuePair[] var9;
        int var10;
        int var11;
        PsiNameValuePair attribute;
        PsiAnnotationMemberValue value;
        if (modifierList != null) {
            annotations = modifierList.getApplicableAnnotations();
            if (annotations.length > 0) {
                var4 = annotations;
                var5 = annotations.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    annotation = var4[var6];
                    if ("com.baomidou.mybatisplus.annotations.TableName".equals(annotation.getQualifiedName())) {
                        attributes = annotation.getParameterList().getAttributes();
                        var9 = attributes;
                        var10 = attributes.length;

                        for(var11 = 0; var11 < var10; ++var11) {
                            attribute = var9[var11];
                            if (attribute.getName() == null || "value".equals(attribute.getName())) {
                                value = attribute.getValue();
                                if (value != null && StringUtils.isNotBlank(value.getText()) && value.getText().startsWith("\"") && value.getText().endsWith("\"")) {
                                    return value.getText().substring(1, value.getText().length() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (modifierList != null) {
            annotations = modifierList.getApplicableAnnotations();
            if (annotations.length > 0) {
                var4 = annotations;
                var5 = annotations.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    annotation = var4[var6];
                    if ("javax.persistence.Table".equals(annotation.getQualifiedName())) {
                        attributes = annotation.getParameterList().getAttributes();
                        var9 = attributes;
                        var10 = attributes.length;

                        for(var11 = 0; var11 < var10; ++var11) {
                            attribute = var9[var11];
                            if (attribute.getName() == null || "name".equals(attribute.getName())) {
                                value = attribute.getValue();
                                if (value != null && StringUtils.isNotBlank(value.getText()) && value.getText().startsWith("\"") && value.getText().endsWith("\"")) {
                                    return value.getText().substring(1, value.getText().length() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return (String)tableName;
    }

    @Nullable
    static PsiClass getClassFromMyBatisPlus(PsiClass srcClass) {
        PsiClassType[] extendsListTypes = srcClass.getExtendsListTypes();
        PsiClassType[] var2 = extendsListTypes;
        int var3 = extendsListTypes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            PsiClassType extendsListType = var2[var4];
            if (Objects.equals(extendsListType.getClassName(), "BaseMapper") || extendsListType.getParameterCount() == 1) {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(extendsListType.getParameters()[0]);
                if (PsiClassUtil.canBeDomainClass(psiClass)) {
                    return psiClass;
                }
            }
        }

        return null;
    }
}
