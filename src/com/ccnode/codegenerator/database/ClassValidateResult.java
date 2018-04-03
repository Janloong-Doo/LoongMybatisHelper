package com.ccnode.codegenerator.database;


import com.intellij.psi.PsiField;

import java.util.List;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:01
 */
public class ClassValidateResult {
    private List<PsiField> validFields;
    private Boolean valid;
    private String invalidMessages;

    public ClassValidateResult() {
    }

    public String getInvalidMessages() {
        return this.invalidMessages;
    }

    public void setInvalidMessages(String invalidMessages) {
        this.invalidMessages = invalidMessages;
    }

    public List<PsiField> getValidFields() {
        return this.validFields;
    }

    public void setValidFields(List<PsiField> validFields) {
        this.validFields = validFields;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
