//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.buidler;

import com.ccnode.codegenerator.constants.SqlTypeEnum;
import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QueryInfo {
    private String returnClass;
    private List<ParamInfo> paramInfos;
    private String methodReturnType;
    private String sql;
    private String id;
    private String returnMap;
    private SqlTypeEnum type;
    private Set<String> importList;
    private ParsedBase parseQuery;

    public QueryInfo() {
    }

    public ParsedBase getParseQuery() {
        return this.parseQuery;
    }

    public void setParseQuery(ParsedBase parseQuery) {
        this.parseQuery = parseQuery;
    }

    public Set<String> getImportList() {
        return this.importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public SqlTypeEnum getType() {
        return this.type;
    }

    public void setType(SqlTypeEnum type) {
        this.type = type;
    }

    public String getMethodReturnType() {
        return this.methodReturnType;
    }

    public void setMethodReturnType(String methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public String getReturnClass() {
        return this.returnClass;
    }

    public void setReturnClass(String returnClass) {
        this.returnClass = returnClass;
    }

    public List<ParamInfo> getParamInfos() {
        return this.paramInfos;
    }

    public void setParamInfos(List<ParamInfo> paramInfos) {
        this.paramInfos = paramInfos;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReturnMap() {
        return this.returnMap;
    }

    public void setReturnMap(String returnMap) {
        this.returnMap = returnMap;
    }

    public void addParams(ParamInfo info) {
        if (this.paramInfos == null) {
            this.paramInfos = new ArrayList();
        }

        this.paramInfos.add(info);
    }
}
