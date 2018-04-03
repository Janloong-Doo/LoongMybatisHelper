//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.myconfigurable;

import com.google.common.collect.Lists;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class MyConfigurable implements Configurable {
    public MyBatisCodeHelperApplicationComponent applicationComponent = MyBatisCodeHelperApplicationComponent.getInstance();
    private SettingDialog form;
    private static List<ConfigChangeListener> configChangeListeners = Lists.newArrayList();

    public MyConfigurable() {
    }

    @Nls
    public String getDisplayName() {
        return "MyBatisCodeHelperPro";
    }

    @Nullable
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    public JComponent createComponent() {
        if (this.form == null) {
            this.form = new SettingDialog(this.applicationComponent.getState().clone());
        }

        return this.form.getRootComponent();
    }

    public boolean isModified() {
        return this.form != null && this.form.isSettingModified(this.applicationComponent.getState());
    }

    public void apply() throws ConfigurationException {
        PluginState state = this.applicationComponent.getState();
        PluginState formSettings = this.form.getSettings();
        Iterator var3 = configChangeListeners.iterator();

        while(var3.hasNext()) {
            ConfigChangeListener configChangeListener = (ConfigChangeListener)var3.next();
            configChangeListener.onConfigChange(state, formSettings);
        }

        this.applicationComponent.loadState(formSettings);
    }

    public void reset() {
        if (this.form != null) {
            this.form.importFrom(this.applicationComponent.getState().clone());
        }

    }

    public void disposeUIResources() {
        this.form = null;
    }

    public static void registerListener(ConfigChangeListener listener) {
        configChangeListeners.add(listener);
    }
}
