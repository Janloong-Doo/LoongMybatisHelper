//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.ccnode.codegenerator.sqlparse.ParseContext;
import com.ccnode.codegenerator.sqlparse.ParsedResult;
import com.ccnode.codegenerator.sqlparse.SqlParser;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import com.intellij.psi.xml.XmlText;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MapperSqlCompletionContributor extends CompletionContributor {
    private static ImmutableListMultimap<String, String> multimap = ImmutableListMultimap.builder().put("s", "select").put("S", "SELECT").put("i", "insert into").put("I", "INSERT INTO").put("u", "update").put("U", "UPDATE").put("d", "delete").put("D", "DELETE").put("j", "join").put("J", "JOIN").put("i", "inner join").put("I", "INNER JOIN").put("l", "left join").put("L", "LEFT JOIN").put("o", "on").put("O", "ON").put("m", "max").put("M", "MAX").put("m", "min").put("M", "MIN").put("c", "count").put("C", "COUNT").put("d", "distinct").put("D", "DISTINCT").put("f", "from").put("F", "FROM").put("o", "order by").put("O", "ORDER BY").put("d", "desc").put("d", "DESC").put("w", "where").put("W", "WHERE").put("r", "right join").put("R", "RIGHT JOIN").put("l", "limit").put("L", "LIMIT").put("h", "having").put("H", "HAVING").put("g", "group by").put("G", "GROUP BY").put("v", "values").put("V", "VALUES").put("d", "duplicate").put("D", "DUPLICATE").put("f", "for update").put("F", "FOR UPDATE").put("a", "asc").put("A", "ASC").put("u", "union").put("U", "UNION").put("r", "replace").put("R", "REPLACE").put("u", "using").put("U", "USING").build();
    private static List<String> jdbcType = Lists.newArrayList(new String[]{"CHAR", "VARCHAR", "LONGVARCHAR", "BIT", "TINYINT", "SMALLINT", "INTEGER", "BIGINT", "REAL", "DOUBLE", "FLOAT", "DECIMAL", "NUMERIC", "DATE", "TIME", "TIMESTAMP"});

    public MapperSqlCompletionContributor() {
    }

    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters == null) {
            $$$reportNull$$$0(0);
        }

        if (result == null) {
            $$$reportNull$$$0(1);
        }

        PsiElement positionElement = parameters.getOriginalPosition();
        if (positionElement != null) {
            PsiElement parent = positionElement.getParent();
            if (parent != null && parent instanceof XmlText) {
                PsiElement position = parameters.getPosition();
                String positionText = position.getText();
                int endPosition = parameters.getEditor().getCaretModel().getCurrentCaret().getSelectionStart();
                int startOffset = parameters.getPosition().getTextRange().getStartOffset();
                if (endPosition - startOffset >= 0) {
                    String realStart = positionText.substring(0, endPosition - startOffset);
                    PsiFile originalFile = parameters.getOriginalFile();
                    if (originalFile instanceof XmlFile) {
                        XmlFile xmlFile = (XmlFile)originalFile;
                        XmlTag rootTag1 = xmlFile.getRootTag();
                        if (rootTag1 != null) {
                            if (rootTag1.getName().equals("mapper")) {
                                int findFieldIndex = realStart.lastIndexOf("#{");
                                if (findFieldIndex != -1) {
                                    String laterString = realStart.substring(findFieldIndex);
                                    if (!laterString.contains("}")) {
                                        this.addRecommendInParam(result, positionElement, realStart, xmlFile, findFieldIndex);
                                        return;
                                    }
                                }

                                int m = realStart.lastIndexOf("`");
                                int startOffset1;
                                if (m != -1 && m > realStart.length() - 10) {
                                    String lastText = realStart.substring(m + 1);
                                    XmlTag[] subTags = rootTag1.getSubTags();
                                    List<ColumnAndField> columnAndFields = new ArrayList();
                                    XmlTag[] var18 = subTags;
                                    startOffset1 = subTags.length;

                                    for(int var20 = 0; var20 < startOffset1; ++var20) {
                                        XmlTag tag = var18[var20];
                                        if (tag.getName().equals("resultMap")) {
                                            columnAndFields.addAll(generateColumnNames(tag));
                                        }
                                    }

                                    Set<String> columns = extractColumn(columnAndFields);
                                    startOffset1 = findFindAlpha(realStart);
                                    columns.forEach((item) -> {
                                        if (result == null) {
                                            $$$reportNull$$$0(3);
                                        }

                                        boolean b = item.startsWith(lastText);
                                        if (b) {
                                            result.addElement(LookupElementBuilder.create(realStart.substring(startOffset1, m + 1) + item + "`"));
                                        }

                                    });
                                }

                                XmlTag currentElementXmlTag = MyPsiXmlUtils.findCurrentElementXmlTag(positionElement);
                                if (currentElementXmlTag != null) {
                                    String text = currentElementXmlTag.getText();
                                    XmlTagValue value = currentElementXmlTag.getValue();
                                    Document document = PsiDocumentManager.getInstance(parameters.getEditor().getProject()).getDocument(xmlFile);
                                    startOffset1 = value.getTextRange().getStartOffset();
                                    if (endPosition < startOffset1) {
                                        return;
                                    }

                                    String startText = document.getText(new TextRange(startOffset1, endPosition));
                                    String afterText = document.getText(new TextRange(endPosition, value.getTextRange().getEndOffset()));
                                    ParseContext context = this.buildParseContext(parameters.getEditor().getProject(), realStart, startText, afterText, value.getText(), endPosition - startOffset1, parameters.getCompletionType(), currentElementXmlTag, xmlFile);
                                    ParsedResult parse = SqlParser.parse(context);
                                    if (parse.getRecommedValues().size() > 0) {
                                        Iterator var24 = parse.getRecommedValues().iterator();

                                        while(var24.hasNext()) {
                                            LookupElement s = (LookupElement)var24.next();
                                            result.addElement(s);
                                        }

                                        return;
                                    }
                                }

                                if (realStart.length() == 1) {
                                    ImmutableList<String> recommends = multimap.get(realStart);
                                    UnmodifiableIterator var31 = recommends.iterator();

                                    while(var31.hasNext()) {
                                        String recommend = (String)var31.next();
                                        result.addElement(LookupElementBuilder.create(recommend + " "));
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private void addRecommendInParam(@NotNull CompletionResultSet result, PsiElement positionElement, String realStart, XmlFile xmlFile, int findFieldIndex) {
        if (result == null) {
            $$$reportNull$$$0(2);
        }

        if (!realStart.endsWith(",")) {
            String namespace = MyPsiXmlUtils.findCurrentXmlFileNameSpace(xmlFile);
            PsiClass namespaceClass = PsiClassUtil.findClassOfQuatifiedType(xmlFile, namespace);
            if (namespaceClass != null) {
                String methodName = MyPsiXmlUtils.findCurrentElementIntefaceMethodName(positionElement);
                if (!StringUtils.isBlank(methodName)) {
                    PsiMethod findMethod = PsiClassUtil.getClassMethodByMethodName(namespaceClass, methodName);
                    if (findMethod != null) {
                        List<String> lookUpResult = PsiClassUtil.extractMyBatisParam(findMethod);
                        String remaining = realStart.substring(findFieldIndex + 2);
                        int findAlpha = findFindAlpha(realStart);
                        String substring = "";
                        if (findAlpha != -1) {
                            substring = realStart.substring(findAlpha, findFieldIndex + 2);
                        }

                        Iterator var14 = lookUpResult.iterator();

                        while(var14.hasNext()) {
                            String s = (String)var14.next();
                            if (s.startsWith(remaining)) {
                                result.addElement(LookupElementBuilder.create(substring + s + "}"));
                            }
                        }

                    }
                }
            }
        } else {
            int findAlpha = findFindAlpha(realStart);
            String substring = "";
            if (findAlpha != -1) {
                substring = realStart.substring(findAlpha);
            }

            Iterator var8 = jdbcType.iterator();

            while(var8.hasNext()) {
                String s = (String)var8.next();
                result.addElement(LookupElementBuilder.create(substring + "jdbcType=" + s));
            }

        }
    }

    private ParseContext buildParseContext(Project project, String realStart, String startText, String afterText, String text, int i, CompletionType type, XmlTag currentTag, XmlFile xmlFile) {
        ParseContext context = new ParseContext();
        context.setCursorOffSet(i);
        context.setBeforeText(startText);
        context.setAfterText(afterText);
        context.setAllText(text);
        context.setCurrentWordStart(realStart);
        context.setProject(project);
        context.setCompletionType(type);
        context.setCurrentTag(currentTag);
        context.setCurrentXmlFile(xmlFile);
        return context;
    }

    private static int findFindAlpha(String realStart) {
        for(int i = 0; i < realStart.length(); ++i) {
            if (Character.isLetterOrDigit(realStart.charAt(i))) {
                return i;
            }
        }

        return -1;
    }

    private static Set<String> extractField(List<ColumnAndField> columnAndFields) {
        Set<String> fields = new HashSet();
        Iterator var2 = columnAndFields.iterator();

        while(var2.hasNext()) {
            ColumnAndField columnAndField = (ColumnAndField)var2.next();
            if (StringUtils.isNotBlank(columnAndField.getField())) {
                fields.add(columnAndField.getField());
            }
        }

        return fields;
    }

    private static Set<String> extractColumn(List<ColumnAndField> columnAndFields) {
        Set<String> columns = new HashSet();
        Iterator var2 = columnAndFields.iterator();

        while(var2.hasNext()) {
            ColumnAndField columnAndField = (ColumnAndField)var2.next();
            if (StringUtils.isNotBlank(columnAndField.getColumn())) {
                columns.add(columnAndField.getColumn());
            }
        }

        return columns;
    }

    private static List<ColumnAndField> generateColumnNames(XmlTag tag) {
        List<ColumnAndField> column = new ArrayList();
        if (tag.getSubTags() != null) {
            XmlTag[] var2 = tag.getSubTags();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                XmlTag subTag = var2[var4];
                ColumnAndField columnAndField = new ColumnAndField();
                String columnName = subTag.getAttributeValue("column");
                String field = subTag.getAttributeValue("property");
                columnAndField.setField(field);
                columnAndField.setColumn(columnName);
                column.add(columnAndField);
            }
        }

        return column;
    }
}
