//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.methodnameparser.buidler.ParsedMethodEnum;

public class MethodNameCheckReuslt {
    private boolean valid = false;
    private ParsedMethodEnum parsedMethodEnum;

    public MethodNameCheckReuslt() {
    }

    public boolean isValid() {
        return this.valid;
    }

    public ParsedMethodEnum getParsedMethodEnum() {
        return this.parsedMethodEnum;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setParsedMethodEnum(ParsedMethodEnum parsedMethodEnum) {
        this.parsedMethodEnum = parsedMethodEnum;
    }
}
