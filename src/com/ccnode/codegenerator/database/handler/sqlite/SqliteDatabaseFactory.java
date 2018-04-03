//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.database.handler.DatabaseFactory;
import com.ccnode.codegenerator.database.handler.GenerateFileHandler;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;

public class SqliteDatabaseFactory implements DatabaseFactory {
    public SqliteDatabaseFactory() {
    }

    public GenerateFileHandler getGenerateFileHandler() {
        return SqliteGenerateFilesHandler.getInstance();
    }

    public GenerateMethodXmlHandler getMethodXmlHandler() {
        return SqliteGenerateMethodXmlHandler.getInstance();
    }

    public UpdateFieldHandler getUpdateFieldHandler() {
        return SqliteUpdateFiledHandler.getInstance();
    }

    public AutoCompleteHandler getAutoCompleteHandler() {
        return SqliteAutoCompleteHandler.getInstance();
    }
}
