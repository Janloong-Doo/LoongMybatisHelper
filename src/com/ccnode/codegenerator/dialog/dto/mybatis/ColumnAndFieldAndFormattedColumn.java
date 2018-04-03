//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog.dto.mybatis;

public class ColumnAndFieldAndFormattedColumn extends ColumnAndField {
    private String formattedColumn;
    private String jdbcType;

    public ColumnAndFieldAndFormattedColumn() {
    }

    public String getJdbcType() {
        return this.jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getFormattedColumn() {
        return this.formattedColumn;
    }

    public void setFormattedColumn(String formattedColumn) {
        this.formattedColumn = formattedColumn;
    }
}
