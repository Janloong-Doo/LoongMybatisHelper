package com.ccnode.codegenerator.database.handler;


import com.ccnode.codegenerator.database.ClassValidateResult;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiClassUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:08
 */
public class HandlerValidator {
    private FieldValidator fieldValidator;

    public HandlerValidator(FieldValidator fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    public ClassValidateResult validateResult(PsiClass psiClass) {
        ClassValidateResult result = new ClassValidateResult();
        PsiField[] allFields = psiClass.getAllFields();
        if (allFields.length == 0) {
            result.setValid(false);
            result.setInvalidMessages("there is no available field in current class");
            return result;
        } else {
            List<PsiField> validFields = Lists.newArrayList();
            List<String> errorMessages = Lists.newArrayList();
            PsiField[] var6 = allFields;
            int var7 = allFields.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                PsiField psiField = var6[var8];
                if (psiField.hasModifierProperty("transient")) {
                    errorMessages.add(buildErrorMessage(psiField, " ignore transient field type"));
                } else if (PsiClassUtil.isSupprtedModifier(psiField)) {
                    String fieldType = psiField.getType().getCanonicalText();
                    if (!this.fieldValidator.isValidField(psiField)) {
                        if (PsiClassUtil.isPrimitiveType(fieldType)) {
                            errorMessages.add(buildErrorMessage(psiField, " please use with object type"));
                        } else {
                            errorMessages.add(buildErrorMessage(psiField, " unsupported field type"));
                        }
                    } else {
                        validFields.add(psiField);
                    }
                } else {
                    errorMessages.add(buildErrorMessage(psiField, " please use not public and not static"));
                }
            }

            result.setValidFields(validFields);
            if (errorMessages.size() <= 0) {
                result.setValid(true);
                return result;
            } else {
                result.setValid(false);
                StringBuilder builder = new StringBuilder();
                Iterator var12 = errorMessages.iterator();

                while(var12.hasNext()) {
                    String errorMessage = (String)var12.next();
                    builder.append(errorMessage + "\n");
                }

                builder.deleteCharAt(builder.length() - 1);
                result.setInvalidMessages(builder.toString());
                return result;
            }
        }
    }

    private static String buildErrorMessage(PsiField psiField, String reason) {
        return "field name is:" + psiField.getName() + "  field type is:" + psiField.getType().getCanonicalText() + "  invalid reason is:" + reason;
    }
}
