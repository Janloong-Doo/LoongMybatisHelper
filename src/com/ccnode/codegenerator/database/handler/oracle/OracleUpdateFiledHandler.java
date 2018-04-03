//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.intellij.psi.PsiField;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class OracleUpdateFiledHandler implements UpdateFieldHandler {
    private static volatile OracleUpdateFiledHandler mInstance;

    private OracleUpdateFiledHandler() {
    }

    public static OracleUpdateFiledHandler getInstance() {
        if (mInstance == null) {
            Class var0 = OracleUpdateFiledHandler.class;
            synchronized(OracleUpdateFiledHandler.class) {
                if (mInstance == null) {
                    mInstance = new OracleUpdateFiledHandler();
                }
            }
        }

        return mInstance;
    }

    public String generateUpdateSql(List<GenCodeProp> newAddedProps, String tableName, List<ColumnAndField> deletedFields) {
        return null;
    }

    @NotNull
    public List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        List var10000 = OracleHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            throw new RuntimeException("OracleUpdateFiledHandler getRecommendDatabaseTypeOfFieldType 有错误啊");

        }

        return var10000;
    }
}
