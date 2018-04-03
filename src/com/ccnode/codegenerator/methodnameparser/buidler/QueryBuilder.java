//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.buidler;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.ChooseParsedResultToUseDialog;
import com.ccnode.codegenerator.methodnameparser.QueryParseDto;
import com.ccnode.codegenerator.methodnameparser.dailog.AddIfTestDialog;
import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedBase;
import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedErrorBase;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCount;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCountError;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDelete;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDeleteError;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFindError;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdate;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdateError;
import com.ccnode.codegenerator.pojo.MethodXmlPsiInfo;
import com.ccnode.codegenerator.util.CollectionUtils;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class QueryBuilder {
    public QueryBuilder() {
    }

    public static QueryParseDto buildFindResult(List<ParsedFind> parsedFinds, List<ParsedFindError> errors, MethodXmlPsiInfo info) {
        QueryParseDto dto = new QueryParseDto();
        ArrayList strings;
        if (parsedFinds.size() == 0) {
            dto.setHasMatched(false);
            strings = new ArrayList();
            Iterator var8 = errors.iterator();

            while(var8.hasNext()) {
                ParsedFindError error = (ParsedFindError)var8.next();
                String errorMsg = buildErrorMsg(error);
                strings.add(errorMsg);
            }

            dto.setErrorMsg(strings);
            return dto;
        } else {
            strings = Lists.newArrayList();
            ParsedFind choosedFind = null;
            if (parsedFinds.size() > 1) {
                for(int i = 0; i < parsedFinds.size(); ++i) {
                    strings.add(((ParsedFind)parsedFinds.get(i)).getParsedResult());
                }

                ChooseParsedResultToUseDialog chooseParsedResultToUseDialog = new ChooseParsedResultToUseDialog(info.getProject(), strings);
                boolean b = chooseParsedResultToUseDialog.showAndGet();
                if (!b) {
                    dto.setHasMatched(false);
                    dto.setDisplayErrorMsg(false);
                    return dto;
                }

                choosedFind = (ParsedFind)parsedFinds.get(chooseParsedResultToUseDialog.getChoosedIndex());
            } else {
                choosedFind = (ParsedFind)parsedFinds.get(0);
            }

            addIfTestToQueryRule(choosedFind, info.getProject());
            QueryInfo e = DatabaseComponenent.currentHandler().getMethodXmlHandler().buildQueryInfoByMethodNameParsedResult(convertFromParsedFind(choosedFind, info));
            if (e != null) {
                dto.setQueryInfo(e);
                dto.setHasMatched(true);
            } else {
                dto.setHasMatched(false);
                dto.setDisplayErrorMsg(false);
            }

            return dto;
        }
    }

    private static void addIfTestToQueryRule(ParsedBase choosedFind, Project project) {
        if (!CollectionUtils.isEmpty(choosedFind.getQueryRules()) && DatabaseComponenent.shouldAddIfTest()) {
            AddIfTestDialog dialog = new AddIfTestDialog(project, choosedFind.getQueryRules());
            boolean b = dialog.showAndGet();
        }
    }

    private static MethodNameParsedResult convertFromParsedFind(ParsedFind find, MethodXmlPsiInfo info) {
        MethodNameParsedResult parsedResult = convertFromInfo(info);
        parsedResult.setParsedType(ParsedMethodEnum.FIND);
        parsedResult.setParsedFind(find);
        return parsedResult;
    }

    private static MethodNameParsedResult convertFromParsedUpdate(ParsedUpdate update, MethodXmlPsiInfo info) {
        MethodNameParsedResult parsedResult = convertFromInfo(info);
        parsedResult.setParsedType(ParsedMethodEnum.UPDATE);
        parsedResult.setParsedUpdate(update);
        return parsedResult;
    }

    private static MethodNameParsedResult convertFromParsedDelete(ParsedDelete delete, MethodXmlPsiInfo info) {
        MethodNameParsedResult parsedResult = convertFromInfo(info);
        parsedResult.setParsedType(ParsedMethodEnum.DELETE);
        parsedResult.setParsedDelete(delete);
        return parsedResult;
    }

    private static MethodNameParsedResult convertFromParsedCount(ParsedCount count, MethodXmlPsiInfo info) {
        MethodNameParsedResult parsedResult = convertFromInfo(info);
        parsedResult.setParsedType(ParsedMethodEnum.COUNT);
        parsedResult.setParsedCount(count);
        return parsedResult;
    }

    private static MethodNameParsedResult convertFromInfo(MethodXmlPsiInfo info) {
        MethodNameParsedResult methodNameParsedResult = new MethodNameParsedResult();
        methodNameParsedResult.setMethodName(info.getMethodName());
        methodNameParsedResult.setRelation(info.getRelation());
        methodNameParsedResult.setTableName(info.getTableName());
        methodNameParsedResult.setFieldMap(info.getFieldMap());
        methodNameParsedResult.setPsiClassName(info.getPsiClassName());
        methodNameParsedResult.setPsiClassFullName(info.getPsiClassFullName());
        methodNameParsedResult.setSrcClass(info.getSrcClass());
        methodNameParsedResult.setProject(info.getProject());
        methodNameParsedResult.setMybatisXmlFile(info.getMybatisXmlFile());
        methodNameParsedResult.setAllColumnName(info.getAllColumnName());
        return methodNameParsedResult;
    }

    private static String buildErrorMsg(ParsedErrorBase error) {
        return StringUtils.isEmpty(error.getRemaining()) ? "the query not end legal" : "the remaining " + error.getRemaining() + " can't be parsed";
    }

    public static QueryParseDto buildUpdateResult(List<ParsedUpdate> updateList, List<ParsedUpdateError> errorList, MethodXmlPsiInfo info) {
        QueryParseDto dto = new QueryParseDto();
        ArrayList strings;
        if (updateList.size() == 0) {
            dto.setHasMatched(false);
            strings = new ArrayList();
            Iterator var8 = errorList.iterator();

            while(var8.hasNext()) {
                ParsedUpdateError error = (ParsedUpdateError)var8.next();
                strings.add(buildErrorMsg(error));
            }

            dto.setErrorMsg(strings);
            return dto;
        } else {
            strings = Lists.newArrayList();
            ParsedUpdate choosedFind = null;
            if (updateList.size() > 1) {
                for(int i = 0; i < updateList.size(); ++i) {
                    strings.add(((ParsedUpdate)updateList.get(i)).getParsedResult());
                }

                ChooseParsedResultToUseDialog chooseParsedResultToUseDialog = new ChooseParsedResultToUseDialog(info.getProject(), strings);
                boolean b = chooseParsedResultToUseDialog.showAndGet();
                if (!b) {
                    dto.setHasMatched(false);
                    dto.setDisplayErrorMsg(false);
                    return dto;
                }

                choosedFind = (ParsedUpdate)updateList.get(chooseParsedResultToUseDialog.getChoosedIndex());
            } else {
                choosedFind = (ParsedUpdate)updateList.get(0);
            }

            QueryInfo e = DatabaseComponenent.currentHandler().getMethodXmlHandler().buildQueryInfoByMethodNameParsedResult(convertFromParsedUpdate(choosedFind, info));
            if (e != null) {
                dto.setQueryInfo(e);
                dto.setHasMatched(true);
            } else {
                dto.setHasMatched(false);
                dto.setDisplayErrorMsg(false);
            }

            return dto;
        }
    }

    public static QueryParseDto buildDeleteResult(List<ParsedDelete> parsedDeletes, List<ParsedDeleteError> errors, MethodXmlPsiInfo info) {
        QueryParseDto dto = new QueryParseDto();
        ArrayList strings;
        Iterator choosedFind;
        if (parsedDeletes.size() == 0) {
            dto.setHasMatched(false);
            strings = new ArrayList();
            choosedFind = errors.iterator();

            while(choosedFind.hasNext()) {
                ParsedDeleteError error = (ParsedDeleteError)choosedFind.next();
                strings.add(buildErrorMsg(error));
            }

            dto.setErrorMsg(strings);
        }

        strings = Lists.newArrayList();
        choosedFind = null;
        ParsedDelete choosedFind;
        if (parsedDeletes.size() > 1) {
            for(int i = 0; i < parsedDeletes.size(); ++i) {
                strings.add(((ParsedDelete)parsedDeletes.get(i)).getParsedResult());
            }

            ChooseParsedResultToUseDialog chooseParsedResultToUseDialog = new ChooseParsedResultToUseDialog(info.getProject(), strings);
            boolean b = chooseParsedResultToUseDialog.showAndGet();
            if (!b) {
                dto.setHasMatched(false);
                dto.setDisplayErrorMsg(false);
                return dto;
            }

            choosedFind = (ParsedDelete)parsedDeletes.get(chooseParsedResultToUseDialog.getChoosedIndex());
        } else {
            choosedFind = (ParsedDelete)parsedDeletes.get(0);
        }

        QueryInfo e = DatabaseComponenent.currentHandler().getMethodXmlHandler().buildQueryInfoByMethodNameParsedResult(convertFromParsedDelete(choosedFind, info));
        if (e != null) {
            dto.setQueryInfo(e);
            dto.setHasMatched(true);
        } else {
            dto.setHasMatched(false);
            dto.setDisplayErrorMsg(false);
        }

        return dto;
    }

    public static QueryParseDto buildCountResult(List<ParsedCount> parsedCounts, List<ParsedCountError> errors, MethodXmlPsiInfo info) {
        QueryParseDto dto = new QueryParseDto();
        ArrayList strings;
        if (parsedCounts.size() == 0) {
            dto.setHasMatched(false);
            strings = new ArrayList();
            Iterator var8 = errors.iterator();

            while(var8.hasNext()) {
                ParsedCountError error = (ParsedCountError)var8.next();
                strings.add(buildErrorMsg(error));
            }

            dto.setErrorMsg(strings);
            return dto;
        } else {
            strings = Lists.newArrayList();
            ParsedCount choosedFind = null;
            if (parsedCounts.size() > 1) {
                for(int i = 0; i < parsedCounts.size(); ++i) {
                    strings.add(((ParsedCount)parsedCounts.get(i)).getParsedResult());
                }

                ChooseParsedResultToUseDialog chooseParsedResultToUseDialog = new ChooseParsedResultToUseDialog(info.getProject(), strings);
                boolean b = chooseParsedResultToUseDialog.showAndGet();
                if (!b) {
                    dto.setHasMatched(false);
                    dto.setDisplayErrorMsg(false);
                    return dto;
                }

                choosedFind = (ParsedCount)parsedCounts.get(chooseParsedResultToUseDialog.getChoosedIndex());
            } else {
                choosedFind = (ParsedCount)parsedCounts.get(0);
            }

            QueryInfo e = DatabaseComponenent.currentHandler().getMethodXmlHandler().buildQueryInfoByMethodNameParsedResult(convertFromParsedCount(choosedFind, info));
            if (e != null) {
                dto.setQueryInfo(e);
                dto.setHasMatched(true);
            } else {
                dto.setHasMatched(false);
                dto.setDisplayErrorMsg(false);
            }

            return dto;
        }
    }
}
