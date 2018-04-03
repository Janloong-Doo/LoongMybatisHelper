//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.DbUtils;
import com.ccnode.codegenerator.database.handler.BaseQueryBuilder;
import com.ccnode.codegenerator.database.handler.QueryBuilderHandler;
import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.FetchProp;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.OrderByRule;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import java.util.Iterator;
import java.util.List;

public class MysqlQueryBuilderHandler implements QueryBuilderHandler {
    public MysqlQueryBuilderHandler() {
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
                    } else {
                        handleWithFunction(relation, builder, prop);
                    }
                }

                builder.deleteCharAt(builder.length() - 1);
            }
        }

        info.setSql(builder.toString());
    }

    public static void handleWithFunction(FieldToColumnRelation relation, StringBuilder builder, FetchProp prop) {
        String returnVal = DbUtils.buildSelectFunctionVal(prop);
        String var4 = prop.getFetchFunction();
        byte var5 = -1;
        switch(var4.hashCode()) {
            case 96978:
                if (var4.equals("avg")) {
                    var5 = 2;
                }
                break;
            case 107876:
                if (var4.equals("max")) {
                    var5 = 0;
                }
                break;
            case 108114:
                if (var4.equals("min")) {
                    var5 = 1;
                }
                break;
            case 114251:
                if (var4.equals("sum")) {
                    var5 = 3;
                }
        }

        switch(var5) {
            case 0:
                builder.append(String.format(" max(%s) as %s,", relation.getPropFormatColumn(prop.getFetchProp()), returnVal));
                break;
            case 1:
                builder.append(String.format(" min(%s) as %s,", relation.getPropFormatColumn(prop.getFetchProp()), returnVal));
                break;
            case 2:
                builder.append(String.format(" avg(%s) as %s,", relation.getPropFormatColumn(prop.getFetchProp()), returnVal));
                break;
            case 3:
                builder.append(String.format(" sum(%s) as %s,", relation.getPropFormatColumn(prop.getFetchProp()), returnVal));
        }

    }

    public void handleFindAfterFromTable(QueryInfo info, MethodNameParsedResult parsedResult) {
        ParsedFind find = parsedResult.getParsedFind();
        if (find.getQueryRules() != null) {
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
            info.setSql(info.getSql() + " limit " + find.getLimit());
        }

    }

    public void handleUpdate(QueryInfo info, MethodNameParsedResult parsedResult) {
    }

    public void handleDelete(QueryInfo info, MethodNameParsedResult parsedResult) {
    }

    public void handleCount(QueryInfo queryInfo, MethodNameParsedResult parsedResult) {
    }
}
