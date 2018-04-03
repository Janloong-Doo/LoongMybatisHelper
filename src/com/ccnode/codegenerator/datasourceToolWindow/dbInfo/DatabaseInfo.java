//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow.dbInfo;

import java.util.List;

public class DatabaseInfo {
    private String databaseName;
    private List<TableInfo> tableInfoList;

    public DatabaseInfo() {
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public List<TableInfo> getTableInfoList() {
        return this.tableInfoList;
    }

    public void setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
    }
}
