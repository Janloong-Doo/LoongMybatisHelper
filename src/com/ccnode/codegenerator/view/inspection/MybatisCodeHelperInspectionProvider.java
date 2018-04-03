//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.inspection;

import com.intellij.codeInspection.InspectionToolProvider;

public class MybatisCodeHelperInspectionProvider implements InspectionToolProvider {
    public MybatisCodeHelperInspectionProvider() {
    }

    public Class[] getInspectionClasses() {
        return new Class[]{MybatisMethodNotFindInXmlInspection.class, MybatisXmlNotUsedUsedInspection.class};
    }
}
