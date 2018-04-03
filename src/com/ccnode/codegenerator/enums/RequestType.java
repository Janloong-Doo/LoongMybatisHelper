//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.enums;

public enum RequestType {
    REGISTER(0, "注册"),
    GEN_CODE(1, ""),
    NONE(-1, "none");

    private Integer code;
    private String desc;

    private RequestType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static RequestType fromName(String name) {
        RequestType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RequestType e = var1[var3];
            if (e.name().equals(name)) {
                return e;
            }
        }

        return NONE;
    }

    public static RequestType fromCode(Integer code) {
        RequestType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RequestType e = var1[var3];
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return NONE;
    }

    public static String codeToName(Integer code) {
        RequestType o = fromCode(code);
        return o.name();
    }

    public static Integer nameToCode(String name) {
        RequestType o = fromName(name);
        return o.getCode();
    }

    public Boolean equalWithName(String name) {
        return this == fromName(name);
    }

    public Boolean equalWithCode(Integer code) {
        return this == fromCode(code);
    }
}
