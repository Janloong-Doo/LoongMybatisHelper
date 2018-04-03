//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log.handler;

import com.ccnode.codegenerator.log.LogEvent;

public abstract class LoggerHandler {
    private LoggerHandler next;

    public LoggerHandler(LoggerHandler next) {
        this.next = next;
    }

    public void handleRequest(LogEvent req) {
        if (this.next != null) {
            this.next.handleRequest(req);
        }

    }
}
