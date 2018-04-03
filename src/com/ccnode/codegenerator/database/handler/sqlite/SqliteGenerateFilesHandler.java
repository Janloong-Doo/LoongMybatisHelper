//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

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

public class SqliteGenerateFilesHandler implements GenerateFileHandler {
    private static volatile SqliteGenerateFilesHandler instance;
    private static volatile HandlerValidator handlerValidator;

    private SqliteGenerateFilesHandler() {
    }

    public static SqliteGenerateFilesHandler getInstance() {
        if (instance == null) {
            Class var0 = SqliteGenerateFilesHandler.class;
            synchronized(SqliteGenerateFilesHandler.class) {
                if (instance == null) {
                    instance = new SqliteGenerateFilesHandler();
                }
            }
        }

        return instance;
    }

    @NotNull
    public List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        List var10000 = SqliteHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            throw new RuntimeException("SqliteGenerateFilesHandler getRecommendDatabaseTypeOfFieldType 有错误啊");

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

        String text;
        for(int i = 0; i < propList.size(); ++i) {
            GenCodeProp field = (GenCodeProp)propList.get(i);
            text = this.genfieldSql(field);
            if (i == propList.size() - 1) {
                text = text.substring(0, text.length() - 1);
            }

            retList.add(text);
            if (field.getIndex()) {
                indexText.add("CREATE INDEX ix_" + field.getColumnName() + " ON " + tableName + " (" + field.getColumnName() + ");");
            }

            if (field.getUnique()) {
                uniques.add("\tCREATE UNIQUE INDEX ux_" + field.getColumnName() + " ON " + tableName + " (" + field.getColumnName() + ");");
            }
        }

        retList.add(");");
        Iterator var14 = multipleColumnIndex.iterator();

        Iterator var12;
        String index;
        List columnUnique;
        while(var14.hasNext()) {
            columnUnique = (List)var14.next();
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

        var14 = multipleColumnUnique.iterator();

        while(var14.hasNext()) {
            columnUnique = (List)var14.next();
            text = "\tCREATE UNIQUE INDEX ux_";

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
            uniques.add(text);
        }

        retList.addAll(uniques);
        retList.addAll(indexText);
        String var10000 = Joiner.on("\n").join(retList);
        if (var10000 == null) {
            //$$$reportNull$$$0(1);
            throw new RuntimeException("SqliteGenerateFilesHandler generateSql 有错误啊");

        }

        return var10000;
    }

    @NotNull
    public ClassValidateResult validateCurrentClass(PsiClass psiClass) {
        if (handlerValidator == null) {
            Class var2 = SqliteGenerateFilesHandler.class;
            synchronized(SqliteGenerateFilesHandler.class) {
                if (handlerValidator == null) {
                    handlerValidator = new HandlerValidator(new SqliteGenerateFilesHandler.SqliteFieldValidator());
                }
            }
        }

        ClassValidateResult var10000 = handlerValidator.validateResult(psiClass);
        if (var10000 == null) {
            throw new RuntimeException("SqliteGenerateFilesHandler validateCurrentClass 有错误啊");

            //$$$reportNull$$$0(2);
        }

        return var10000;
    }

    private String genfieldSql(GenCodeProp field) {
        StringBuilder ret = new StringBuilder();
        ret.append("\t").append(field.getColumnName()).append(" ").append(field.getFiledType());
        if (StringUtils.isNotBlank(field.getSize())) {
            ret.append("(" + field.getSize() + ")");
        }

        if (field.getPrimaryKey()) {
            ret.append(" PRIMARY KEY");
        }

        if (!field.getCanBeNull()) {
            ret.append(" NOT NULL");
        }

        if (!field.getPrimaryKey() && field.getHasDefaultValue() && StringUtils.isNotBlank(field.getDefaultValue())) {
            ret.append(" DEFAULT " + field.getDefaultValue());
        }

        ret.append(",");
        return ret.toString();
    }

    class SqliteFieldValidator implements FieldValidator {
        SqliteFieldValidator() {
        }

        public boolean isValidField(PsiField psiField) {
            String canonicalText = psiField.getType().getCanonicalText();
            List<TypeProps> typePropss = SqliteHandlerUtils.getTypePropByQulitifiedName(canonicalText);
            if (typePropss != null) {
                return true;
            } else {
                PsiClass psiClass = PsiTypesUtil.getPsiClass(psiField.getType());
                return psiClass != null && psiClass.isEnum();
            }
        }
    }
}
