//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log.handler;

import com.ccnode.codegenerator.log.LogEvent;
import com.ccnode.codegenerator.log.LoggerLevel;

public class ErrorHandler extends LoggerHandler {
    public ErrorHandler(LoggerHandler next) {
        super(next);
    }

    public void handleRequest(LogEvent req) {
        if (req.getLevel() == LoggerLevel.ERROR) {
            ;
        }

        super.handleRequest(req);
    }
}
