//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.find;

import java.util.List;

public class ParsedFindDto {
    private List<ParsedFind> parsedFinds;
    private List<ParsedFindError> parsedFindErrors;

    public ParsedFindDto() {
    }

    public List<ParsedFind> getParsedFinds() {
        return this.parsedFinds;
    }

    public void setParsedFinds(List<ParsedFind> parsedFinds) {
        this.parsedFinds = parsedFinds;
    }

    public List<ParsedFindError> getParsedFindErrors() {
        return this.parsedFindErrors;
    }

    public void setParsedFindErrors(List<ParsedFindError> parsedFindErrors) {
        this.parsedFindErrors = parsedFindErrors;
    }
}
