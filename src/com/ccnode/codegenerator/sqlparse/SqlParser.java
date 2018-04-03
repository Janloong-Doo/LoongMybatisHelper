//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.sqlparse;

import com.ccnode.codegenerator.util.PsiClassUtil;
import com.ccnode.codegenerator.view.completion.MysqlCompleteCacheInteface;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlTag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class SqlParser {
    public SqlParser() {
    }

    public static ParsedResult parse(ParseContext context) {
        List<String> baseRecommends = Lists.newArrayList();
        ParsedResult result = new ParsedResult();
        result.setRecommedValues(new ArrayList());
        String currentWordStart = context.getCurrentWordStart();
        String beforeCurrentWordString = getBeforeRealString(currentWordStart);
        List<String> beforeWord = extractWords(context.getBeforeText().toLowerCase());
        List<String> afterWords = extractWords(context.getAfterText().toLowerCase());
        boolean currentIsSkipChar = isSkipChar(context.getAllText().charAt(context.getCursorOffSet() - 1));
        if (beforeWord.size() != 0 && (beforeWord.size() != 1 || currentIsSkipChar)) {
            MysqlCompleteCacheInteface cacheService = (MysqlCompleteCacheInteface)ServiceManager.getService(context.getProject(), MysqlCompleteCacheInteface.class);
            if (beforeWord.contains("select")) {
                if (checkTableNameRecommend(beforeWord, currentIsSkipChar)) {
                    List<String> allTables = cacheService.getAllTables();
                    result.setRecommedValues(convertToRecommeds(allTables));
                    return result;
                } else {
                    boolean beforeContainsFrom = beforeWord.contains("from");
                    boolean afterContainsFrom = afterWords.contains("from");
                    if (!beforeContainsFrom) {
                        if (checkAsRecommed(beforeWord, currentIsSkipChar)) {
                            XmlTag currentTag = context.getCurrentTag();
                            final String attributeValue = currentTag.getAttributeValue("resultType");
                            if (StringUtils.isNotEmpty(attributeValue)) {
                                PsiClass classOfQuatifiedType = PsiClassUtil.findClassOfQuatifiedType(ModuleUtilCore.findModuleForPsiElement(context.getCurrentXmlFile()), context.getProject(), attributeValue);
                                if (classOfQuatifiedType != null) {
                                    List<String> strings = PsiClassUtil.extractProps(classOfQuatifiedType);
                                    Iterator var15 = strings.iterator();

                                    while(var15.hasNext()) {
                                        final String string = (String)var15.next();
                                        result.getRecommedValues().add(LookupElementBuilder.create(string).withRenderer(new LookupElementRenderer<LookupElement>() {
                                            public void renderElement(LookupElement element, LookupElementPresentation presentation) {
                                                presentation.setItemText(string);
                                                presentation.setTypeText(attributeValue);
                                            }
                                        }));
                                    }
                                }
                            }

                            return result;
                        } else {
                            List fields;
                            Iterator var20;
                            TableNameAndFieldName field;
                            if (!afterContainsFrom) {
                                fields = cacheService.getAllFieldsWithTable();
                                var20 = fields.iterator();

                                while(var20.hasNext()) {
                                    field = (TableNameAndFieldName)var20.next();
                                    result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                                }

                                result.getRecommedValues().add(LookupElementBuilder.create("from "));
                                result.getRecommedValues().add(LookupElementBuilder.create("as "));
                                result.getRecommedValues().addAll(MethodRecommendCache.getRecommends(beforeCurrentWordString));
                                return result;
                            } else {
                                fields = getRecommendFromTableFields(afterWords, cacheService);
                                var20 = fields.iterator();

                                while(var20.hasNext()) {
                                    field = (TableNameAndFieldName)var20.next();
                                    result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                                }

                                result.getRecommedValues().addAll(MethodRecommendCache.getRecommends(beforeCurrentWordString));
                                return result;
                            }
                        }
                    } else {
                        boolean beforeContainWhereOrOn = beforeWord.contains("where") || beforeWord.contains("on");
                        List fields;
                        Iterator var13;
                        TableNameAndFieldName field;
                        if (checkListContains(beforeWord, "order", "by")) {
                            fields = getRecommendFromTableFields(beforeWord, cacheService);
                            var13 = fields.iterator();

                            while(var13.hasNext()) {
                                field = (TableNameAndFieldName)var13.next();
                                result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                            }

                            result.getRecommedValues().addAll(MethodRecommendCache.getRecommends(beforeCurrentWordString));
                            result.getRecommedValues().add(LookupElementBuilder.create("limit "));
                            return result;
                        } else if (checkListContains(beforeWord, "having")) {
                            fields = getRecommendFromTableFields(beforeWord, cacheService);
                            var13 = fields.iterator();

                            while(var13.hasNext()) {
                                field = (TableNameAndFieldName)var13.next();
                                result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                            }

                            result.getRecommedValues().addAll(MethodRecommendCache.getRecommends(beforeCurrentWordString));
                            result.getRecommedValues().add(LookupElementBuilder.create("limit "));
                            result.getRecommedValues().add(LookupElementBuilder.create("order by "));
                            return result;
                        } else if (checkListContains(beforeWord, "group", "by")) {
                            fields = getRecommendFromTableFields(beforeWord, cacheService);
                            var13 = fields.iterator();

                            while(var13.hasNext()) {
                                field = (TableNameAndFieldName)var13.next();
                                result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                            }

                            result.getRecommedValues().add(LookupElementBuilder.create("order by "));
                            result.getRecommedValues().add(LookupElementBuilder.create("limit "));
                            result.getRecommedValues().add(LookupElementBuilder.create("having "));
                            return result;
                        } else if (!beforeContainWhereOrOn) {
                            baseRecommends = Lists.newArrayList();
                            baseRecommends.add("inner join ");
                            baseRecommends.add("left join ");
                            baseRecommends.add("right join ");
                            baseRecommends.add("where ");
                            if (beforeWord.contains("join")) {
                                baseRecommends.add("on ");
                            }

                            result.getRecommedValues().addAll(convertToRecommeds(baseRecommends));
                            result.getRecommedValues().add(LookupElementBuilder.create("order by "));
                            result.getRecommedValues().add(LookupElementBuilder.create("limit "));
                            result.getRecommedValues().add(LookupElementBuilder.create("having "));
                            result.getRecommedValues().add(LookupElementBuilder.create("group by "));
                            return result;
                        } else {
                            fields = getRecommendFromTableFields(beforeWord, cacheService);
                            var13 = fields.iterator();

                            while(var13.hasNext()) {
                                field = (TableNameAndFieldName)var13.next();
                                result.getRecommedValues().add(getTableAndFieldElement(beforeCurrentWordString, field));
                            }

                            result.getRecommedValues().add(LookupElementBuilder.create("order by "));
                            result.getRecommedValues().add(LookupElementBuilder.create("limit "));
                            result.getRecommedValues().add(LookupElementBuilder.create("having "));
                            result.getRecommedValues().add(LookupElementBuilder.create("group by "));
                            return result;
                        }
                    }
                }
            } else {
                return result;
            }
        } else {
            baseRecommends.add("insert into ");
            baseRecommends.add("select ");
            baseRecommends.add("update ");
            baseRecommends.add("delete ");
            result.setRecommedValues(convertToRecommeds(baseRecommends));
            return result;
        }
    }

    private static boolean checkAsRecommed(List<String> beforeWord, boolean currentIsSkipChar) {
        int size = beforeWord.size();
        if (size >= 2) {
            if (currentIsSkipChar) {
                if ("as".equals(beforeWord.get(size - 1))) {
                    return true;
                }
            } else if ("as".equals(beforeWord.get(size - 2))) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkListContains(List<String> beforeWord, String... contains) {
        String[] var2 = contains;
        int var3 = contains.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            if (!beforeWord.contains(s)) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkTableNameRecommend(List<String> beforeWord, boolean currentIsSkipChar) {
        int size = beforeWord.size();
        if (size >= 2) {
            if (currentIsSkipChar) {
                if ("join".equals(beforeWord.get(size - 1)) || "from".equals(beforeWord.get(size - 1))) {
                    return true;
                }
            } else if ("join".equals(beforeWord.get(size - 2)) || "from".equals(beforeWord.get(size - 2))) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    private static LookupElementBuilder getTableAndFieldElement(String beforeCurrentWordString, final TableNameAndFieldName field) {
        LookupElementBuilder var10000 = LookupElementBuilder.create(beforeCurrentWordString + field.getFieldName()).withRenderer(new LookupElementRenderer<LookupElement>() {
            public void renderElement(LookupElement element, LookupElementPresentation presentation) {
                presentation.setItemText(field.getFieldName() + "  (" + field.getTableName() + ")");
            }
        });
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }

    private static List<LookupElement> convertToRecommeds(List<String> baseRecommends) {
        List<LookupElement> lookupElements = Lists.newArrayList();
        Iterator var2 = baseRecommends.iterator();

        while(var2.hasNext()) {
            String baseRecommend = (String)var2.next();
            lookupElements.add(LookupElementBuilder.create(baseRecommend));
        }

        return lookupElements;
    }

    private static String getBeforeRealString(String currentWordStart) {
        for(int i = currentWordStart.length() - 1; i >= 0; --i) {
            if (isSkipChar(currentWordStart.charAt(i))) {
                return currentWordStart.substring(0, i + 1);
            }
        }

        return "";
    }

    private static List<TableNameAndFieldName> getRecommendFromTableFields(List<String> words, MysqlCompleteCacheInteface cacheInteface) {
        List<TableNameAndFieldName> recommends = Lists.newArrayList();
        List<TableNameAndAliaseName> tableNameAndAliaseNames = extractNameFrom(words);
        Iterator var4 = tableNameAndAliaseNames.iterator();

        while(true) {
            while(var4.hasNext()) {
                TableNameAndAliaseName tableNameAndAliaseName = (TableNameAndAliaseName)var4.next();
                if (tableNameAndAliaseName.getAliaseName() == null) {
                    recommends.addAll(cacheInteface.getTableAllFields(tableNameAndAliaseName.getTableName()));
                } else {
                    List<TableNameAndFieldName> tableAllFields = cacheInteface.getTableAllFields(tableNameAndAliaseName.getTableName());
                    Iterator var7 = tableAllFields.iterator();

                    while(var7.hasNext()) {
                        TableNameAndFieldName tableAllField = (TableNameAndFieldName)var7.next();
                        String s = tableNameAndAliaseName.getAliaseName() + "." + tableAllField.getFieldName();
                        TableNameAndFieldName tableNameAndFieldName = new TableNameAndFieldName();
                        tableNameAndFieldName.setFieldName(s);
                        tableNameAndFieldName.setTableName(tableAllField.getTableName());
                        recommends.add(tableNameAndFieldName);
                    }
                }
            }

            return recommends;
        }
    }

    private static List<TableNameAndAliaseName> extractNameFrom(List<String> words) {
        List<TableNameAndAliaseName> tableNameAndAliaseNames = Lists.newArrayList();
        int size = words.size();

        for(int i = 0; i < words.size(); ++i) {
            String s = (String)words.get(i);
            if (("from".equals(s) || "join".equals(s)) && i < size - 1) {
                TableNameAndAliaseName tableNameAndAliaseName = new TableNameAndAliaseName();
                String tableName = (String)words.get(i + 1);
                tableNameAndAliaseName.setTableName(tableName);
                if (i < size - 2) {
                    String aliase = (String)words.get(i + 2);
                    if (!"inner".equals(aliase) && !"left".equals(aliase) && !"right".equals(aliase) && !"where".equals(aliase) && !"on".equals(aliase)) {
                        if ("as".equals(aliase)) {
                            if (i < size - 3) {
                                tableNameAndAliaseName.setAliaseName((String)words.get(i + 3));
                            }
                        } else {
                            tableNameAndAliaseName.setAliaseName(aliase);
                        }
                    }
                }

                tableNameAndAliaseNames.add(tableNameAndAliaseName);
            }
        }

        return tableNameAndAliaseNames;
    }

    private static List<String> extractWords(String startText) {
        List<String> words = Lists.newArrayList();
        String m = "";

        for(int i = 0; i < startText.length(); ++i) {
            char c = startText.charAt(i);
            if (isSkipChar(c)) {
                if (m.length() > 0) {
                    words.add(m);
                    m = "";
                }
            } else {
                m = m + c;
            }
        }

        if (m.length() > 0) {
            words.add(m);
        }

        return words;
    }

    private static boolean isSkipChar(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == ',' || c == '(' || c == ')';
    }
}
