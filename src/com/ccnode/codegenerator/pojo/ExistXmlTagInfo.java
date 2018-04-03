//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.pojo;

public class ExistXmlTagInfo {
    private String tableName;
    private FieldToColumnRelation fieldToColumnRelation;
    private boolean hasAllColumn;
    private String allColumnSqlId;
    private boolean hasResultMap;

    public ExistXmlTagInfo() {
    }

    public boolean isHasResultMap() {
        return this.hasResultMap;
    }

    public void setHasResultMap(boolean hasResultMap) {
        this.hasResultMap = hasResultMap;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public FieldToColumnRelation getFieldToColumnRelation() {
        return this.fieldToColumnRelation;
    }

    public void setFieldToColumnRelation(FieldToColumnRelation fieldToColumnRelation) {
        this.fieldToColumnRelation = fieldToColumnRelation;
    }

    public boolean isHasAllColumn() {
        return this.hasAllColumn;
    }

    public void setHasAllColumn(boolean hasAllColumn) {
        this.hasAllColumn = hasAllColumn;
    }

    public String getAllColumnSqlId() {
        return this.allColumnSqlId;
    }

    public void setAllColumnSqlId(String allColumnSqlId) {
        this.allColumnSqlId = allColumnSqlId;
    }
}
