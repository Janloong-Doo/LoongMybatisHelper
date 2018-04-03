//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser;

import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;
import java.util.List;

public class QueryParseDto {
    private QueryInfo queryInfo;
    private Boolean hasMatched = false;
    private Boolean displayErrorMsg = true;
    private List<String> errorMsg;

    public QueryParseDto() {
    }

    public Boolean getDisplayErrorMsg() {
        return this.displayErrorMsg;
    }

    public void setDisplayErrorMsg(Boolean displayErrorMsg) {
        this.displayErrorMsg = displayErrorMsg;
    }

    public QueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public void setQueryInfo(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public Boolean getHasMatched() {
        return this.hasMatched;
    }

    public void setHasMatched(Boolean hasMatched) {
        this.hasMatched = hasMatched;
    }

    public List<String> getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }
}
