package com.ccnode.codegenerator.database.handler;


import com.intellij.psi.PsiParameter;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:06
 */
public interface AutoCompleteHandler {
    boolean isSupportedParam(PsiParameter var1);
}