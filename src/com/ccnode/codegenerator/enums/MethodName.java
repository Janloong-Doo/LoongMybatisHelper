//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.enums;

public enum MethodName {
    insert(0, "插入"),
    insertList(1, "插入"),
    select(2, "插入"),
    update(3, "插入"),
    delete(4, "插入"),
    insertSelective(5, "插入"),
    none(-1, "none");

    private Integer code;
    private String desc;

    private MethodName(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static MethodName fromName(String name) {
        MethodName[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MethodName e = var1[var3];
            if (e.name().equals(name)) {
                return e;
            }
        }

        return none;
    }

    public static MethodName fromCode(Integer code) {
        MethodName[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MethodName e = var1[var3];
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return none;
    }

    public static String codeToName(Integer code) {
        MethodName o = fromCode(code);
        return o.name();
    }

    public static Integer nameToCode(String name) {
        MethodName o = fromName(name);
        return o.getCode();
    }

    public Boolean equalWithName(String name) {
        return this == fromName(name);
    }

    public Boolean equalWithCode(String name) {
        return this == fromName(name);
    }
}
