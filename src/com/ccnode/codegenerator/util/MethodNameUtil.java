//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.methodnameparser.buidler.ParsedMethodEnum;

public class MethodNameUtil {
    public MethodNameUtil() {
    }

    public static MethodNameCheckReuslt checkValidTextStarter(String text) {
        MethodNameCheckReuslt result = new MethodNameCheckReuslt();
        if (text.startsWith("find")) {
            result.setValid(true);
            result.setParsedMethodEnum(ParsedMethodEnum.FIND);
            return result;
        } else if (text.startsWith("update")) {
            result.setValid(true);
            result.setParsedMethodEnum(ParsedMethodEnum.UPDATE);
            return result;
        } else if (text.startsWith("delete")) {
            result.setValid(true);
            result.setParsedMethodEnum(ParsedMethodEnum.DELETE);
            return result;
        } else if (text.startsWith("count")) {
            result.setValid(true);
            result.setParsedMethodEnum(ParsedMethodEnum.COUNT);
            return result;
        } else {
            return result;
        }
    }
}
