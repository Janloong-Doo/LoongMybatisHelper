//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.utils;

import com.ccnode.codegenerator.database.handler.mysql.MysqlHandlerUtils;
import com.ccnode.codegenerator.database.handler.mysql.UnsignedCheckResult;
import java.util.HashMap;
import java.util.Map;

public class JdbcTypeUtils {
    private static Map<String, String> fieldToJdbcType = new HashMap<String, String>() {
        {
            this.put("INT", "INTEGER");
            this.put("TINYINT", "TINYINT");
            this.put("SMALLINT", "SMALLINT");
            this.put("MEDIUMINT", "INTEGER");
            this.put("BIGINT", "BIGINT");
            this.put("BIT", "BIT");
            this.put("FLOAT", "REAL");
            this.put("DOUBLE", "DOUBLE");
            this.put("DECIMAL", "DECIMAL");
            this.put("DATE", "DATE");
            this.put("DATETIME", "DATE");
            this.put("TIMESTAMP", "TIMESTAMP");
            this.put("TIME", "TIME");
            this.put("CHAR", "CHAR");
            this.put("VARCHAR", "VARCHAR");
            this.put("BLOB", "VARBINARY");
            this.put("TINYBLOB", "VARBINARY");
            this.put("MEDIUMBLOB", "VARBINARY");
            this.put("LONGBLOB", "LONGVARCHAR");
            this.put("TEXT", "VARCHAR");
            this.put("MEDIUMTEXT", "LONGVARCHAR");
            this.put("LONGTEXT", "LONGVARCHAR");
            this.put("TINYTEXT", "VARCHAR");
            this.put("NUMBER", "NUMERIC");
            this.put("VARCHAR2", "VARCHAR");
            this.put("REAL", "REAL");
            this.put("NUMERIC", "NUMERIC");
        }
    };

    public JdbcTypeUtils() {
    }

    public static String extractFromDbType(String filedType) {
        UnsignedCheckResult unsignedCheckResult = MysqlHandlerUtils.checkUnsigned(filedType);
        String type = unsignedCheckResult.getType();
        return (String)fieldToJdbcType.get(type);
    }
}
