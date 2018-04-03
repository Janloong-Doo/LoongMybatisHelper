//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.dto.mybatis.ClassMapperMethod;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.ccnode.codegenerator.dialog.dto.mybatis.MapperMethodEnum;
import com.ccnode.codegenerator.enums.MethodName;
import com.ccnode.codegenerator.freemarker.TemplateUtil;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.google.common.collect.Maps;
import com.intellij.psi.PsiClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapperUtil {
    public static final String SELECT = "select";
    public static final String FROM = "from";

    public MapperUtil() {
    }

    @Nullable
    static String generateSql(List<GenCodeProp> newAddedProps, List<ColumnAndField> deleteFields, String sqlText, List<ColumnAndField> existingFields) {
        sqlText = deleteEndEmptyChar(sqlText);
        String beforeWhere = sqlText;
        int start = 0;
        int end = sqlText.length();
        String lowerSqlText = sqlText.toLowerCase();
        int where = findMatchFor(lowerSqlText, "from");
        if (where != -1) {
            end = where;
            beforeWhere = sqlText.substring(0, where);
        }

        int select = findMatchFor(lowerSqlText, "select");
        if (select != -1) {
            start = select + "select".length();
            beforeWhere = beforeWhere.substring(select + "select".length());
        }

        if (beforeWhere.contains("(")) {
            return null;
        } else {
            String[] split = beforeWhere.split(",");
            List<String> beforeFormatted = new ArrayList();
            String[] var12 = split;
            int i = split.length;

            int i;
            for(i = 0; i < i; ++i) {
                String uu = var12[i];
                String term = trimUseLess(uu);
                boolean isDeleted = false;
                Iterator var18 = deleteFields.iterator();

                while(var18.hasNext()) {
                    ColumnAndField deleteField = (ColumnAndField)var18.next();
                    if (term.toLowerCase().equals(deleteField.getColumn().toLowerCase())) {
                        isDeleted = true;
                        break;
                    }
                }

                if (!isDeleted) {
                    beforeFormatted.add(uu);
                }
            }

            String beforeInsert = "";

            for(i = 0; i < beforeFormatted.size(); ++i) {
                beforeInsert = beforeInsert + (String)beforeFormatted.get(i);
                if (i != beforeFormatted.size() - 1) {
                    beforeInsert = beforeInsert + ",";
                }
            }

            String newAddInsert = "";

            for(i = 0; i < newAddedProps.size(); ++i) {
                newAddInsert = newAddInsert + ",\n" + DatabaseComponenent.formatColumn(((GenCodeProp)newAddedProps.get(i)).getColumnName());
            }

            String newValueText = sqlText.substring(0, start) + beforeInsert + newAddInsert + sqlText.substring(end) + "\n";
            return newValueText;
        }
    }

    private static int findMatchFor(String lowerSqlText, String where) {
        Pattern matcher = Pattern.compile("\\b" + where + "\\b");
        Matcher matcher1 = matcher.matcher(lowerSqlText);
        return matcher1.find() ? matcher1.start() : -1;
    }

    private static String trimUseLess(String uu) {
        int len = uu.length();
        int start = 0;
        int end = uu.length();
        int start = start + 1;

        char c;
        for(c = uu.charAt(start); start != len && c == '\n' || c == ' ' || c == '\t' || c == '`'; c = uu.charAt(start++)) {
            ;
        }

        if (start == len) {
            return "";
        } else {
            --end;

            for(c = uu.charAt(end); end >= start && c == '\n' || c == ' ' || c == '\t' || c == '`'; c = uu.charAt(end)) {
                --end;
            }

            return uu.substring(start - 1, end + 1);
        }
    }

    public static String generateMapperMethod(PsiClass myclass, List<ColumnAndField> finalFields, String tableName, MapperMethodEnum type, ClassMapperMethod classMapperMethod) {
        if (tableName == null) {
            tableName = "";
        }

        String methodName = classMapperMethod.getMethodName();
        if (methodName.equals(MethodName.insert.name())) {
            return genInsert(myclass, finalFields, tableName);
        } else if (methodName.equals(MethodName.insertList.name())) {
            return genInsertList(myclass, finalFields, tableName);
        } else if (methodName.equals(MethodName.insertSelective.name())) {
            return genInsertSelective(myclass, finalFields, tableName);
        } else {
            return methodName.equals(MethodName.update.name()) ? genUpdateMethod(myclass, finalFields, tableName) : null;
        }
    }

    private static String genUpdateMethod(PsiClass myClass, List<ColumnAndField> finalFields, String tableName) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("finalFields", finalFields);
        root.put("tableName", tableName);
        root.put("pojoName", GenCodeUtil.getLowerCamel(myClass.getName()));
        return TemplateUtil.processToString("update.ftl", root);
    }

    private static String genInsertList(PsiClass myClass, List<ColumnAndField> finalFields, String tableName) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("finalFields", finalFields);
        root.put("tableName", tableName);
        root.put("pojoName", GenCodeUtil.getLowerCamel(myClass.getName()));
        root.put("currentDatabase", DatabaseComponenent.currentDatabase());
        return TemplateUtil.processToString("insertList.ftl", root);
    }

    private static String genInsert(PsiClass myClass, List<ColumnAndField> finalFields, String tableName) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("finalFields", finalFields);
        root.put("tableName", tableName);
        root.put("pojoName", GenCodeUtil.getLowerCamel(myClass.getName()));
        String s = null;
        boolean useTest = false;
        if (useTest) {
            s = TemplateUtil.processToString("insert.ftl", root);
        } else {
            s = TemplateUtil.processToString("insert_without_test.ftl", root);
        }

        return s;
    }

    private static String genInsertSelective(PsiClass myClass, List<ColumnAndField> finalFields, String tableName) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("finalFields", finalFields);
        root.put("tableName", tableName);
        root.put("pojoName", GenCodeUtil.getLowerCamel(myClass.getName()));
        String s = null;
        boolean useTest = true;
        if (useTest) {
            s = TemplateUtil.processToString("insert.ftl", root);
        } else {
            s = TemplateUtil.processToString("insert_without_test.ftl", root);
        }

        return s;
    }

    public static String extractTable(String insertText) {
        if (insertText.length() == 0) {
            return null;
        } else {
            String formattedInsert = formatBlank(insertText).toLowerCase();
            int i = formattedInsert.indexOf("insert into");
            if (i == -1) {
                return null;
            } else {
                int s = i + "insert into".length() + 1;
                StringBuilder resBuilder = new StringBuilder();

                for(int j = s; j < formattedInsert.length(); ++j) {
                    char c = formattedInsert.charAt(j);
                    if (isBlankChar(c)) {
                        break;
                    }

                    resBuilder.append(c);
                }

                return resBuilder.length() > 0 ? resBuilder.toString() : null;
            }
        }
    }

    private static String formatBlank(String insertText) {
        StringBuilder result = new StringBuilder();
        char firstChar = insertText.charAt(0);
        result.append(firstChar);
        boolean before = isBlankChar(firstChar);

        for(int i = 1; i < insertText.length(); ++i) {
            char curChar = insertText.charAt(i);
            boolean cur = isBlankChar(curChar);
            if (!cur || !before) {
                result.append(curChar);
                before = cur;
            }
        }

        return result.toString();
    }

    private static boolean isBlankChar(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '(' || c == '<' || c == ')' || c == '>';
    }

    @NotNull
    public static String extractClassShortName(String fullName) {
        int i = fullName.lastIndexOf(".");
        String var10000 = fullName.substring(i + 1);
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }

    @Nullable
    public static String extractPackage(String fullName) {
        int i = fullName.lastIndexOf(".");
        return i == -1 ? null : fullName.substring(0, i);
    }

    public static String deleteEndEmptyChar(String text) {
        int end = text.length();
        int useEnd = end - 1;

        for(int j = end - 1; j >= 0; --j) {
            char c = text.charAt(j);
            if (c != '\n' && c != '\t' && c != ' ') {
                useEnd = j;
                break;
            }
        }

        return text.substring(0, useEnd + 1);
    }
}
