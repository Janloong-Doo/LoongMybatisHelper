//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.myconfigurable;

import com.ccnode.codegenerator.enums.IconEnum;

public class Profile extends DomainObject {
    private String database = "MySql";
    private Boolean addMapperAnnotation = true;
    private Boolean useGeneratedKeys = true;
    private Boolean mysqlUseWithDash = false;
    private Boolean generateWithIfTest = false;
    private Boolean generateMethodInService = false;
    private Boolean generateMethodInServiceInterface = false;
    private String mapperPrefix = "Dao";
    private String javaModelPackage;
    private String javaMapperPackage;
    private String xmlMapperPackage;
    private String javaModelPath;
    private String javaMapperPath;
    private String xmlMapperPath;
    private String iconName;

    public Profile() {
        this.iconName = IconEnum.DEFAULT.getIconName();
    }

    public String getDatabase() {
        return this.database;
    }

    public Boolean getAddMapperAnnotation() {
        return this.addMapperAnnotation;
    }

    public Boolean getUseGeneratedKeys() {
        return this.useGeneratedKeys;
    }

    public Boolean getMysqlUseWithDash() {
        return this.mysqlUseWithDash;
    }

    public Boolean getGenerateWithIfTest() {
        return this.generateWithIfTest;
    }

    public Boolean getGenerateMethodInService() {
        return this.generateMethodInService;
    }

    public Boolean getGenerateMethodInServiceInterface() {
        return this.generateMethodInServiceInterface;
    }

    public String getMapperPrefix() {
        return this.mapperPrefix;
    }

    public String getJavaModelPackage() {
        return this.javaModelPackage;
    }

    public String getJavaMapperPackage() {
        return this.javaMapperPackage;
    }

    public String getXmlMapperPackage() {
        return this.xmlMapperPackage;
    }

    public String getJavaModelPath() {
        return this.javaModelPath;
    }

    public String getJavaMapperPath() {
        return this.javaMapperPath;
    }

    public String getXmlMapperPath() {
        return this.xmlMapperPath;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setAddMapperAnnotation(Boolean addMapperAnnotation) {
        this.addMapperAnnotation = addMapperAnnotation;
    }

    public void setUseGeneratedKeys(Boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
    }

    public void setMysqlUseWithDash(Boolean mysqlUseWithDash) {
        this.mysqlUseWithDash = mysqlUseWithDash;
    }

    public void setGenerateWithIfTest(Boolean generateWithIfTest) {
        this.generateWithIfTest = generateWithIfTest;
    }

    public void setGenerateMethodInService(Boolean generateMethodInService) {
        this.generateMethodInService = generateMethodInService;
    }

    public void setGenerateMethodInServiceInterface(Boolean generateMethodInServiceInterface) {
        this.generateMethodInServiceInterface = generateMethodInServiceInterface;
    }

    public void setMapperPrefix(String mapperPrefix) {
        this.mapperPrefix = mapperPrefix;
    }

    public void setJavaModelPackage(String javaModelPackage) {
        this.javaModelPackage = javaModelPackage;
    }

    public void setJavaMapperPackage(String javaMapperPackage) {
        this.javaMapperPackage = javaMapperPackage;
    }

    public void setXmlMapperPackage(String xmlMapperPackage) {
        this.xmlMapperPackage = xmlMapperPackage;
    }

    public void setJavaModelPath(String javaModelPath) {
        this.javaModelPath = javaModelPath;
    }

    public void setJavaMapperPath(String javaMapperPath) {
        this.javaMapperPath = javaMapperPath;
    }

    public void setXmlMapperPath(String xmlMapperPath) {
        this.xmlMapperPath = xmlMapperPath;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
