//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.myconfigurable;

import com.ccnode.codegenerator.util.MyCloner;

public class PluginState extends DomainObject implements Cloneable {
    private Profile profile;

    public PluginState() {
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    protected PluginState clone() {
        return (PluginState)MyCloner.getCloner().deepClone(this);
    }

    public Profile getDefaultProfile() {
        if (this.profile != null) {
            return this.profile;
        } else {
            this.profile = new Profile();
            return this.profile;
        }
    }
}
