//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

public class GenCodeProp {
    private String fieldName;
    private String columnName;
    private Boolean primaryKey;
    private Boolean unique;
    private String size;
    private String filedType;
    private String defaultValue;
    private Boolean canBeNull;
    private String jdbcType;
    private Boolean index;
    private Boolean hasDefaultValue;
    private String comment;

    public GenCodeProp() {
    }

    public String getJdbcType() {
        return this.jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Boolean getIndex() {
        return this.index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    public Boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }

    public void setHasDefaultValue(Boolean hasDefaultValue) {
        this.hasDefaultValue = hasDefaultValue;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getCanBeNull() {
        return this.canBeNull;
    }

    public void setCanBeNull(Boolean canBeNull) {
        this.canBeNull = canBeNull;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Boolean getPrimaryKey() {
        return this.primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getUnique() {
        return this.unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFiledType() {
        return this.filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
