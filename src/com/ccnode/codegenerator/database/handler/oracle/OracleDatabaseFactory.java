//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.database.handler.DatabaseFactory;
import com.ccnode.codegenerator.database.handler.GenerateFileHandler;
import com.ccnode.codegenerator.database.handler.GenerateMethodXmlHandler;
import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;

public class OracleDatabaseFactory implements DatabaseFactory {
    public OracleDatabaseFactory() {
    }

    public GenerateFileHandler getGenerateFileHandler() {
        return OracleGenerateFilesHandler.getInstance();
    }

    public GenerateMethodXmlHandler getMethodXmlHandler() {
        return OracleGenerateMethodXmlHandler.getInstance();
    }

    public UpdateFieldHandler getUpdateFieldHandler() {
        return OracleUpdateFiledHandler.getInstance();
    }

    public AutoCompleteHandler getAutoCompleteHandler() {
        return OracleAutoComplateHandler.getInstance();
    }
}
