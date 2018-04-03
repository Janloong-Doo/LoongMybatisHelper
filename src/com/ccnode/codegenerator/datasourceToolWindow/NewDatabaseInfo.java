//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NewDatabaseInfo {
    private String databaseType;
    private String url;
    private String userName;
    private String password;
    private String database;

    public NewDatabaseInfo() {
    }

    public String getDatabaseType() {
        return this.databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            NewDatabaseInfo that = (NewDatabaseInfo)o;
            return (new EqualsBuilder()).append(this.databaseType, that.databaseType).append(this.url, that.url).append(this.userName, that.userName).append(this.password, that.password).append(this.database, that.database).isEquals();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (new HashCodeBuilder(17, 37)).append(this.databaseType).append(this.url).append(this.userName).append(this.password).append(this.database).toHashCode();
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
