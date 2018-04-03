package com.ccnode.codegenerator.constants;


/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 11:57
 */
public enum SqlTypeEnum {

    SELECT("select"),

    UPDATE("update"),

    DELETE("delete");

    private String value;

    private SqlTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
