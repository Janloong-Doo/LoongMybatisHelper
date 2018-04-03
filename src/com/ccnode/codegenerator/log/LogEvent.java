//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LogEvent {
    private String message;
    private Exception e;
    private String name;
    private LoggerLevel level;

    public LogEvent() {
    }

    @NotNull
    public String getName() {
        String var10000 = this.name;
        if (this.name == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public Exception getE() {
        return this.e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    @NotNull
    public LoggerLevel getLevel() {
        LoggerLevel var10000 = this.level;
        if (this.level == null) {
            $$$reportNull$$$0(1);
        }

        return var10000;
    }

    public void setLevel(LoggerLevel level) {
        this.level = level;
    }
}
