//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler;

import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;

public interface QueryBuilderHandler {
    void handleFindBeforeFromTable(QueryInfo var1, MethodNameParsedResult var2, boolean var3);

    void handleFindAfterFromTable(QueryInfo var1, MethodNameParsedResult var2);

    void handleUpdate(QueryInfo var1, MethodNameParsedResult var2);

    void handleDelete(QueryInfo var1, MethodNameParsedResult var2);

    void handleCount(QueryInfo var1, MethodNameParsedResult var2);
}
