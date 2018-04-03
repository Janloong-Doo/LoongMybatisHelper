package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.ClassValidateResult;
import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.database.handler.FieldValidator;
import com.ccnode.codegenerator.database.handler.GenerateFileHandler;
import com.ccnode.codegenerator.database.handler.HandlerValidator;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.util.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.intellij.diagnostic.ReportMessages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTypesUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.intellij.reporting.ReporterKt;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MysqlGenerateFileHandler implements GenerateFileHandler {
    private static volatile MysqlGenerateFileHandler instance;
    private static volatile HandlerValidator handlerValidator;

    @NotNull
    public List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        List var10000 = MysqlHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            //reportNull(0);
            throw new RuntimeException("MysqlGenerateFileHandler getRecommendDatabaseTypeOfFieldType 有错误啊");


        }

        return var10000;
    }

    private MysqlGenerateFileHandler() {
        if (instance != null) {
            throw new IllegalStateException("Already initialized.");
        }
    }

    public static MysqlGenerateFileHandler getInstance() {
        MysqlGenerateFileHandler result = instance;
        if (result == null) {
            Class var1 = MysqlGenerateFileHandler.class;
            synchronized(MysqlGenerateFileHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new MysqlGenerateFileHandler();
                }
            }
        }

        return result;
    }

    @NotNull
    public String generateSql(List<GenCodeProp> propList, GenCodeProp primaryKey, String tableName, List<List<String>> multipleColumnIndex, List<List<String>> multipleColumnUnique) {
        List<String> retList = Lists.newArrayList();
        String newTableName = DatabaseComponenent.formatColumn(tableName);
        retList.add(String.format("-- auto Generated on %s ", DateUtil.formatLong(new Date())));
        retList.add("-- DROP TABLE IF EXISTS " + newTableName + "; ");
        retList.add("CREATE TABLE " + newTableName + "(");
        List<String> indexText = Lists.newArrayList();
        Iterator var9 = propList.iterator();

        String m;
        while(var9.hasNext()) {
            GenCodeProp field = (GenCodeProp)var9.next();
            m = genfieldSql(field);
            retList.add(m);
            if (field.getIndex()) {
                indexText.add("\tINDEX(" + field.getColumnName() + "),");
            }
        }

        retList.addAll(indexText);
        Iterator var12;
        String string;
        List strings;
        if (!multipleColumnIndex.isEmpty()) {
            var9 = multipleColumnIndex.iterator();

            while(var9.hasNext()) {
                strings = (List)var9.next();
                m = "INDEX `ix_";

                for(var12 = strings.iterator(); var12.hasNext(); m = m + string + "_") {
                    string = (String)var12.next();
                }

                m = m.substring(0, m.length() - 1);
                m = m + "`(";

                for(var12 = strings.iterator(); var12.hasNext(); m = m + string + ",") {
                    string = (String)var12.next();
                }

                m = m.substring(0, m.length() - 1);
                m = m + "),";
                retList.add(m);
            }
        }

        if (!multipleColumnUnique.isEmpty()) {
            var9 = multipleColumnUnique.iterator();

            while(var9.hasNext()) {
                strings = (List)var9.next();
                m = "UNIQUE `ux_";

                for(var12 = strings.iterator(); var12.hasNext(); m = m + string + "_") {
                    string = (String)var12.next();
                }

                m = m.substring(0, m.length() - 1);
                m = m + "`(";

                for(var12 = strings.iterator(); var12.hasNext(); m = m + string + ",") {
                    string = (String)var12.next();
                }

                m = m.substring(0, m.length() - 1);
                m = m + "),";
                retList.add(m);
            }
        }

        retList.add("\tPRIMARY KEY (" + DatabaseComponenent.formatColumn(primaryKey.getColumnName()) + ")");
        retList.add(String.format(")ENGINE=%s DEFAULT CHARSET=%s COMMENT '%s';", "InnoDB", "utf8", newTableName));
        String var10000 = Joiner.on("\n").join(retList);
        if (var10000 == null) {
            $$$reportNull$$$0(1);
        }

        return var10000;
    }

    private static String genfieldSql(GenCodeProp field) {
        StringBuilder ret = new StringBuilder();
        UnsignedCheckResult result = MysqlHandlerUtils.checkUnsigned(field.getFiledType());
        ret.append("\t").append(DatabaseComponenent.formatColumn(field.getColumnName())).append(" ").append(result.getType());
        if (StringUtils.isNotBlank(field.getSize())) {
            ret.append(" (" + field.getSize() + ")");
        }

        if (result.isUnsigned()) {
            ret.append(" UNSIGNED");
        }

        if (field.getUnique()) {
            ret.append(" UNIQUE");
        }

        if (!field.getCanBeNull()) {
            ret.append(" NOT NULL");
        }

        if (!field.getPrimaryKey() && field.getHasDefaultValue() && StringUtils.isNotBlank(field.getDefaultValue())) {
            ret.append(" DEFAULT " + field.getDefaultValue());
        }

        if (field.getPrimaryKey()) {
            ret.append(" AUTO_INCREMENT");
        }

        ret.append(" COMMENT '" + field.getComment() + "',");
        return ret.toString();
    }

    @NotNull
    public ClassValidateResult validateCurrentClass(PsiClass psiClass) {
        if (handlerValidator == null) {
            Class var2 = MysqlGenerateFileHandler.class;
            synchronized(MysqlGenerateFileHandler.class) {
                if (handlerValidator == null) {
                    handlerValidator = new HandlerValidator(new MysqlGenerateFileHandler.MysqlFieldValidator());
                }
            }
        }

        ClassValidateResult var10000 = handlerValidator.validateResult(psiClass);
        if (var10000 == null) {
            $$$reportNull$$$0(2);
        }

        return var10000;
    }

    class MysqlFieldValidator implements FieldValidator {
        MysqlFieldValidator() {
        }

        public boolean isValidField(PsiField psiField) {
            String canonicalText = psiField.getType().getCanonicalText();
            List<TypeProps> typePropss = MysqlHandlerUtils.getTypePropsByQulifiType(canonicalText);
            if (typePropss != null) {
                return true;
            } else {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(psiField.getType());
                return psiClass != null && psiClass.isEnum();
            }
        }
    }
}
