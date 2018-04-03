//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.update;

import java.util.List;

public class ParsedUpdateDto {
    private List<ParsedUpdate> updateList;
    private List<ParsedUpdateError> errorList;

    public ParsedUpdateDto() {
    }

    public List<ParsedUpdate> getUpdateList() {
        return this.updateList;
    }

    public void setUpdateList(List<ParsedUpdate> updateList) {
        this.updateList = updateList;
    }

    public List<ParsedUpdateError> getErrorList() {
        return this.errorList;
    }

    public void setErrorList(List<ParsedUpdateError> errorList) {
        this.errorList = errorList;
    }
}
