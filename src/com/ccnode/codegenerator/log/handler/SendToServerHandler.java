//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log.handler;

import com.ccnode.codegenerator.log.LogEvent;

public class SendToServerHandler extends LoggerHandler {
    public SendToServerHandler(LoggerHandler next) {
        super(next);
    }

    public void handleRequest(LogEvent req) {
        this.sendLogToServer(req);
        super.handleRequest(req);
    }

    public void sendLogToServer(LogEvent req) {
        MybatisLogRequest request = new MybatisLogRequest();
        request.setClassName(req.getName());
        request.setLoggerLevel(req.getLevel().name());
        request.setMessages(MessageBuilder.buildMessage(req.getMessage(), req.getE()));
        HttpClient.sendDataToLog(request);
    }
}
