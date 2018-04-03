//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.dialog.InsertFileProp;
import com.ccnode.codegenerator.freemarker.TemplateUtil;
import com.ccnode.codegenerator.pojo.ClassInfo;
import com.ccnode.codegenerator.util.FileUtils;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class GenServiceInterfaceService {
    public GenServiceInterfaceService() {
    }

    public static void generateServiceIntefaceUsingFtl(InsertFileProp fileProp, ClassInfo srcClass) {
        List<String> retList = Lists.newArrayList();
        Map<String, Object> root = Maps.newHashMap();
        root.put("servicePackage", fileProp.getPackageName());
        root.put("pojoFullType", srcClass.getQualifiedName());
        root.put("serviceType", fileProp.getName());
        root.put("pojoType", srcClass.getName());
        root.put("pojoName", GenCodeUtil.getLowerCamel(srcClass.getName()));
        String generateServiceString = TemplateUtil.processToString("gencode/service_interface.ftl", root);
        retList.add(generateServiceString);
        FileUtils.writeFiles(fileProp, retList);
    }
}
