//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.enums;

public enum IconEnum {
    DEFAULT("default", "icon/mybatis_new_6.png", "icon/mybatis_new_5.png"),
    CHANGE("move", "icon/move1.png", "icon/move2.png");

    private String iconName;
    private String mapperIconPlace;
    private String mapperXmlIconPlace;

    private IconEnum(String iconName, String mapperIconPlace, String mapperXmlIconPlace) {
        this.iconName = iconName;
        this.mapperIconPlace = mapperIconPlace;
        this.mapperXmlIconPlace = mapperXmlIconPlace;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getMapperIconPlace() {
        return this.mapperIconPlace;
    }

    public void setMapperIconPlace(String mapperIconPlace) {
        this.mapperIconPlace = mapperIconPlace;
    }

    public String getMapperXmlIconPlace() {
        return this.mapperXmlIconPlace;
    }

    public void setMapperXmlIconPlace(String mapperXmlIconPlace) {
        this.mapperXmlIconPlace = mapperXmlIconPlace;
    }

    public static IconEnum getIconEnum(String name) {
        IconEnum[] values = values();
        IconEnum[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            IconEnum value = var2[var4];
            if (name.equals(value.getIconName())) {
                return value;
            }
        }

        return null;
    }
}
