//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.ClassValidateResult;
import com.ccnode.codegenerator.database.handler.FieldValidator;
import com.ccnode.codegenerator.database.handler.GenerateFileHandler;
import com.ccnode.codegenerator.database.handler.HandlerValidator;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.util.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTypesUtil;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class OracleGenerateFilesHandler implements GenerateFileHandler {
    private static volatile OracleGenerateFilesHandler instance;
    private static volatile HandlerValidator handlerValidator;

    private OracleGenerateFilesHandler() {
    }

    public static OracleGenerateFilesHandler getInstance() {
        if (instance == null) {
            Class var0 = OracleGenerateFilesHandler.class;
            synchronized(OracleGenerateFilesHandler.class) {
                if (instance == null) {
                    instance = new OracleGenerateFilesHandler();
                }
            }
        }

        return instance;
    }

    @NotNull
    public List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        List var10000 = OracleHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            throw new RuntimeException("OracleGenerateFilesHandler getRecommendDatabaseTypeOfFieldType 有错误啊");

        }

        return var10000;
    }

    @NotNull
    public String generateSql(List<GenCodeProp> propList, GenCodeProp primaryKey, String tableName, List<List<String>> multipleColumnIndex, List<List<String>> multipleColumnUnique) {
        List<String> retList = Lists.newArrayList();
        retList.add(String.format("-- auto Generated on %s ", DateUtil.formatLong(new Date())));
        retList.add("-- DROP TABLE IF EXISTS " + tableName + "; ");
        retList.add("CREATE TABLE " + tableName + "(");
        List<String> indexText = Lists.newArrayList();
        List<String> uniques = Lists.newArrayList();
        Iterator var9 = propList.iterator();

        String text;
        while(var9.hasNext()) {
            GenCodeProp field = (GenCodeProp)var9.next();
            text = this.genfieldSql(field);
            retList.add(text);
            if (field.getIndex()) {
                indexText.add("CREATE INDEX ix_" + field.getColumnName() + " ON " + tableName + " (" + field.getColumnName() + ");");
            }

            if (field.getUnique()) {
                uniques.add("\tCONSTRAINT ux_" + field.getColumnName() + " UNIQUE(" + field.getColumnName() + "),");
            }
        }

        var9 = multipleColumnIndex.iterator();

        Iterator var12;
        String index;
        List columnUnique;
        while(var9.hasNext()) {
            columnUnique = (List)var9.next();
            text = "CREATE INDEX ix_";

            for(var12 = columnUnique.iterator(); var12.hasNext(); text = text + index + "_") {
                index = (String)var12.next();
            }

            text = text.substring(0, text.length() - 1);
            text = text + " ON " + tableName + " (";

            for(var12 = columnUnique.iterator(); var12.hasNext(); text = text + index + ",") {
                index = (String)var12.next();
            }

            text = text.substring(0, text.length() - 1);
            text = text + ");";
            indexText.add(text);
        }

        var9 = multipleColumnUnique.iterator();

        while(var9.hasNext()) {
            columnUnique = (List)var9.next();
            text = "\tCONSTRAINT ux_";

            for(var12 = columnUnique.iterator(); var12.hasNext(); text = text + index + "_") {
                index = (String)var12.next();
            }

            text = text.substring(0, text.length() - 1);
            text = text + " UNIQUE(";

            for(var12 = columnUnique.iterator(); var12.hasNext(); text = text + index + ",") {
                index = (String)var12.next();
            }

            text = text.substring(0, text.length() - 1);
            text = text + "),";
            uniques.add(text);
        }

        retList.addAll(uniques);
        retList.add("\tCONSTRAINT " + tableName + "_pk PRIMARY KEY (" + primaryKey.getColumnName() + "));");
        retList.add(generateAutoIncrementAndTrigger(tableName, primaryKey.getColumnName()));
        retList.addAll(indexText);
        String var10000 = Joiner.on("\n").join(retList);
        if (var10000 == null) {
            //$$$reportNull$$$0(1);
            throw new RuntimeException("OracleGenerateFilesHandler generateSql 有错误啊");

        }

        return var10000;
    }

    @NotNull
    public ClassValidateResult validateCurrentClass(PsiClass psiClass) {
        if (handlerValidator == null) {
            Class var2 = OracleGenerateFilesHandler.class;
            synchronized(OracleGenerateFilesHandler.class) {
                if (handlerValidator == null) {
                    handlerValidator = new HandlerValidator(new OracleGenerateFilesHandler.OracleFieldValidator());
                }
            }
        }

        ClassValidateResult var10000 = handlerValidator.validateResult(psiClass);
        if (var10000 == null) {
            //$$$reportNull$$$0(2);
            throw new RuntimeException("OracleGenerateFilesHandler validateCurrentClass 有错误啊");

        }

        return var10000;
    }

    private String genfieldSql(GenCodeProp field) {
        StringBuilder ret = new StringBuilder();
        ret.append("\t").append(field.getColumnName()).append(" ").append(field.getFiledType());
        if (StringUtils.isNotBlank(field.getSize())) {
            ret.append("(" + field.getSize() + ")");
        }

        if (!field.getPrimaryKey() && field.getHasDefaultValue() && StringUtils.isNotBlank(field.getDefaultValue())) {
            ret.append(" DEFAULT " + field.getDefaultValue());
        }

        if (!field.getCanBeNull()) {
            ret.append(" NOT NULL");
        }

        ret.append(",");
        return ret.toString();
    }

    private static String generateAutoIncrementAndTrigger(String tableName, String primaryColumnName) {
        return "create sequence " + tableName + "_seq start with 1 increment by 1 nomaxvalue;\ncreate trigger " + tableName + "_trigger\nbefore insert on " + tableName + "\nfor each row\n   begin\n     select " + tableName + "_seq.nextval into :new." + primaryColumnName + " from dual;\n   end;";
    }

    class OracleFieldValidator implements FieldValidator {
        OracleFieldValidator() {
        }

        public boolean isValidField(PsiField psiField) {
            String canonicalText = psiField.getType().getCanonicalText();
            List<TypeProps> typePropss = OracleHandlerUtils.getTypePropByQulitifiedName(canonicalText);
            if (typePropss != null) {
                return true;
            } else {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(psiField.getType());
                return psiClass != null && psiClass.isEnum();
            }
        }
    }
}
