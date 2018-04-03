//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genCode;

import com.ccnode.codegenerator.dialog.InsertFileProp;
import com.ccnode.codegenerator.freemarker.TemplateUtil;
import com.ccnode.codegenerator.myconfigurable.MyBatisCodeHelperApplicationComponent;
import com.ccnode.codegenerator.pojo.ClassInfo;
import com.ccnode.codegenerator.util.FileUtils;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class GenDaoService {
    public GenDaoService() {
    }

    public static void generateDaoFileUsingFtl(InsertFileProp daoProp, ClassInfo srcClass) {
        Map<String, Object> root = Maps.newHashMap();
        root.put("daoPackageName", daoProp.getPackageName());
        root.put("pojoFullType", srcClass.getQualifiedName());
        root.put("daoType", daoProp.getName());
        root.put("pojoType", srcClass.getName());
        root.put("pojoName", GenCodeUtil.getLowerCamel(srcClass.getName()));
        root.put("addMapperAnnotation", MyBatisCodeHelperApplicationComponent.getInstance().getState().getProfile().getAddMapperAnnotation());
        String genDaoString = TemplateUtil.processToString("gencode/dao.ftl", root);
        List<String> lines = Lists.newArrayList();
        lines.add(genDaoString);
        FileUtils.writeFiles(daoProp, lines);
    }
}
