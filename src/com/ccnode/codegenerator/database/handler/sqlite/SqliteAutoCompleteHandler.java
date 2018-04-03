//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.intellij.psi.PsiParameter;

public class SqliteAutoCompleteHandler implements AutoCompleteHandler {
    private static volatile SqliteAutoCompleteHandler mInstance;

    private SqliteAutoCompleteHandler() {
    }

    public static SqliteAutoCompleteHandler getInstance() {
        if (mInstance == null) {
            Class var0 = SqliteAutoCompleteHandler.class;
            synchronized(SqliteAutoCompleteHandler.class) {
                if (mInstance == null) {
                    mInstance = new SqliteAutoCompleteHandler();
                }
            }
        }

        return mInstance;
    }

    public boolean isSupportedParam(PsiParameter psiParameter) {
        return SqliteHandlerUtils.getTypePropByQulitifiedName(PsiClassUtil.convertToObjectText(psiParameter.getType().getCanonicalText())) != null;
    }
}
