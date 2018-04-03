//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.database.GenClassInfo;
import com.ccnode.codegenerator.freemarker.TemplateUtil;
import com.ccnode.codegenerator.util.FileUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class GenClassService {
    public GenClassService() {
    }

    public static void generateClassFileUsingFtl(GenClassInfo classInfo) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("className", classInfo.getClassName());
        root.put("classPackage", classInfo.getClassPackageName());
        root.put("fields", classInfo.getClassFieldInfos());
        root.put("importList", classInfo.getImportList());
        String genClassString = TemplateUtil.processToString("newclass.ftl", root);
        List<String> lines = Lists.newArrayList();
        lines.add(genClassString);
        FileUtils.writeFiles(classInfo.getClassFullPath(), lines, classInfo.getClassName());
    }
}
