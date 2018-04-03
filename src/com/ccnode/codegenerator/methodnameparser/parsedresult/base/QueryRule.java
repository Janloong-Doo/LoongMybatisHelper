//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.base;

public class QueryRule {
    private String prop;
    private String operator;
    private String connector;
    private boolean useIfTest;

    public QueryRule() {
    }

    public boolean isUseIfTest() {
        return this.useIfTest;
    }

    public void setUseIfTest(boolean useIfTest) {
        this.useIfTest = useIfTest;
    }

    public String getProp() {
        return this.prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getConnector() {
        return this.connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }
}
