//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.myconfigurable;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@State(
        name = "MyBatisCodeHelper",
        storages = {@Storage(
                id = "MyBatisCodeHelper",
                file = "$APP_CONFIG$/MyBatisCodeHelper.xml"
        )}
)
public class MyBatisCodeHelperApplicationComponent implements ApplicationComponent, PersistentStateComponent<PluginState> {
    private PluginState settings;

    public MyBatisCodeHelperApplicationComponent() {
    }

    public static MyBatisCodeHelperApplicationComponent getInstance() {
        return (MyBatisCodeHelperApplicationComponent)ApplicationManager.getApplication().getComponent(MyBatisCodeHelperApplicationComponent.class);
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        if ("MyBatisCodeHelper" == null) {
            $$$reportNull$$$0(0);
        }

        return "MyBatisCodeHelper";
    }

    public PluginState getState() {
        if (this.settings == null) {
            this.settings = new PluginState();
            this.settings.setProfile(DefaultState.createDefault());
        }

        return this.settings;
    }

    public void loadState(PluginState state) {
        this.settings = state;
    }
}
