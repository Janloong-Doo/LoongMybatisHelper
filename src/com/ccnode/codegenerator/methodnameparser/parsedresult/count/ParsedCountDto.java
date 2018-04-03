//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.count;

import java.util.List;

public class ParsedCountDto {
    private List<ParsedCount> parsedCounts;
    private List<ParsedCountError> errors;

    public ParsedCountDto() {
    }

    public List<ParsedCount> getParsedCounts() {
        return this.parsedCounts;
    }

    public void setParsedCounts(List<ParsedCount> parsedCounts) {
        this.parsedCounts = parsedCounts;
    }

    public List<ParsedCountError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ParsedCountError> errors) {
        this.errors = errors;
    }
}
