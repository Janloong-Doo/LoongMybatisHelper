//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.buidler;

import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCount;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDelete;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdate;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlFile;
import java.util.Map;

public class MethodNameParsedResult {
    private ParsedMethodEnum parsedType;
    private String methodName;
    private FieldToColumnRelation relation;
    private XmlFile mybatisXmlFile;
    private String allColumnName;
    private Project project;
    private PsiClass srcClass;
    private String tableName;
    private Map<String, String> fieldMap;
    private String psiClassName;
    private ParsedFind parsedFind;
    private ParsedUpdate parsedUpdate;
    private ParsedDelete parsedDelete;
    private ParsedCount parsedCount;
    private String psiClassFullName;

    public MethodNameParsedResult() {
    }

    public String getPsiClassFullName() {
        return this.psiClassFullName;
    }

    public void setPsiClassFullName(String psiClassFullName) {
        this.psiClassFullName = psiClassFullName;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PsiClass getSrcClass() {
        return this.srcClass;
    }

    public void setSrcClass(PsiClass srcClass) {
        this.srcClass = srcClass;
    }

    public ParsedMethodEnum getParsedType() {
        return this.parsedType;
    }

    public void setParsedType(ParsedMethodEnum parsedType) {
        this.parsedType = parsedType;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public FieldToColumnRelation getRelation() {
        return this.relation;
    }

    public void setRelation(FieldToColumnRelation relation) {
        this.relation = relation;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public String getPsiClassName() {
        return this.psiClassName;
    }

    public void setPsiClassName(String psiClassName) {
        this.psiClassName = psiClassName;
    }

    public ParsedFind getParsedFind() {
        return this.parsedFind;
    }

    public void setParsedFind(ParsedFind parsedFind) {
        this.parsedFind = parsedFind;
    }

    public ParsedUpdate getParsedUpdate() {
        return this.parsedUpdate;
    }

    public void setParsedUpdate(ParsedUpdate parsedUpdate) {
        this.parsedUpdate = parsedUpdate;
    }

    public ParsedDelete getParsedDelete() {
        return this.parsedDelete;
    }

    public void setParsedDelete(ParsedDelete parsedDelete) {
        this.parsedDelete = parsedDelete;
    }

    public ParsedCount getParsedCount() {
        return this.parsedCount;
    }

    public void setParsedCount(ParsedCount parsedCount) {
        this.parsedCount = parsedCount;
    }

    public XmlFile getMybatisXmlFile() {
        return this.mybatisXmlFile;
    }

    public void setMybatisXmlFile(XmlFile mybatisXmlFile) {
        this.mybatisXmlFile = mybatisXmlFile;
    }

    public String getAllColumnName() {
        return this.allColumnName;
    }

    public void setAllColumnName(String allColumnName) {
        this.allColumnName = allColumnName;
    }
}
