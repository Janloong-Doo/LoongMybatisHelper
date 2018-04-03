package com.ccnode.codegenerator.database.handler;

import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;

import java.util.List;

public interface UpdateFieldHandler extends JTableRecommendHandler {
    String generateUpdateSql(List<GenCodeProp> var1, String var2, List<ColumnAndField> var3);
}
