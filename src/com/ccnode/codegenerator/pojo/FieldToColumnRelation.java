//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.pojo;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import java.util.Map;

public class FieldToColumnRelation {
    private String resultMapId;
    private Boolean hasFullRelation;
    private Boolean hasJavaTypeResultMap;
    private Map<String, String> filedToColumnMap;
    private Map<String, String> fieldToJdbcTypeMap;

    public FieldToColumnRelation() {
    }

    public Map<String, String> getFieldToJdbcTypeMap() {
        return this.fieldToJdbcTypeMap;
    }

    public void setFieldToJdbcTypeMap(Map<String, String> fieldToJdbcTypeMap) {
        this.fieldToJdbcTypeMap = fieldToJdbcTypeMap;
    }

    public String getResultMapId() {
        return this.resultMapId;
    }

    public void setResultMapId(String resultMapId) {
        this.resultMapId = resultMapId;
    }

    public Map<String, String> getFiledToColumnMap() {
        return this.filedToColumnMap;
    }

    public void setFiledToColumnMap(Map<String, String> filedToColumnMap) {
        this.filedToColumnMap = filedToColumnMap;
    }

    public String getPropFormatColumn(String prop) {
        String s = (String)this.filedToColumnMap.get(prop.toLowerCase());
        return DatabaseComponenent.formatColumn(s);
    }

    public Boolean getHasFullRelation() {
        return this.hasFullRelation;
    }

    public void setHasFullRelation(Boolean hasFullRelation) {
        this.hasFullRelation = hasFullRelation;
    }

    public String getPropColumn(String prop) {
        return (String)this.filedToColumnMap.get(prop.toLowerCase());
    }

    public Boolean getHasJavaTypeResultMap() {
        return this.hasJavaTypeResultMap;
    }

    public void setHasJavaTypeResultMap(Boolean hasJavaTypeResultMap) {
        this.hasJavaTypeResultMap = hasJavaTypeResultMap;
    }

    public String getJdbcType(String prop) {
        String s = (String)this.fieldToJdbcTypeMap.get(prop.toLowerCase());
        return s == null ? "" : ",jdbcType=" + s;
    }
}
