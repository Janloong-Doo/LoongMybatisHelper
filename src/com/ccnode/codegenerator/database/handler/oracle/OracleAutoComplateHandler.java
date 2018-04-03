//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.handler.AutoCompleteHandler;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.intellij.psi.PsiParameter;

public class OracleAutoComplateHandler implements AutoCompleteHandler {
    private static volatile OracleAutoComplateHandler mInstance;

    private OracleAutoComplateHandler() {
    }

    public static OracleAutoComplateHandler getInstance() {
        if (mInstance == null) {
            Class var0 = OracleAutoComplateHandler.class;
            synchronized(OracleAutoComplateHandler.class) {
                if (mInstance == null) {
                    mInstance = new OracleAutoComplateHandler();
                }
            }
        }

        return mInstance;
    }

    public boolean isSupportedParam(PsiParameter psiParameter) {
        return OracleHandlerUtils.getTypePropByQulitifiedName(PsiClassUtil.convertToObjectText(psiParameter.getType().getCanonicalText())) != null;
    }
}
