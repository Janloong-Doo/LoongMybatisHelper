//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog.datatype;

public class TypeProps {
    private String defaultType;
    private String size;
    private Boolean canBeNull;
    private Boolean unique;
    private String defaultValue;
    private Boolean primary;
    private Boolean index;
    private Boolean hasDefaultValue;
    private Integer order;

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getIndex() {
        return this.index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    public Boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }

    public void setHasDefaultValue(Boolean hasDefaultValue) {
        this.hasDefaultValue = hasDefaultValue;
    }

    public Boolean getPrimary() {
        return this.primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getDefaultType() {
        return this.defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getCanBeNull() {
        return this.canBeNull;
    }

    public void setCanBeNull(Boolean canBeNull) {
        this.canBeNull = canBeNull;
    }

    public Boolean getUnique() {
        return this.unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public TypeProps(String defaultType, String size, String defaultValue) {
        this.defaultType = defaultType;
        this.size = size;
        this.defaultValue = defaultValue;
        this.canBeNull = false;
        this.unique = false;
        this.primary = false;
        this.index = false;
        this.hasDefaultValue = true;
    }

    public TypeProps() {
    }
}
