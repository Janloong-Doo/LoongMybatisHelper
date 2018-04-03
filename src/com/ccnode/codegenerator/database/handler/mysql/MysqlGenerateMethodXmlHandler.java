//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.handler.BaseQueryBuilder;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;

public class MysqlGenerateMethodXmlHandler implements GenerateMethodXmlHandler {
    private static volatile MysqlGenerateMethodXmlHandler instance;
    private static volatile BaseQueryBuilder baseQueryBuilder;

    private MysqlGenerateMethodXmlHandler() {
    }

    public static MysqlGenerateMethodXmlHandler getInstance() {
        if (instance == null) {
            Class var0 = MysqlGenerateMethodXmlHandler.class;
            synchronized(MysqlGenerateMethodXmlHandler.class) {
                if (instance == null) {
                    instance = new MysqlGenerateMethodXmlHandler();
                }
            }
        }

        return instance;
    }

    public QueryInfo buildQueryInfoByMethodNameParsedResult(MethodNameParsedResult result) {
        if (baseQueryBuilder == null) {
            Class var2 = MysqlGenerateMethodXmlHandler.class;
            synchronized(MysqlGenerateMethodXmlHandler.class) {
                if (baseQueryBuilder == null) {
                    baseQueryBuilder = new BaseQueryBuilder(new MysqlQueryBuilderHandler());
                }
            }
        }

        return baseQueryBuilder.buildQueryInfoByMethodNameParsedResult(result);
    }
}
