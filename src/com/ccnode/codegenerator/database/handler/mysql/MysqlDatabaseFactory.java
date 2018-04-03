//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.database.handler.DatabaseFactory;
import com.ccnode.codegenerator.database.handler.GenerateFileHandler;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;

public class MysqlDatabaseFactory implements DatabaseFactory {
    public MysqlDatabaseFactory() {
    }

    public GenerateFileHandler getGenerateFileHandler() {
        return MysqlGenerateFileHandler.getInstance();
    }

    public GenerateMethodXmlHandler getMethodXmlHandler() {
        return MysqlGenerateMethodXmlHandler.getInstance();
    }

    public UpdateFieldHandler getUpdateFieldHandler() {
        return MysqlUpdateFieldHandler.getInstance();
    }

    public AutoCompleteHandler getAutoCompleteHandler() {
        return MysqlAutoCompleteHandler.getInstance();
    }
}
