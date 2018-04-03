//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.BaseQueryBuilder;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;

public class SqliteGenerateMethodXmlHandler implements GenerateMethodXmlHandler {
    private static volatile SqliteGenerateMethodXmlHandler mInstance;
    private static volatile BaseQueryBuilder baseQueryBuilder;

    private SqliteGenerateMethodXmlHandler() {
    }

    public static SqliteGenerateMethodXmlHandler getInstance() {
        if (mInstance == null) {
            Class var0 = SqliteGenerateMethodXmlHandler.class;
            synchronized(SqliteGenerateMethodXmlHandler.class) {
                if (mInstance == null) {
                    mInstance = new SqliteGenerateMethodXmlHandler();
                }
            }
        }

        return mInstance;
    }

    public QueryInfo buildQueryInfoByMethodNameParsedResult(MethodNameParsedResult result) {
        if (baseQueryBuilder == null) {
            Class var2 = SqliteGenerateMethodXmlHandler.class;
            synchronized(SqliteGenerateMethodXmlHandler.class) {
                baseQueryBuilder = new BaseQueryBuilder(new SqliteQueryBuilderHandler());
            }
        }

        return baseQueryBuilder.buildQueryInfoByMethodNameParsedResult(result);
    }
}
