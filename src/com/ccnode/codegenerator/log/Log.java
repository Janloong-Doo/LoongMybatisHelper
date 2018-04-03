//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log;

public interface Log {
    String getName();

    void info(Exception var1);

    void error(Exception var1);

    void info(String var1);

    void info(String var1, Exception var2);

    void error(String var1, Exception var2);

    void error(String var1);
}
