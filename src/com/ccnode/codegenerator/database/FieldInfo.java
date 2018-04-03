package com.ccnode.codegenerator.database;


/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:03
 */
public class FieldInfo {
    private String fieldName;
    private String fieldType;
    private String columnName;
    private String fieldModifier;

    public FieldInfo() {
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldModifier() {
        return this.fieldModifier;
    }

    public void setFieldModifier(String fieldModifier) {
        this.fieldModifier = fieldModifier;
    }
}
