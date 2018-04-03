//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow.dbInfo;

import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.google.common.collect.Lists;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications.Bus;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseConnector {
    public DatabaseConnector() {
    }

    public static DatabaseInfo getDataBaseInfoFromConnection(DatasourceConnectionProperty datasourceConnectionProperty) {
        Connection conn = null;
        Statement stmt = null;
        DatabaseInfo databaseInfo = null;

        try {
            conn = getConnection(datasourceConnectionProperty);
            DatabaseMetaData metaData = conn.getMetaData();
            String schemaName = datasourceConnectionProperty.getDatabase();
            databaseInfo = new DatabaseInfo();
            databaseInfo.setDatabaseName(schemaName);
            List<TableInfo> tableInfos = Lists.newArrayList();
            ResultSet tableTypes = metaData.getTableTypes();
            ArrayList tableTypeList = Lists.newArrayList();

            while(tableTypes.next()) {
                tableTypeList.add(tableTypes.getString(1));
            }

            ResultSet tables = metaData.getTables((String)null, schemaName, "%", (String[])null);
            ArrayList tableList = Lists.newArrayList();

            while(tables.next()) {
                tableList.add(tables.getString(3));
            }

            Iterator var11 = tableList.iterator();

            while(var11.hasNext()) {
                String table = (String)var11.next();
                TableInfo info1 = new TableInfo();
                info1.setTableName(table);
                List<TableColumnInfo> tableColumnInfos = Lists.newArrayList();
                ResultSet columns = metaData.getColumns((String)null, schemaName, table, "%");

                while(columns.next()) {
                    String columnName = columns.getString(4);
                    TableColumnInfo columnInfo = new TableColumnInfo();
                    String columnType = columns.getString(6);
                    columnInfo.setFieldName(columnName);
                    columnInfo.setFieldType(columnType);
                    tableColumnInfos.add(columnInfo);
                }

                info1.setTableColumnInfos(tableColumnInfos);
                tableInfos.add(info1);
            }

            databaseInfo.setTableInfoList(tableInfos);
            System.out.println("Creating statement...");
            conn.close();
        } catch (SQLException var34) {
            Bus.notify(new Notification("mybatisDb", "can't connect to db", "connect to " + datasourceConnectionProperty.getUrl() + " with userName " + datasourceConnectionProperty.getUserName() + " with password" + datasourceConnectionProperty.getPassword() + " fail", NotificationType.ERROR, (NotificationListener)null));
        } catch (Exception var35) {
            Bus.notify(new Notification("mybatisDb", "can't connect to db", "connect to " + datasourceConnectionProperty.getUrl() + " with userName " + datasourceConnectionProperty.getUserName() + " with password" + datasourceConnectionProperty.getPassword() + " fail", NotificationType.ERROR, (NotificationListener)null));
        } finally {
            try {
                if (stmt != null) {
                    ((Statement)stmt).close();
                }
            } catch (SQLException var33) {
                ;
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var32) {
                var32.printStackTrace();
            }

        }

        return databaseInfo;
    }

    private static Connection getConnection(DatasourceConnectionProperty datasourceConnectionProperty) throws ClassNotFoundException, SQLException {
        DriverManager.setLoginTimeout(1);
        String className = buildDriverClassNameByDatabaseType(datasourceConnectionProperty.getDatabaseType());
        Class.forName(className);
        String realUrl = buildUrl(datasourceConnectionProperty.getDatabaseType(), datasourceConnectionProperty.getUrl(), datasourceConnectionProperty.getDatabase());
        return DriverManager.getConnection(realUrl, datasourceConnectionProperty.getUserName(), datasourceConnectionProperty.getPassword());
    }

    private static String buildDriverClassNameByDatabaseType(String databaseType) {
        DBType dbType = DBType.valueOf(databaseType);
        return dbType.getDriverClass();
    }

    public static boolean checkConnection(String databaseType, String url, String userName, String password, String database) {
        String realUrl = buildUrl(databaseType, url, database);
        Connection conn = null;
        Statement stmt = null;
        Object var8 = null;

        boolean var10;
        try {
            Class.forName(buildDriverClassNameByDatabaseType(databaseType));
            System.out.println("Connecting to databaseType...");
            conn = DriverManager.getConnection(realUrl, userName, password);
            return true;
        } catch (Exception var20) {
            var10 = false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return var10;
    }

    public static String buildUrl(String databaseType, String url, String database) {
        return databaseType.equals("MySql") ? "jdbc:mysql://" + url + "/" + database : "jdbc:mysql://" + url + "/" + database;
    }

    public static List<TableColumnInfo> getTableColumnInfo(NewDatabaseInfo info, String tableName) {
        Connection conn = null;
        Statement stmt = null;
        DatabaseInfo databaseInfo = null;
        ArrayList tableColumnInfos = Lists.newArrayList();

        try {
            try {
                Class.forName(buildDriverClassNameByDatabaseType(info.getDatabaseType()));
                System.out.println("Connecting to databaseType...");
                String realUrl = buildUrl(info.getDatabaseType(), info.getUrl(), info.getDatabase());
                conn = DriverManager.getConnection(realUrl, info.getUserName(), info.getPassword());
                DatabaseMetaData metaData = conn.getMetaData();
                String schemaName = info.getDatabase();
                databaseInfo = new DatabaseInfo();
                databaseInfo.setDatabaseName(schemaName);
                List<TableInfo> tableInfos = Lists.newArrayList();
                ResultSet tableTypes = metaData.getTableTypes();
                ArrayList tableTypeList = Lists.newArrayList();

                while(tableTypes.next()) {
                    tableTypeList.add(tableTypes.getString(1));
                }

                ResultSet tables = metaData.getTables((String)null, schemaName, tableName, (String[])null);
                ArrayList tableList = Lists.newArrayList();

                while(tables.next()) {
                    tableList.add(tables.getString(3));
                }

                if (tableList.size() != 1) {
                    System.out.println("table is not only one");
                    ArrayList var40 = Lists.newArrayList();
                    return var40;
                }

                String table = (String)tableList.get(0);
                TableInfo info1 = new TableInfo();
                info1.setTableName(table);
                ResultSet columns = metaData.getColumns((String)null, schemaName, table, "%");

                while(columns.next()) {
                    String columnName = columns.getString(4);
                    TableColumnInfo columnInfo = new TableColumnInfo();
                    String columnType = columns.getString(6);
                    columnInfo.setFieldName(columnName);
                    columnInfo.setFieldType(columnType);
                    tableColumnInfos.add(columnInfo);
                }

                conn.close();
                ArrayList var41 = tableColumnInfos;
                return var41;
            } catch (SQLException var37) {
                Bus.notify(new Notification("mybatisDb", "can't connect to db", "connect to " + info.getUrl() + " with userName " + info.getUserName() + " with password" + info.getPassword() + " fail", NotificationType.ERROR, (NotificationListener)null));
            } catch (Exception var38) {
                Bus.notify(new Notification("mybatisDb", "can't connect to db", "connect to " + info.getUrl() + " with userName " + info.getUserName() + " with password" + info.getPassword() + " fail", NotificationType.ERROR, (NotificationListener)null));
            }

            return tableColumnInfos;
        } finally {
            try {
                if (stmt != null) {
                    ((Statement)stmt).close();
                }
            } catch (SQLException var36) {
                ;
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var35) {
                var35.printStackTrace();
            }

        }
    }
}
