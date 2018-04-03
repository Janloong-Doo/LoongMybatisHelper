//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.find;

import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedBase;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class ParsedFind extends ParsedBase {
    private List<FetchProp> fetchProps;
    private Boolean distinct = false;
    private Boolean withPage = false;
    private Boolean returnList = true;
    private List<GroupByRule> groupByProps;
    private Integer limit = -1;
    List<OrderByRule> orderByProps;

    public ParsedFind() {
    }

    public Boolean getWithPage() {
        return this.withPage;
    }

    public void setWithPage(Boolean withPage) {
        this.withPage = withPage;
    }

    public ParsedFind clone() {
        return (ParsedFind)Cloner.standard().deepClone(this);
    }

    public void addFetchProps(String props) {
        if (this.fetchProps == null) {
            this.fetchProps = new ArrayList();
        }

        FetchProp e = new FetchProp();
        e.setFetchProp(props);
        this.fetchProps.add(e);
    }

    public void addFunction(String function) {
        if (this.fetchProps == null) {
            this.fetchProps = new ArrayList();
        }

        FetchProp e = new FetchProp();
        e.setFetchFunction(function);
        this.fetchProps.add(e);
    }

    public void addFunctionProp(String functionProp) {
        if (this.fetchProps == null) {
            throw new RuntimeException("add function prop, the fetchProp shall not be empty");
        } else {
            FetchProp lastFecthProp = (FetchProp)this.fetchProps.get(this.fetchProps.size() - 1);
            lastFecthProp.setFetchProp(functionProp);
        }
    }

    public void addOrderByProp(String prop) {
        if (this.orderByProps == null) {
            this.orderByProps = new ArrayList();
        }

        OrderByRule rule = new OrderByRule();
        rule.setProp(prop);
        this.orderByProps.add(rule);
    }

    public void addOrderByPropOrder(String order) {
        OrderByRule rule = (OrderByRule)this.orderByProps.get(this.orderByProps.size() - 1);
        rule.setOrder(order);
    }

    public Boolean getDistinct() {
        return this.distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<FetchProp> getFetchProps() {
        return this.fetchProps;
    }

    public List<OrderByRule> getOrderByProps() {
        return this.orderByProps;
    }

    public List<GroupByRule> getGroupByProps() {
        return this.groupByProps;
    }

    public Boolean getReturnList() {
        return this.returnList;
    }

    public void setReturnList(Boolean returnList) {
        this.returnList = returnList;
    }
}
