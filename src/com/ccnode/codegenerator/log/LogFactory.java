//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log;

import com.ccnode.codegenerator.log.handler.ErrorHandler;
import com.ccnode.codegenerator.log.handler.InfoHandler;
import com.ccnode.codegenerator.log.handler.LoggerHandler;
import com.ccnode.codegenerator.log.handler.SendToServerHandler;

public class LogFactory {
    private static LoggerHandler chain = new InfoHandler(new ErrorHandler(new SendToServerHandler((LoggerHandler)null)));

    public LogFactory() {
    }

    public static Log getLogger(Class aClass) {
        return new LogImpl(aClass.getName(), chain);
    }

    public static Log getLogger(String name) {
        return new LogImpl(name, chain);
    }
}
