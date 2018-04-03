package com.ccnode.codegenerator.database.handler;

import com.ccnode.codegenerator.methodnameparser.buidler.MethodNameParsedResult;
import com.ccnode.codegenerator.methodnameparser.buidler.QueryInfo;

public interface QueryBuilder {    QueryInfo buildQueryInfoByMethodNameParsedResult(MethodNameParsedResult var1);

}
