//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.BaseQueryBuilder;
import com.ccnode.codegenerator.database.handler.QueryBuilderHandler;
import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.FetchProp;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.OrderByRule;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqliteQueryBuilderHandler implements QueryBuilderHandler {
    public SqliteQueryBuilderHandler() {
    }

    public void handleFindBeforeFromTable(QueryInfo info, MethodNameParsedResult parsedResult, boolean queryAllTable) {
        ParsedFind find = parsedResult.getParsedFind();
        FieldToColumnRelation relation = parsedResult.getRelation();
        StringBuilder builder = new StringBuilder();
        if (queryAllTable) {
            builder.append("\n\tselect <include refid=\"" + parsedResult.getAllColumnName() + "\"/>");
        } else {
            builder.append("\n\tselect");
            Iterator var7;
            FetchProp prop;
            if (find.getDistinct()) {
                builder.append(" distinct(");
                var7 = find.getFetchProps().iterator();

                while(var7.hasNext()) {
                    prop = (FetchProp)var7.next();
                    builder.append(relation.getPropFormatColumn(prop.getFetchProp()) + ",");
                }

                builder.deleteCharAt(builder.length() - 1);
                builder.append(")");
            } else {
                var7 = find.getFetchProps().iterator();

                while(var7.hasNext()) {
                    prop = (FetchProp)var7.next();
                    if (prop.getFetchFunction() == null) {
                        builder.append(" " + relation.getPropFormatColumn(prop.getFetchProp()) + ",");
                    }
                }

                builder.deleteCharAt(builder.length() - 1);
            }
        }

        info.setSql(builder.toString());
    }

    public void handleFindAfterFromTable(QueryInfo info, MethodNameParsedResult parsedResult) {
        ParsedFind find = parsedResult.getParsedFind();
        if (find.getQueryRules() != null) {
            info.setParamInfos(new ArrayList());
            (new BaseQueryBuilder(this)).buildQuerySqlAndParam(find.getQueryRules(), info, parsedResult.getFieldMap(), parsedResult.getRelation());
        }

        if (find.getOrderByProps() != null) {
            info.setSql(info.getSql() + " order by");
            List<OrderByRule> orderByProps = find.getOrderByProps();

            for(int i = 0; i < orderByProps.size(); ++i) {
                OrderByRule rule = (OrderByRule)orderByProps.get(i);
                info.setSql(info.getSql() + " " + parsedResult.getRelation().getPropFormatColumn(rule.getProp()) + " " + rule.getOrder());
                if (i != orderByProps.size() - 1) {
                    info.setSql(info.getSql() + ",");
                }
            }
        }

        if (find.getLimit() > 0) {
            if (find.getQueryRules() != null && find.getQueryRules().size() > 0) {
                info.setSql(info.getSql() + " and ROWNUM " + BaseQueryBuilder.cdata("<=") + find.getLimit());
            } else {
                info.setSql(info.getSql() + " ROWNUM " + BaseQueryBuilder.cdata("<=") + find.getLimit());
            }
        }

    }

    public void handleUpdate(QueryInfo info, MethodNameParsedResult parsedResult) {
    }

    public void handleDelete(QueryInfo info, MethodNameParsedResult parsedResult) {
    }

    public void handleCount(QueryInfo queryInfo, MethodNameParsedResult parsedResult) {
    }
}
