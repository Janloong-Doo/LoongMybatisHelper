//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.UpdateFieldHandler;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.intellij.psi.PsiField;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class SqliteUpdateFiledHandler implements UpdateFieldHandler {
    private static volatile SqliteUpdateFiledHandler mInstance;

    private SqliteUpdateFiledHandler() {
    }

    public static SqliteUpdateFiledHandler getInstance() {
        if (mInstance == null) {
            Class var0 = SqliteUpdateFiledHandler.class;
            synchronized(SqliteUpdateFiledHandler.class) {
                if (mInstance == null) {
                    mInstance = new SqliteUpdateFiledHandler();
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
        List var10000 = SqliteHandlerUtils.getRecommendDatabaseTypeOfFieldType(field);
        if (var10000 == null) {
            //$$$reportNull$$$0(0);
            throw new RuntimeException("SqliteUpdateFiledHandler getRecommendDatabaseTypeOfFieldType 有错误啊");

        }

        return var10000;
    }
}
