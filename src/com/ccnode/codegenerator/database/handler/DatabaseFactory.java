package com.ccnode.codegenerator.database.handler;

public interface DatabaseFactory {
    GenerateFileHandler getGenerateFileHandler();

    GenerateMethodXmlHandler getMethodXmlHandler();

    UpdateFieldHandler getUpdateFieldHandler();

    AutoCompleteHandler getAutoCompleteHandler();
}
