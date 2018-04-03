//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow;

import java.util.ArrayList;
import java.util.List;

public class DatasourceState {
    private List<NewDatabaseInfo> databaseInfos = new ArrayList();
    private NewDatabaseInfo activeDatabaseInfo;

    public DatasourceState() {
    }

    public List<NewDatabaseInfo> getDatabaseInfos() {
        return this.databaseInfos;
    }

    public void setDatabaseInfos(List<NewDatabaseInfo> databaseInfos) {
        this.databaseInfos = databaseInfos;
    }

    public NewDatabaseInfo getActiveDatabaseInfo() {
        return this.activeDatabaseInfo;
    }

    public void setActiveDatabaseInfo(NewDatabaseInfo activeDatabaseInfo) {
        this.activeDatabaseInfo = activeDatabaseInfo;
    }
}
