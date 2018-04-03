//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.database.handler.mysql.MysqlHandlerUtils;
import com.ccnode.codegenerator.dialog.MapperUtil;
import com.ccnode.codegenerator.dialog.datatype.ClassFieldInfo;
import com.ccnode.codegenerator.pojo.DomainClassInfo;
import com.ccnode.codegenerator.pojo.DomainClassSourceType;
import com.ccnode.codegenerator.pojo.PropWithType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToIntFunction;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiClassUtil {
    public static final String PARAM = "@Param(\"";
    public static final String MYBATISPLUS_BASEMAPPER = "BaseMapper";
    private static Map<String, String> primitiveToObjectMap = new HashMap<String, String>() {
        {
            this.put("int", "java.lang.Integer");
            this.put("short", "java.lang.Short");
            this.put("long", "java.lang.Long");
            this.put("double", "java.lang.Double");
            this.put("float", "java.lang.FLoat");
            this.put("byte", "java.lang.Byte");
            this.put("boolean", "java.lang.Boolean");
            this.put("byte[]", "java.lang.String");
        }
    };

    public PsiClassUtil() {
    }

    @NotNull
    public static List<String> extractProps(PsiClass pojoClass) {
        PsiField[] allFields = pojoClass.getAllFields();
        List<String> props = new ArrayList();
        PsiField[] var3 = allFields;
        int var4 = allFields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiField psiField = var3[var5];
            if (isSupprtedModifier(psiField)) {
                props.add(psiField.getName());
            }
        }

        if (props == null) {
            $$$reportNull$$$0(0);
        }

        return props;
    }

    @NotNull
    public static List<PropWithType> extractPropsWithType(PsiClass pojoClass) {
        PsiField[] allFields = pojoClass.getAllFields();
        List<PropWithType> props = Lists.newArrayList();
        PsiField[] var3 = allFields;
        int var4 = allFields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiField psiField = var3[var5];
            if (isSupprtedModifier(psiField)) {
                PropWithType e = new PropWithType();
                e.setPropName(psiField.getName());
                e.setPropType(convertToObjectText(psiField.getType().getCanonicalText()));
                props.add(e);
            }
        }

        if (props == null) {
            $$$reportNull$$$0(1);
        }

        return props;
    }

    @NotNull
    public static String getModuleSrcPathOfClass(PsiClass srcClass) {
        String qualifiedName = srcClass.getQualifiedName();
        String[] split = qualifiedName.split("\\.");
        int splitLength = split.length;

        VirtualFile psiFile;
        for(psiFile = srcClass.getContainingFile().getVirtualFile(); splitLength > 0; --splitLength) {
            psiFile = psiFile.getParent();
        }

        String var10000 = psiFile.getPath();
        if (var10000 == null) {
            $$$reportNull$$$0(2);
        }

        return var10000;
    }

    @NotNull
    public static String getPackageToModule(String path, String modulePath) {
        Path moduleSrc = Paths.get(modulePath);
        Path relativeToSouce = moduleSrc.relativize(Paths.get(path));
        String relate = relativeToSouce.toString();
        relate = relate.replace("\\", ".");
        relate = relate.replace("/", ".");
        if (relate == null) {
            $$$reportNull$$$0(3);
        }

        return relate;
    }

    public static PsiMethod getAddMethod(PsiClass srcClass) {
        PsiMethod[] methods = srcClass.getMethods();
        List<PsiMethod> methodsList = new ArrayList();
        PsiMethod[] var3 = methods;
        int var4 = methods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiMethod classMethod = var3[var5];
            String name = classMethod.getName().toLowerCase();
            if ((name.startsWith("insert") || name.startsWith("save") || name.startsWith("add") || name.startsWith("create")) && classMethod.getParameterList().getParameters().length == 1) {
                PsiParameter parameter = classMethod.getParameterList().getParameters()[0];
                PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter.getType());
                if (canBeDomainClass(psiClass)) {
                    methodsList.add(classMethod);
                }
            }
        }

        if (methodsList.size() == 0) {
            return null;
        } else {
            PsiMethod miniMethod = (PsiMethod)methodsList.stream().min(Comparator.comparingInt((o) -> {
                return o.getName().length();
            })).get();
            return miniMethod;
        }
    }

    public static boolean canBeDomainClass(PsiClass psiClass) {
        if (psiClass != null) {
            String qualifiedName = psiClass.getQualifiedName();
            if (qualifiedName == null) {
                return false;
            }

            if (!qualifiedName.startsWith("java.")) {
                PsiField[] allFields = psiClass.getAllFields();
                PsiField[] var3 = allFields;
                int var4 = allFields.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    PsiField allField = var3[var5];
                    if (isSupprtedModifier(allField)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Nullable
    public static DomainClassInfo getDomainClassInfo(PsiClass srcClass) {
        PsiMethod addMethod = getAddMethod(srcClass);
        PsiClass psiClass;
        DomainClassInfo info;
        if (addMethod != null) {
            psiClass = PsiTypesUtil.getPsiClass(addMethod.getParameterList().getParameters()[0].getType());
            info = new DomainClassInfo();
            info.setDomainClass(psiClass);
            info.setDomainClassSourceType(DomainClassSourceType.FROMMETHOD);
            return info;
        } else {
            psiClass = MyBatisPlusUtils.getClassFromMyBatisPlus(srcClass);
            if (psiClass != null) {
                info = new DomainClassInfo();
                info.setDomainClass(psiClass);
                info.setDomainClassSourceType(DomainClassSourceType.MYBATISPLUS);
                return info;
            } else {
                return null;
            }
        }
    }

    public static List<ClassFieldInfo> buildPropFieldInfo(List<PsiField> validFields) {
        List<ClassFieldInfo> lists = new ArrayList();

        ClassFieldInfo info;
        for(Iterator var2 = validFields.iterator(); var2.hasNext(); lists.add(info)) {
            PsiField psiField = (PsiField)var2.next();
            info = new ClassFieldInfo();
            info.setFieldName(psiField.getName());
            info.setFieldType(convertToObjectText(psiField.getType().getCanonicalText()));
            info.setPsiField(psiField);
            if (psiField.getDocComment() != null && !StringUtils.isBlank(psiField.getDocComment().getText())) {
                info.setComment(extractCommentForDocComment(psiField.getDocComment().getText()));
            } else {
                PsiComment childOfType = (PsiComment)PsiTreeUtil.findChildOfType(psiField, PsiComment.class);
                if (childOfType != null) {
                    info.setComment(extractCommentForDocComment(childOfType.getText()));
                } else {
                    info.setComment(psiField.getName());
                }
            }
        }

        return lists;
    }

    public static boolean isSupprtedModifier(PsiField psiField) {
        return (psiField.hasModifierProperty("private") || psiField.hasModifierProperty("protected")) && !psiField.hasModifierProperty("static") && !psiField.hasModifierProperty("transient");
    }

    public static List<String> buildPropFields(PsiClass psiClass) {
        List<String> lists = new ArrayList();
        PsiField[] allFields = psiClass.getAllFields();
        PsiField[] var3 = allFields;
        int var4 = allFields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiField psiField = var3[var5];
            if (isSupprtedModifier(psiField)) {
                lists.add(psiField.getName());
            }
        }

        return lists;
    }

    @NotNull
    public static Map<String, String> buildFieldMapWithConvertPrimitiveType(PsiClass pojoClass) {
        Map<String, String> fieldMap = new HashMap();
        PsiField[] allFields = pojoClass.getAllFields();
        PsiField[] var3 = allFields;
        int var4 = allFields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiField f = var3[var5];
            if (isSupprtedModifier(f)) {
                String canonicalText = f.getType().getCanonicalText();
                String objectTypeText = convertToObjectText(canonicalText);
                fieldMap.put(f.getName(), objectTypeText);
            }
        }

        if (fieldMap == null) {
            $$$reportNull$$$0(4);
        }

        return fieldMap;
    }

    @NotNull
    public static Map<String, String> buildFieldMap(PsiClass pojoClass) {
        Map<String, String> fieldMap = new HashMap();
        PsiField[] allFields = pojoClass.getAllFields();
        PsiField[] var3 = allFields;
        int var4 = allFields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiField f = var3[var5];
            if (isSupprtedModifier(f)) {
                String canonicalText = f.getType().getCanonicalText();
                fieldMap.put(f.getName(), canonicalText);
            }
        }

        if (fieldMap == null) {
            $$$reportNull$$$0(5);
        }

        return fieldMap;
    }

    public static String convertToObjectText(String canonicalText) {
        String s = (String)primitiveToObjectMap.get(canonicalText);
        return s != null ? s : canonicalText;
    }

    public static boolean isPrimitiveType(String type) {
        return primitiveToObjectMap.containsKey(type);
    }

    @Nullable
    public static PsiClass findClassOfQuatifiedType(@NotNull PsiElement element, @NotNull String resultTypeValue) {
        if (element == null) {
            $$$reportNull$$$0(6);
        }

        if (resultTypeValue == null) {
            $$$reportNull$$$0(7);
        }

        Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(element);
        if (moduleForPsiElement == null) {
            return null;
        } else {
            PsiClass[] classesByName = PsiShortNamesCache.getInstance(element.getProject()).getClassesByName(MapperUtil.extractClassShortName(resultTypeValue), GlobalSearchScope.moduleScope(moduleForPsiElement));
            if (classesByName.length == 0) {
                return null;
            } else {
                PsiClass[] var4 = classesByName;
                int var5 = classesByName.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PsiClass psiClass = var4[var6];
                    if (psiClass.getQualifiedName().equals(resultTypeValue)) {
                        return psiClass;
                    }
                }

                return null;
            }
        }
    }

    @Nullable
    public static PsiClass findClassOfQuatifiedTypeUsingType(@NotNull PsiElement element, @NotNull String resultTypeValue) {
        if (element == null) {
            $$$reportNull$$$0(8);
        }

        if (resultTypeValue == null) {
            $$$reportNull$$$0(9);
        }

        Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(element);
        if (moduleForPsiElement == null) {
            return null;
        } else {
            PsiClassType typeByName = PsiType.getTypeByName(resultTypeValue, element.getProject(), GlobalSearchScope.moduleScope(moduleForPsiElement));
            PsiClass psiClass = PsiTypesUtil.getPsiClass(typeByName);
            return psiClass;
        }
    }

    @Nullable
    public static PsiClass findClassOfQuatifiedType(@Nullable Module module, @NotNull Project project, @NotNull String resultTypeValue) {
        if (project == null) {
            $$$reportNull$$$0(10);
        }

        if (resultTypeValue == null) {
            $$$reportNull$$$0(11);
        }

        if (module == null) {
            return null;
        } else {
            PsiClass[] classesByName = PsiShortNamesCache.getInstance(project).getClassesByName(MapperUtil.extractClassShortName(resultTypeValue), GlobalSearchScope.moduleScope(module));
            if (classesByName.length == 0) {
                return null;
            } else {
                PsiClass[] var4 = classesByName;
                int var5 = classesByName.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PsiClass psiClass = var4[var6];
                    if (psiClass.getQualifiedName().equals(resultTypeValue)) {
                        return psiClass;
                    }
                }

                return null;
            }
        }
    }

    @NotNull
    public static List<String> extractMyBatisParam(PsiMethod findMethod) {
        List<String> lookUpResult = new ArrayList();
        PsiParameter[] parameters1 = findMethod.getParameterList().getParameters();
        PsiParameter[] var3 = parameters1;
        int var4 = parameters1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiParameter parameter = var3[var5];
            String parameterText = parameter.getText();
            String param = extractParam(parameterText);
            String parameterType = parameter.getType().getCanonicalText();
            parameterType = convertToObjectText(parameterType);
            if (DatabaseComponenent.currentHandler().getAutoCompleteHandler().isSupportedParam(parameter)) {
                if (param != null) {
                    lookUpResult.add(param);
                }
            } else {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter.getType());
                if (psiClass != null) {
                    List<String> props = extractProps(psiClass);
                    if (param == null) {
                        lookUpResult.addAll(props);
                    } else {
                        Iterator var12 = props.iterator();

                        while(var12.hasNext()) {
                            String prop = (String)var12.next();
                            lookUpResult.add(param + "." + prop);
                        }
                    }
                }
            }
        }

        if (lookUpResult == null) {
            $$$reportNull$$$0(12);
        }

        return lookUpResult;
    }

    @NotNull
    public static Map<String, String> extractMybatisParamForXmlSql(PsiMethod findMethod) {
        Map<String, String> lookUpResult = Maps.newLinkedHashMap();
        PsiParameter[] parameters1 = findMethod.getParameterList().getParameters();
        PsiParameter[] var3 = parameters1;
        int var4 = parameters1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiParameter parameter = var3[var5];
            String parameterText = parameter.getText();
            String param = extractParam(parameterText);
            String parameterType = parameter.getType().getCanonicalText();
            parameterType = convertToObjectText(parameterType);
            int k = 0;
            if (DatabaseComponenent.currentHandler().getAutoCompleteHandler().isSupportedParam(parameter)) {
                if (param == null) {
                    StringBuilder var10001 = (new StringBuilder()).append("param");
                    int var21 = k + 1;
                    lookUpResult.put(var10001.append(k).toString(), parameterType);
                } else {
                    lookUpResult.put(param, parameterType);
                }
            } else {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(parameter.getType());
                if (psiClass != null) {
                    if (Objects.equals(psiClass.getQualifiedName(), "java.util.List")) {
                        PsiType[] typeParameters = parameter.getTypeElement().getInnermostComponentReferenceElement().getTypeParameters();
                        if (typeParameters.length > 0) {
                            PsiType typeParameter = typeParameters[0];
                            PsiClass psiClass1 = PsiTypesUtil.getPsiClass(typeParameter);
                            String type = convertToObjectText(psiClass1.getQualifiedName());
                            int listSize = 2;
                            if (!CollectionUtils.isEmpty(MysqlHandlerUtils.getTypePropsByQulifiType(type))) {
                                for(int i = 0; i < listSize; ++i) {
                                    lookUpResult.put(param + "[0]", type);
                                    lookUpResult.put(param + "[1]", type);
                                }
                            } else {
                                List<PropWithType> props = extractPropsWithType(psiClass1);

                                for(int i = 0; i < listSize; ++i) {
                                    Iterator var19;
                                    PropWithType prop;
                                    if (param == null) {
                                        var19 = props.iterator();

                                        while(var19.hasNext()) {
                                            prop = (PropWithType)var19.next();
                                            lookUpResult.put("[" + i + "]." + prop.getPropName(), prop.getPropType());
                                        }
                                    } else {
                                        var19 = props.iterator();

                                        while(var19.hasNext()) {
                                            prop = (PropWithType)var19.next();
                                            lookUpResult.put(param + "[" + i + "]." + prop.getPropName(), prop.getPropType());
                                        }
                                    }
                                }
                            }
                        }

                        if (lookUpResult == null) {
                            $$$reportNull$$$0(13);
                        }

                        return lookUpResult;
                    }

                    List<PropWithType> props = extractPropsWithType(psiClass);
                    Iterator var13;
                    PropWithType prop;
                    if (param == null) {
                        var13 = props.iterator();

                        while(var13.hasNext()) {
                            prop = (PropWithType)var13.next();
                            lookUpResult.put(prop.getPropName(), prop.getPropType());
                        }
                    } else {
                        var13 = props.iterator();

                        while(var13.hasNext()) {
                            prop = (PropWithType)var13.next();
                            lookUpResult.put(param + "." + prop.getPropName(), prop.getPropType());
                        }
                    }
                }
            }
        }

        if (lookUpResult == null) {
            $$$reportNull$$$0(14);
        }

        return lookUpResult;
    }

    public static String extractParam(String parameterText) {
        int i = parameterText.indexOf("@Param(\"");
        if (i == -1) {
            return null;
        } else {
            int u = i + "@Param(\"".length();

            String m;
            char c;
            for(m = ""; u < parameterText.length() && (c = parameterText.charAt(u)) != '"'; ++u) {
                m = m + c;
            }

            return m.length() > 0 ? m : null;
        }
    }

    @Nullable
    public static PsiMethod getClassMethodByMethodName(PsiClass namespaceClass, String methodName) {
        PsiMethod[] methods = namespaceClass.getMethods();
        PsiMethod findMethod = null;
        PsiMethod[] var4 = methods;
        int var5 = methods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PsiMethod method = var4[var6];
            if (method.getName().equals(methodName)) {
                findMethod = method;
                break;
            }
        }

        return findMethod;
    }

    @Nullable
    public static String extractRealType(PsiType returnType) {
        if (returnType == null) {
            return null;
        } else if (returnType instanceof PsiClassReferenceType) {
            PsiClassReferenceType referenceType = (PsiClassReferenceType)returnType;
            return referenceType.getParameterCount() == 0 ? returnType.getCanonicalText() : referenceType.getParameters()[0].getCanonicalText();
        } else {
            return returnType.getCanonicalText();
        }
    }

    public static String extractLast(String returnClass) {
        int s = returnClass.lastIndexOf(".");
        return returnClass.substring(s + 1);
    }

    @NotNull
    public static String extractCommentForDocComment(String totalString) {
        String strip;
        if (totalString.startsWith("//")) {
            totalString = totalString.substring(2);
            strip = StringUtils.strip(totalString, " *\n\t");
            if (strip == null) {
                $$$reportNull$$$0(15);
            }

            return strip;
        } else {
            if (totalString.startsWith("/*")) {
                totalString = totalString.substring(2);
            }

            if (totalString.endsWith("*/")) {
                totalString = totalString.substring(0, totalString.length() - 2);
            }

            strip = StringUtils.strip(totalString, " *\n\t");
            if (strip == null) {
                $$$reportNull$$$0(16);
            }

            return strip;
        }
    }

    public static String extractTableName(PsiClass psiClass) {
        PsiModifierList modifierList = psiClass.getModifierList();
        if (modifierList != null) {
            PsiAnnotation[] annotations = modifierList.getApplicableAnnotations();
            if (annotations.length > 0) {
                PsiAnnotation[] var3 = annotations;
                int var4 = annotations.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    PsiAnnotation annotation = var3[var5];
                    if ("javax.persistence.Table".equals(annotation.getQualifiedName())) {
                        PsiNameValuePair[] attributes = annotation.getParameterList().getAttributes();
                        PsiNameValuePair[] var8 = attributes;
                        int var9 = attributes.length;

                        for(int var10 = 0; var10 < var9; ++var10) {
                            PsiNameValuePair attribute = var8[var10];
                            if (attribute.getName() == null || "name".equals(attribute.getName())) {
                                PsiAnnotationMemberValue value = attribute.getValue();
                                if (value != null && org.apache.commons.lang.StringUtils.isNotBlank(value.getText()) && value.getText().startsWith("\"") && value.getText().endsWith("\"")) {
                                    return value.getText().substring(1, value.getText().length() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        String className = psiClass.getName();
        return GenCodeUtil.getUnderScore(className);
    }
}
