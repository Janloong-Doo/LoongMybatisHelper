//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.intellij.psi.PsiParameter;

public class MysqlAutoCompleteHandler implements AutoCompleteHandler {
    private static volatile MysqlAutoCompleteHandler mInstance;

    private MysqlAutoCompleteHandler() {
    }

    public static MysqlAutoCompleteHandler getInstance() {
        if (mInstance == null) {
            Class var0 = MysqlAutoCompleteHandler.class;
            synchronized(MysqlAutoCompleteHandler.class) {
                if (mInstance == null) {
                    mInstance = new MysqlAutoCompleteHandler();
                }
            }
        }

        return mInstance;
    }

    public boolean isSupportedParam(PsiParameter psiParameter) {
        return MysqlHandlerUtils.getTypePropsByQulifiType(PsiClassUtil.convertToObjectText(psiParameter.getType().getCanonicalText())) != null;
    }
}
