package com.ccnode.codegenerator.database;


import com.ccnode.codegenerator.database.handler.DatabaseFactory;
import com.ccnode.codegenerator.database.handler.mysql.MysqlDatabaseFactory;
import com.ccnode.codegenerator.database.handler.oracle.OracleDatabaseFactory;
import com.ccnode.codegenerator.database.handler.sqlite.SqliteDatabaseFactory;
import com.ccnode.codegenerator.myconfigurable.MyBatisCodeHelperApplicationComponent;
import com.ccnode.codegenerator.myconfigurable.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:02
 */
public class DatabaseComponenent {
    private static Map<String, DatabaseFactory> databaseHandlerMap = new HashMap<String, DatabaseFactory>() {
        {
            this.put("MySql", new MysqlDatabaseFactory());
            this.put("Oracle", new OracleDatabaseFactory());
            this.put("Sqlite", new SqliteDatabaseFactory());
        }
    };
    private static MyBatisCodeHelperApplicationComponent myBatisCodeHelperApplicationComponent = MyBatisCodeHelperApplicationComponent.getInstance();

    public DatabaseComponenent() {
    }

    @NotNull
    public static DatabaseFactory currentHandler() {
        String database = myBatisCodeHelperApplicationComponent.getState().getDefaultProfile().getDatabase();
        DatabaseFactory var10000 = (DatabaseFactory)databaseHandlerMap.get(database);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            throw new RuntimeException("DatabaseComponenent currentHandler 有错误啊");

        }

        return var10000;
    }

    public static String currentDatabase() {
        return myBatisCodeHelperApplicationComponent.getState().getDefaultProfile().getDatabase();
    }

    public static boolean shouldAddIfTest() {
        return myBatisCodeHelperApplicationComponent.getState().getDefaultProfile().getGenerateWithIfTest();
    }

    public static Profile getProfile() {
        return myBatisCodeHelperApplicationComponent.getState().getDefaultProfile();
    }

    public static String formatColumn(String column) {
        if (currentDatabase().equals("MySql")) {
            return myBatisCodeHelperApplicationComponent.getState().getDefaultProfile().getMysqlUseWithDash() ? "`" + column + "`" : column;
        } else {
            return column;
        }
    }
}
