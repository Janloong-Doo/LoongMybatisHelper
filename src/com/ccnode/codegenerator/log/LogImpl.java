//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log;

import com.ccnode.codegenerator.log.handler.LoggerHandler;

public class LogImpl implements Log {
    private String name;
    private LoggerHandler chain;

    public LogImpl(String name, LoggerHandler chain) {
        this.name = name;
        this.chain = chain;
    }

    public String getName() {
        return this.name;
    }

    public void info(Exception e) {
        LogEvent event = new LogEvent();
        event.setName(this.name);
        event.setE(e);
        event.setLevel(LoggerLevel.INFO);
        this.chain.handleRequest(event);
    }

    public void error(Exception e) {
        LogEvent event = new LogEvent();
        event.setName(this.name);
        event.setE(e);
        event.setLevel(LoggerLevel.ERROR);
        this.chain.handleRequest(event);
    }

    public void info(String message) {
        LogEvent event = new LogEvent();
        event.setName(this.name);
        event.setMessage(message);
        event.setLevel(LoggerLevel.INFO);
        this.chain.handleRequest(event);
    }

    public void info(String message, Exception e) {
        LogEvent event = new LogEvent();
        event.setName(this.name);
        event.setMessage(message);
        event.setE(e);
        event.setLevel(LoggerLevel.INFO);
        this.chain.handleRequest(event);
    }

    public void error(String message, Exception e) {
        LogEvent event = new LogEvent();
        event.setMessage(message);
        event.setName(this.name);
        event.setE(e);
        event.setLevel(LoggerLevel.ERROR);
        this.chain.handleRequest(event);
    }

    public void error(String message) {
        LogEvent event = new LogEvent();
        event.setName(this.name);
        event.setMessage(message);
        event.setLevel(LoggerLevel.ERROR);
        this.chain.handleRequest(event);
    }
}
