//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.pojo;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlFile;
import java.util.Map;

public class MethodXmlPsiInfo {
    private String methodName;
    private FieldToColumnRelation relation;
    private String tableName;
    private Map<String, String> fieldMap;
    private String allColumnName;
    private String psiClassName;
    private XmlFile mybatisXmlFile;
    private String psiClassFullName;
    private PsiClass serviceClass;
    private PsiClass serviceInterfaceClass;
    private Project project;
    private PsiClass srcClass;

    public MethodXmlPsiInfo() {
    }

    public XmlFile getMybatisXmlFile() {
        return this.mybatisXmlFile;
    }

    public void setMybatisXmlFile(XmlFile mybatisXmlFile) {
        this.mybatisXmlFile = mybatisXmlFile;
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

    public String getPsiClassFullName() {
        return this.psiClassFullName;
    }

    public void setPsiClassFullName(String psiClassFullName) {
        this.psiClassFullName = psiClassFullName;
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

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAllColumnName() {
        return this.allColumnName;
    }

    public void setAllColumnName(String allColumnName) {
        this.allColumnName = allColumnName;
    }

    public PsiClass getServiceClass() {
        return this.serviceClass;
    }

    public void setServiceClass(PsiClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public PsiClass getServiceInterfaceClass() {
        return this.serviceInterfaceClass;
    }

    public void setServiceInterfaceClass(PsiClass serviceInterfaceClass) {
        this.serviceInterfaceClass = serviceInterfaceClass;
    }
}
