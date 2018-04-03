//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.ccnode.codegenerator.sqlparse.TableNameAndFieldName;
import java.util.List;

public interface MysqlCompleteCacheInteface {
    void addDatabaseCache(NewDatabaseInfo var1);

    List<String> getRecommendFromCache(String var1, String var2);

    List<String> getAllTables();

    List<String> getAllFields();

    List<TableNameAndFieldName> getAllFieldsWithTable();

    List<TableNameAndFieldName> getTableAllFields(String var1);

    String getFieldType();

    void cleanAll();
}
