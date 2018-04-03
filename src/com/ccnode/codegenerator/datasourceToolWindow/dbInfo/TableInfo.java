//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow.dbInfo;

import java.util.List;

public class TableInfo {
    private String tableName;
    private List<TableColumnInfo> tableColumnInfos;

    public TableInfo() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumnInfo> getTableColumnInfos() {
        return this.tableColumnInfos;
    }

    public void setTableColumnInfos(List<TableColumnInfo> tableColumnInfos) {
        this.tableColumnInfos = tableColumnInfos;
    }
}
