//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.InsertFileProp;
import com.ccnode.codegenerator.util.FileUtils;
import com.google.common.collect.Lists;
import java.util.List;

public class GenSqlService {
    public GenSqlService() {
    }

    public static void generateSqlFile(InsertFileProp prop, List<GenCodeProp> propList, GenCodeProp primaryKey, String tableName, List<List<String>> multipleColumnIndex, List<List<String>> multipleColumnUnique) {
        String sql = DatabaseComponenent.currentHandler().getGenerateFileHandler().generateSql(propList, primaryKey, tableName, multipleColumnIndex, multipleColumnUnique);
        List<String> retList = Lists.newArrayList(new String[]{sql});
        FileUtils.writeFiles(prop, retList);
    }
}
