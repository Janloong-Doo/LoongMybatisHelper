//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.pojo;

import com.intellij.psi.PsiClass;

public class DomainClassInfo {
    private PsiClass domainClass;
    private DomainClassSourceType domainClassSourceType;

    public DomainClassInfo() {
    }

    public PsiClass getDomainClass() {
        return this.domainClass;
    }

    public void setDomainClass(PsiClass domainClass) {
        this.domainClass = domainClass;
    }

    public DomainClassSourceType getDomainClassSourceType() {
        return this.domainClassSourceType;
    }

    public void setDomainClassSourceType(DomainClassSourceType domainClassSourceType) {
        this.domainClassSourceType = domainClassSourceType;
    }
}
