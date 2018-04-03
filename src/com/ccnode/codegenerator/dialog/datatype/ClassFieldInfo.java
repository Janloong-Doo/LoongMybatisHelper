//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog.datatype;

import com.intellij.psi.PsiField;

public class ClassFieldInfo {
    private String fieldName;
    private String fieldType;
    private PsiField psiField;
    private String comment;

    public ClassFieldInfo() {
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PsiField getPsiField() {
        return this.psiField;
    }

    public void setPsiField(PsiField psiField) {
        this.psiField = psiField;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
