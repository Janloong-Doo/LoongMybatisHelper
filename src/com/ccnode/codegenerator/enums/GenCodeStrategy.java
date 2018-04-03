//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.enums;

public enum GenCodeStrategy {
    APPEND_NEW(0, ""),
    REPLACE_TOTALLY(1, ""),
    NONE(-1, "none");

    private Integer code;
    private String desc;

    private GenCodeStrategy(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static GenCodeStrategy fromName(String name) {
        GenCodeStrategy[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GenCodeStrategy e = var1[var3];
            if (e.name().equals(name)) {
                return e;
            }
        }

        return NONE;
    }

    public static GenCodeStrategy fromCode(Integer code) {
        GenCodeStrategy[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GenCodeStrategy e = var1[var3];
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return NONE;
    }

    public static String codeToName(Integer code) {
        GenCodeStrategy o = fromCode(code);
        return o.name();
    }

    public static Integer nameToCode(String name) {
        GenCodeStrategy o = fromName(name);
        return o.getCode();
    }
}
