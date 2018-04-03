package com.ccnode.codegenerator.database;


import java.util.List;
import java.util.Set;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:04
 */
public class GenClassInfo {
    private String classPackageName;
    private String className;
    private Set<String> importList;
    private List<NewClassFieldInfo> classFieldInfos;
    private String classFullPath;

    public GenClassInfo() {
    }

    public String getClassPackageName() {
        return this.classPackageName;
    }

    public String getClassFullPath() {
        return this.classFullPath;
    }

    public void setClassFullPath(String classFullPath) {
        this.classFullPath = classFullPath;
    }

    public void setClassPackageName(String classPackageName) {
        this.classPackageName = classPackageName;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<String> getImportList() {
        return this.importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public List<NewClassFieldInfo> getClassFieldInfos() {
        return this.classFieldInfos;
    }

    public void setClassFieldInfos(List<NewClassFieldInfo> classFieldInfos) {
        this.classFieldInfos = classFieldInfos;
    }
}
