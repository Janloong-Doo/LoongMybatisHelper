//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.InsertFileProp;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndFieldAndFormattedColumn;
import com.ccnode.codegenerator.freemarker.TemplateUtil;
import com.ccnode.codegenerator.myconfigurable.MyBatisCodeHelperApplicationComponent;
import com.ccnode.codegenerator.pojo.ClassInfo;
import com.ccnode.codegenerator.util.FileUtils;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenMapperService {
    public GenMapperService() {
    }

    public static void generateMapperXmlUsingFtl(InsertFileProp fileProp, List<GenCodeProp> props, ClassInfo srcClass, InsertFileProp daoProp, String tableName, GenCodeProp primaryProp) {
        List<String> retList = Lists.newArrayList();
        Map<String, Object> root = Maps.newHashMap();
        root.put("daoFullType", daoProp.getQutifiedName());
        root.put("pojoFullType", srcClass.getQualifiedName());
        root.put("pojoName", GenCodeUtil.getLowerCamel(srcClass.getName()));
        List<ColumnAndFieldAndFormattedColumn> columnAndFields = (List)props.stream().map((prop) -> {
            if (prop.getPrimaryKey()) {
                root.put("primaryJdbcType", prop.getJdbcType());
            }

            ColumnAndFieldAndFormattedColumn columnAndField = new ColumnAndFieldAndFormattedColumn();
            columnAndField.setColumn(prop.getColumnName());
            columnAndField.setField(prop.getFieldName());
            columnAndField.setJdbcType(prop.getJdbcType());
            columnAndField.setFormattedColumn(DatabaseComponenent.formatColumn(prop.getColumnName()));
            return columnAndField;
        }).collect(Collectors.toList());
        root.put("useGeneratedKeys", MyBatisCodeHelperApplicationComponent.getInstance().getState().getProfile().getUseGeneratedKeys());
        root.put("fieldAndColumns", columnAndFields);
        root.put("primaryColumn", DatabaseComponenent.formatColumn(primaryProp.getColumnName()));
        root.put("primaryField", primaryProp.getFieldName());
        root.put("tableName", tableName);
        root.put("currentDatabase", DatabaseComponenent.currentDatabase());
        String generateMapperString = TemplateUtil.processToString("gencode/mapperxml.ftl", root);
        retList.add(generateMapperString);
        FileUtils.writeFiles(fileProp, retList);
    }
}
