//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CompleteField {
    private String databaseType;
    private String tableName;
    private String fieldName;

    public CompleteField(String databaseType, String tableName, String fieldName) {
        this.databaseType = databaseType;
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CompleteField that = (CompleteField)o;
            return (new EqualsBuilder()).append(this.databaseType, that.databaseType).append(this.tableName, that.tableName).append(this.fieldName, that.fieldName).isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.databaseType).append(this.tableName).append(this.fieldName).toHashCode();
    }
}
