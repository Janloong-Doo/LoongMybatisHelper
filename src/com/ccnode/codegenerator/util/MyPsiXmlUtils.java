//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.constants.MyBatisXmlConstants;
import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.MapperUtil;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;
import com.ccnode.codegenerator.methodnameparser.tag.XmlTagAndInfo;
import com.ccnode.codegenerator.pojo.ExistXmlTagInfo;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyPsiXmlUtils {
    public static final int TABLENAME_MAX_SIZE = 30;

    public MyPsiXmlUtils() {
    }

    @NotNull
    public static List<XmlTag> getXmlAttributeOfType(XmlDocument xmlDocument, Set<String> tagNames) {
        List<XmlTag> values = Lists.newArrayList();
        XmlTag rootTag = xmlDocument.getRootTag();
        if (rootTag == null) {
            if (values == null) {
                $$$reportNull$$$0(0);
            }

            return values;
        } else {
            if (tagNames.contains(rootTag.getName())) {
                values.add(rootTag);
            }

            addTagsToList(rootTag, (Set)tagNames, values);
            if (values == null) {
                $$$reportNull$$$0(1);
            }

            return values;
        }
    }

    @NotNull
    public static List<XmlTag> getXmlAttributeOfType(XmlDocument xmlDocument, String tagName) {
        List<XmlTag> values = Lists.newArrayList();
        XmlTag rootTag = xmlDocument.getRootTag();
        if (rootTag == null) {
            if (values == null) {
                $$$reportNull$$$0(2);
            }

            return values;
        } else {
            if (tagName.equals(rootTag.getName())) {
                values.add(rootTag);
            }

            addTagsToList(rootTag, (String)tagName, values);
            if (values == null) {
                $$$reportNull$$$0(3);
            }

            return values;
        }
    }

    private static void addTagsToList(XmlTag rootTag, Set<String> tagNames, List<XmlTag> values) {
        XmlTag[] subTags = rootTag.getSubTags();
        if (subTags.length != 0) {
            XmlTag[] var4 = subTags;
            int var5 = subTags.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                XmlTag subTag = var4[var6];
                if (tagNames.contains(subTag.getName())) {
                    values.add(subTag);
                }

                addTagsToList(subTag, tagNames, values);
            }

        }
    }

    private static void addTagsToList(XmlTag rootTag, String tagName, List<XmlTag> values) {
        XmlTag[] subTags = rootTag.getSubTags();
        if (subTags.length != 0) {
            XmlTag[] var4 = subTags;
            int var5 = subTags.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                XmlTag subTag = var4[var6];
                if (tagName.equals(subTag.getName())) {
                    values.add(subTag);
                }

                addTagsToList(subTag, tagName, values);
            }

        }
    }

    @Nullable
    public static String findCurrentElementIntefaceMethodName(PsiElement positionElement) {
        for(PsiElement parent = positionElement.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof XmlTag) {
                String name = ((XmlTag)parent).getName();
                if (MyBatisXmlConstants.mapperMethodSet.contains(name)) {
                    return ((XmlTag)parent).getAttributeValue("id");
                }
            }
        }

        return null;
    }

    @Nullable
    public static XmlTag findCurrentElementXmlTag(PsiElement positionElement) {
        for(PsiElement parent = positionElement.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof XmlTag) {
                String name = ((XmlTag)parent).getName();
                if (MyBatisXmlConstants.mapperMethodSet.contains(name)) {
                    return (XmlTag)parent;
                }
            }
        }

        return null;
    }

    @Nullable
    public static String findCurrentXmlFileNameSpace(@NotNull XmlFile xmlFile) {
        if (xmlFile == null) {
            $$$reportNull$$$0(4);
        }

        XmlTag rootTag = xmlFile.getRootTag();
        return rootTag != null && rootTag.getName().equals("mapper") ? rootTag.getAttributeValue("namespace") : null;
    }

    @Nullable
    public static String findTableNameFromRootTag(XmlTag rootTag) {
        List<XmlTag> insertXmlTags = findTagWithNameType(rootTag, "insert");
        if (insertXmlTags.isEmpty()) {
            return null;
        } else {
            Iterator var2 = insertXmlTags.iterator();

            String tableName;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                XmlTag insertXmlTag = (XmlTag)var2.next();
                String insertText = insertXmlTag.getValue().getText();
                tableName = MapperUtil.extractTable(insertText);
            } while(tableName == null || tableName.length() >= 30);

            return tableName;
        }
    }

    @NotNull
    private static List<XmlTag> findTagWithNameType(XmlTag rootTag, String tagName) {
        List<XmlTag> xmlTags = Lists.newArrayList();
        XmlTag[] subTags = rootTag.getSubTags();
        XmlTag[] var4 = subTags;
        int var5 = subTags.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            XmlTag tag = var4[var6];
            if (tag.getName().equalsIgnoreCase(tagName)) {
                xmlTags.add(tag);
            }
        }

        if (xmlTags == null) {
            $$$reportNull$$$0(5);
        }

        return xmlTags;
    }

    @NotNull
    public static ExistXmlTagInfo extractExistXmlInfo(List<String> props, XmlTag rootTag, String qualifiedName) {
        ExistXmlTagInfo existXmlTagInfo = new ExistXmlTagInfo();
        XmlTag[] subTags = rootTag.getSubTags();
        XmlTag[] var5 = subTags;
        int var6 = subTags.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            XmlTag tag = var5[var7];
            String tableName;
            String resultMapId;
            if (tag.getName().equalsIgnoreCase("insert")) {
                if (existXmlTagInfo.getTableName() == null) {
                    resultMapId = tag.getValue().getText();
                    tableName = MapperUtil.extractTable(resultMapId);
                    if (tableName != null && tableName.length() < 30) {
                        existXmlTagInfo.setTableName(tableName);
                    }
                }
            } else if (existXmlTagInfo.getFieldToColumnRelation() == null && tag.getName().equalsIgnoreCase("resultMap")) {
                XmlAttribute id = tag.getAttribute("id");
                if (id != null && id.getValue() != null) {
                    resultMapId = id.getValue();
                    XmlAttribute typeAttribute = tag.getAttribute("type");
                    if (typeAttribute != null && typeAttribute.getValue() != null && typeAttribute.getValue().trim().equals(qualifiedName)) {
                        existXmlTagInfo.setHasResultMap(true);
                        existXmlTagInfo.setFieldToColumnRelation(extractFieldAndColumnRelation(tag, props, resultMapId));
                    }
                }
            } else if (!existXmlTagInfo.isHasAllColumn() && tag.getName().equalsIgnoreCase("sql")) {
                XmlAttribute id = tag.getAttribute("id");
                if (id != null && StringUtils.isNotBlank(id.getValue())) {
                    tableName = tag.getValue().getText();
                }
            }
        }

        if (existXmlTagInfo == null) {
            $$$reportNull$$$0(6);
        }

        return existXmlTagInfo;
    }

    @Nullable
    public static FieldToColumnRelation extractFieldAndColumnRelation(XmlTag tag, List<String> props, String resultMapId) {
        Set<String> propSet = new HashSet(props);
        XmlTag[] subTags = tag.getSubTags();
        if (subTags != null && subTags.length != 0) {
            Map<String, String> fieldAndColumnMap = new LinkedHashMap();
            Map<String, String> jdbcTypeMap = new HashMap();
            XmlTag[] var7 = subTags;
            int var8 = subTags.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                XmlTag propTag = var7[var9];
                XmlAttribute column = propTag.getAttribute("column");
                XmlAttribute property = propTag.getAttribute("property");
                XmlAttribute jdbcType = propTag.getAttribute("jdbcType");
                if (column != null && column.getValue() != null && property != null && property.getValue() != null) {
                    String columnString = column.getValue().trim();
                    String propertyString = property.getValue().trim();
                    if (propSet.contains(propertyString)) {
                        if (jdbcType != null && jdbcType.getValue() != null) {
                            jdbcTypeMap.put(propertyString.toLowerCase(), jdbcType.getValue().trim());
                        }

                        fieldAndColumnMap.put(propertyString.toLowerCase(), columnString);
                        propSet.remove(propertyString);
                    }
                }
            }

            if (propSet.size() != 0) {
                return null;
            } else {
                FieldToColumnRelation relation = new FieldToColumnRelation();
                relation.setFieldToJdbcTypeMap(jdbcTypeMap);
                relation.setFiledToColumnMap(fieldAndColumnMap);
                relation.setResultMapId(resultMapId);
                return relation;
            }
        } else {
            return null;
        }
    }

    public static String buildAllColumn(Map<String, String> filedToColumnMap) {
        StringBuilder bu = new StringBuilder();
        int i = 0;
        Iterator var3 = filedToColumnMap.keySet().iterator();

        while(var3.hasNext()) {
            String s = (String)var3.next();
            ++i;
            bu.append("\n\t").append(DatabaseComponenent.formatColumn((String)filedToColumnMap.get(s)));
            if (i != filedToColumnMap.size()) {
                bu.append(",");
            }
        }

        bu.append("\n");
        return bu.toString();
    }

    public static String buildAllCoumnMap(Map<String, String> fieldToColumnMap) {
        StringBuilder builder = new StringBuilder();
        Iterator var2 = fieldToColumnMap.keySet().iterator();

        while(var2.hasNext()) {
            String prop = (String)var2.next();
            builder.append("\n\t").append("<result column=\"").append((String)fieldToColumnMap.get(prop)).append("\"").append(" property=\"").append(prop).append("\"/>");
        }

        builder.append("\n");
        return builder.toString();
    }

    public static XmlTag methodAlreadyExist(PsiFile psixml, String methodName) {
        XmlTag rootTag = ((XmlFileImpl)psixml).getRootTag();
        XmlTag[] subTags = rootTag.getSubTags();
        XmlTag[] var4 = subTags;
        int var5 = subTags.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            XmlTag subTag = var4[var6];
            XmlAttribute id = subTag.getAttribute("id");
            if (id != null && id.getValue() != null && id.getValue().equalsIgnoreCase(methodName)) {
                return subTag;
            }
        }

        return null;
    }

    public static XmlTagAndInfo generateTag(XmlTag rootTag, QueryInfo info, String methodName) {
        XmlTagAndInfo xmlTagAndInfo = new XmlTagAndInfo();
        xmlTagAndInfo.setInfo(info);
        XmlTag select = rootTag.createChildTag(info.getType().getValue(), "", info.getSql(), false);
        select.setAttribute("id", methodName);
        if (info.getReturnMap() != null) {
            select.setAttribute("resultMap", info.getReturnMap());
        } else if (info.getReturnClass() != null) {
            select.setAttribute("resultType", info.getReturnClass());
        }

        xmlTagAndInfo.setXmlTag(select);
        return xmlTagAndInfo;
    }

    public static FieldToColumnRelation convertToRelation(FieldToColumnRelation relation1) {
        FieldToColumnRelation relation = new FieldToColumnRelation();
        relation.setResultMapId(relation1.getResultMapId());
        Map<String, String> fieldToColumnLower = new LinkedHashMap();
        Iterator var3 = relation1.getFiledToColumnMap().keySet().iterator();

        while(var3.hasNext()) {
            String prop = (String)var3.next();
            fieldToColumnLower.put(prop.toLowerCase(), relation1.getFiledToColumnMap().get(prop));
        }

        relation.setFiledToColumnMap(fieldToColumnLower);
        return relation;
    }

    public static void buildAllColumnMap(Project myProject, Document document, XmlTag rootTag, PsiDocumentManager psiDocumentManager, FieldToColumnRelation relation1, String qualifiedName) {
        String allColumnMap = buildAllCoumnMap(relation1.getFiledToColumnMap());
        XmlTag resultMap = rootTag.createChildTag("resultMap", "", allColumnMap, false);
        resultMap.setAttribute("id", relation1.getResultMapId());
        resultMap.setAttribute("type", qualifiedName);
        WriteCommandAction.runWriteCommandAction(myProject, () -> {
            rootTag.addSubTag(resultMap, true);
            PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, document);
        });
    }

    @Nullable
    public static XmlTag findTagForMethodName(XmlFile xmlFile, String name) {
        XmlTag rootTag = xmlFile.getRootTag();
        if (rootTag == null) {
            return null;
        } else {
            XmlTag[] subTags = rootTag.getSubTags();
            if (subTags.length == 0) {
                return null;
            } else {
                XmlTag[] var4 = subTags;
                int var5 = subTags.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    XmlTag tag = var4[var6];
                    XmlAttribute id = tag.getAttribute("id");
                    if (id != null && id.getValue().equals(name)) {
                        return tag;
                    }
                }

                return null;
            }
        }
    }

    @NotNull
    public static FieldToColumnRelation findFieldToColumnRelation(XmlTag rootTag, String qualifiedName, List<String> props) {
        List<XmlTag> resultMapTags = findTagWithNameType(rootTag, "resultMap");
        FieldToColumnRelation fieldToColumnRelation = new FieldToColumnRelation();
        fieldToColumnRelation.setHasFullRelation(false);
        fieldToColumnRelation.setHasJavaTypeResultMap(false);
        Iterator var5 = resultMapTags.iterator();

        while(var5.hasNext()) {
            XmlTag tag = (XmlTag)var5.next();
            XmlAttribute id = tag.getAttribute("id");
            if (id != null && id.getValue() != null) {
                String resultMapId = id.getValue();
                XmlAttribute typeAttribute = tag.getAttribute("type");
                if (typeAttribute != null && typeAttribute.getValue() != null && typeAttribute.getValue().trim().equals(qualifiedName)) {
                    fieldToColumnRelation.setHasJavaTypeResultMap(true);
                    FieldToColumnRelation relation = extractFieldAndColumnRelation(tag, props, resultMapId);
                    if (relation != null) {
                        fieldToColumnRelation.setHasFullRelation(true);
                        fieldToColumnRelation.setFiledToColumnMap(relation.getFiledToColumnMap());
                        fieldToColumnRelation.setResultMapId(relation.getResultMapId());
                        fieldToColumnRelation.setFieldToJdbcTypeMap(relation.getFieldToJdbcTypeMap());
                        if (fieldToColumnRelation == null) {
                            $$$reportNull$$$0(7);
                        }

                        return fieldToColumnRelation;
                    }
                }
            }
        }

        if (fieldToColumnRelation == null) {
            $$$reportNull$$$0(8);
        }

        return fieldToColumnRelation;
    }

    @Nullable
    public static String findAllColumnName(XmlTag rootTag, Map<String, String> filedToColumnMap) {
        List<XmlTag> sqlTags = findTagWithNameType(rootTag, "sql");
        Set<String> allColumns = getAllColumn(filedToColumnMap);
        Iterator var4 = sqlTags.iterator();

        while(var4.hasNext()) {
            XmlTag sqlTag = (XmlTag)var4.next();
            XmlAttribute id = sqlTag.getAttribute("id");
            if (id != null && StringUtils.isNotBlank(id.getValue())) {
                String text = sqlTag.getValue().getText();
                if (checkContainAllColumn(text, allColumns)) {
                    return id.getValue();
                }
            }
        }

        return null;
    }

    private static boolean checkContainAllColumn(String text, Set<String> allColumns) {
        Set<String> textColumns = Sets.newHashSet();
        String u = "";

        for(int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c != '`' && c != ' ' && c != '\t' && c != '\n' && c != ',') {
                u = u + c;
            } else if (u.length() > 0) {
                textColumns.add(u);
                u = "";
            }
        }

        if (u.length() > 0) {
            textColumns.add(u);
        }

        if (textColumns.size() == allColumns.size()) {
            Iterator var6 = textColumns.iterator();

            String textColumn;
            do {
                if (!var6.hasNext()) {
                    return true;
                }

                textColumn = (String)var6.next();
            } while(allColumns.contains(textColumn));

            return false;
        } else {
            return false;
        }
    }

    private static Set<String> getAllColumn(Map<String, String> filedToColumnMap) {
        Set<String> allColumn = Sets.newHashSet();
        filedToColumnMap.forEach((key, value) -> {
            allColumn.add(value);
        });
        return allColumn;
    }

    @Nullable
    public static PsiMethod findMethodOfXmlTag(XmlTag tag) {
        PsiFile containingFile = tag.getContainingFile();
        XmlFile xmlFile = (XmlFile)containingFile;
        if (xmlFile.getRootTag() == null) {
            return null;
        } else {
            XmlAttribute namespaceAttribute = xmlFile.getRootTag().getAttribute("namespace");
            if (namespaceAttribute != null && namespaceAttribute.getValue() != null) {
                String namespace = namespaceAttribute.getValue();
                XmlAttribute id = tag.getAttribute("id");
                if (id != null && id.getValue() != null) {
                    String[] split = namespace.split("\\.");
                    String className = split[split.length - 1];
                    Module moduleForPsiElement = ModuleUtilCore.findModuleForPsiElement(tag);
                    if (moduleForPsiElement == null) {
                        return null;
                    } else {
                        PsiClass[] classesByName = PsiShortNamesCache.getInstance(tag.getProject()).getClassesByName(className, GlobalSearchScope.moduleScope(moduleForPsiElement));
                        PsiClass realClass = null;
                        PsiClass[] var11 = classesByName;
                        int var12 = classesByName.length;

                        for(int var13 = 0; var13 < var12; ++var13) {
                            PsiClass psiClass = var11[var13];
                            if (psiClass.isInterface() && psiClass.getQualifiedName().equals(namespace)) {
                                realClass = psiClass;
                                break;
                            }
                        }

                        if (realClass == null) {
                            return null;
                        } else {
                            String xmlMethodName = id.getValue();
                            PsiMethod findedMethod = null;
                            PsiMethod[] allMethods = realClass.getAllMethods();
                            PsiMethod[] var21 = allMethods;
                            int var15 = allMethods.length;

                            for(int var16 = 0; var16 < var15; ++var16) {
                                PsiMethod classMethod = var21[var16];
                                if (xmlMethodName.equals(classMethod.getName())) {
                                    findedMethod = classMethod;
                                    break;
                                }
                            }

                            return findedMethod == null ? null : findedMethod;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
