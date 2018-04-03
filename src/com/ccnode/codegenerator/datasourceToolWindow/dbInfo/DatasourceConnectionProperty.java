//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow.dbInfo;

public class DatasourceConnectionProperty {
    private final String databaseType;
    private final String url;
    private final String userName;
    private final String password;
    private final String database;

    public DatasourceConnectionProperty(String databaseType, String url, String userName, String password, String database) {
        this.databaseType = databaseType;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.database = database;
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDatabase() {
        return this.database;
    }
}
