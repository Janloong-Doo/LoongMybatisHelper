//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.delete;

import java.util.List;

public class ParsedDeleteDto {
    private List<ParsedDelete> parsedDeletes;
    private List<ParsedDeleteError> errors;

    public ParsedDeleteDto() {
    }

    public List<ParsedDelete> getParsedDeletes() {
        return this.parsedDeletes;
    }

    public void setParsedDeletes(List<ParsedDelete> parsedDeletes) {
        this.parsedDeletes = parsedDeletes;
    }

    public List<ParsedDeleteError> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ParsedDeleteError> errors) {
        this.errors = errors;
    }
}
