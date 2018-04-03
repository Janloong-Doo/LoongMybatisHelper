//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.base;

import com.ccnode.codegenerator.dialog.ParseTypeEnum;
import java.util.ArrayList;
import java.util.List;

public class ParsedBase {
    protected List<QueryRule> queryRules;
    protected String parsedResult;

    public ParsedBase() {
    }

    public String getParsedResult() {
        return this.parsedResult;
    }

    public void setParsedResult(String parsedResult) {
        this.parsedResult = parsedResult;
    }

    public void addParsePart(ParseTypeEnum parseTypeEnum, String value) {
        if (this.parsedResult == null) {
            this.parsedResult = "| parsedType:" + parseTypeEnum.name() + " parse value:" + value;
        } else {
            this.parsedResult = this.parsedResult + "| parsedType:" + parseTypeEnum.name() + " parse value:" + value;
        }

    }

    public void addQueryProp(String queryProp) {
        if (this.queryRules == null) {
            this.queryRules = new ArrayList();
        }

        QueryRule rule = new QueryRule();
        rule.setProp(queryProp);
        this.queryRules.add(rule);
    }

    public void addQueryOperator(String operator) {
        QueryRule rule = (QueryRule)this.queryRules.get(this.queryRules.size() - 1);
        rule.setOperator(operator);
    }

    public void addConnector(String connector) {
        QueryRule rule = (QueryRule)this.queryRules.get(this.queryRules.size() - 1);
        rule.setConnector(connector);
    }

    public List<QueryRule> getQueryRules() {
        return this.queryRules;
    }
}
