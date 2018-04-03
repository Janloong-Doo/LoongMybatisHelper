//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.sqlparse;

import com.intellij.codeInsight.lookup.LookupElement;
import java.util.List;

public class ParsedResult {
    private String currentState;
    private List<LookupElement> recommedValues;

    public ParsedResult() {
    }

    public String getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<LookupElement> getRecommedValues() {
        return this.recommedValues;
    }

    public void setRecommedValues(List<LookupElement> recommedValues) {
        this.recommedValues = recommedValues;
    }
}
