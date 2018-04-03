//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.handler.BaseQueryBuilder;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;

public class OracleGenerateMethodXmlHandler implements GenerateMethodXmlHandler {
    private static volatile OracleGenerateMethodXmlHandler mInstance;
    private static volatile BaseQueryBuilder baseQueryBuilder;

    private OracleGenerateMethodXmlHandler() {
    }

    public static OracleGenerateMethodXmlHandler getInstance() {
        if (mInstance == null) {
            Class var0 = OracleGenerateMethodXmlHandler.class;
            synchronized(OracleGenerateMethodXmlHandler.class) {
                if (mInstance == null) {
                    mInstance = new OracleGenerateMethodXmlHandler();
                }
            }
        }

        return mInstance;
    }

    public QueryInfo buildQueryInfoByMethodNameParsedResult(MethodNameParsedResult result) {
        if (baseQueryBuilder == null) {
            Class var2 = OracleGenerateMethodXmlHandler.class;
            synchronized(OracleGenerateMethodXmlHandler.class) {
                baseQueryBuilder = new BaseQueryBuilder(new OracleQueryBuilderHandler());
            }
        }

        return baseQueryBuilder.buildQueryInfoByMethodNameParsedResult(result);
    }
}
