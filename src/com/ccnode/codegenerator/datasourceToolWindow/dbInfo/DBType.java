//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow.dbInfo;

import com.google.common.collect.Lists;
import java.util.List;

public enum DBType {
    Oracle("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@%s:%s:%s", "oracleJDBC.jar"),
    MySql("com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=%s", "mysqlJDBC.jar"),
    SqlServer("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://%s:%s;databaseName=%s", "sqlserverJDBC.jar"),
    PostgreSQL("org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s", "postgresqlJDBC.jar"),
    Sqlite("org.postgresql.Driver", "jdbc:sqlite://%s", "sqliteJDBC.jar");

    private final String driverClass;
    private final String connectionUrlPattern;
    private final String connectorJarFile;

    private DBType(String driverClass, String connectionUrlPattern, String connectorJarFile) {
        this.driverClass = driverClass;
        this.connectionUrlPattern = connectionUrlPattern;
        this.connectorJarFile = connectorJarFile;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public String getConnectionUrlPattern() {
        return this.connectionUrlPattern;
    }

    public String getConnectorJarFile() {
        return this.connectorJarFile;
    }

    public static List<String> getAllDatabaseSources() {
        List<String> dbs = Lists.newArrayList();
        DBType[] values = values();
        DBType[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            DBType value = var2[var4];
            dbs.add(value.name());
        }

        return dbs;
    }
}
