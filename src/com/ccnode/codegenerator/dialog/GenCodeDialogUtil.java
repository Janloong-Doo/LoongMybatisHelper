//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.datatype.ClassFieldInfo;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GenCodeDialogUtil {
    public GenCodeDialogUtil() {
    }

    static Map<String, List<TypeProps>> extractMap(List<ClassFieldInfo> propFields) {
        Map<String, List<TypeProps>> fieldTypeMap = new HashMap();
        Iterator var2 = propFields.iterator();

        while(var2.hasNext()) {
            ClassFieldInfo info = (ClassFieldInfo)var2.next();
            fieldTypeMap.put(info.getFieldName(), DatabaseComponenent.currentHandler().getGenerateFileHandler().getRecommendDatabaseTypeOfFieldType(info.getPsiField()));
        }

        return fieldTypeMap;
    }
}
