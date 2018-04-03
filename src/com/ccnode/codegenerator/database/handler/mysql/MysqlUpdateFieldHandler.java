//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.intellij.psi.PsiField;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public class MysqlUpdateFieldHandler implements UpdateFieldHandler {
    private static volatile MysqlUpdateFieldHandler mInstance;

    private MysqlUpdateFieldHandler() {
    }

    public static MysqlUpdateFieldHandler getInstance() {
        if (mInstance == null) {
            Class var0 = MysqlUpdateFieldHandler.class;
            synchronized(MysqlUpdateFieldHandler.class) {
                if (mInstance == null) {
                    mInstance = new MysqlUpdateFieldHandler();
                }
            }
        }

        return mInstance;
    }

    public String generateUpdateSql(List<GenCodeProp> newAddedProps, String tableName, List<ColumnAndField> deletedFields) {
        StringBuilder ret = new StringBuilder();

        Iterator var5;
        for(var5 = newAddedProps.iterator(); var5.hasNext(); ret.append(";")) {
            GenCodeProp field = (GenCodeProp)var5.next();
            ret.append("ALTER TABLE " + tableName + "\n\tADD " + field.getColumnName());
            UnsignedCheckResult result = MysqlHandlerUtils.checkUnsigned(field.getFiledType());
            ret.append(" " + result.getType());
            if (StringUtils.isNotBlank(field.getSize())) {
                ret.append("(" + field.getSize() + ")");
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

            if (field.getHasDefaultValue() && StringUtils.isNotBlank(field.getDefaultValue())) {
                ret.append(" DEFAULT " + field.getDefaultValue());
            }

            if (field.getPrimaryKey()) {
                ret.append(" AUTO_INCREMENT");
            }

            ret.append(" COMMENT '" + field.getFieldName() + "'");
            if (field.getIndex()) {
                ret.append(",\n\tADD INDEX (" + field.getColumnName() + ")");
            }
        }

        var5 = deletedFields.iterator();

        while(var5.hasNext()) {
            ColumnAndField deletedField = (ColumnAndField)var5.next();
            ret.append("ALTER TABLE " + tableName + " DROP COLUMN ");
            ret.append(deletedField.getColumn() + ";");
        }

        return ret.toString();
    }

    @NotNull
    public List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        List var10000 = MysqlHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }
}
