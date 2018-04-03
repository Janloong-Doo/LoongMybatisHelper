//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.myconfigurable;

import com.ccnode.codegenerator.enums.IconEnum;

import javax.swing.*;
import java.awt.*;

public class SettingDialog {
    private JPanel rootComponent;
    private JLabel databaseLabel;
    private JComboBox databaseCombox;
    private JCheckBox addMapperAnnotationCheckBox;
    private JCheckBox useGeneratedKeysCheckBox;
    private JCheckBox mysqlUsingWithDashCheckBox;
    private JCheckBox useWithIfTestCheckBox;
    private JCheckBox generateMethodXmlInServiceCheckBox;
    private JCheckBox generateMethodXmlInServiceInterfaceCheckBOx;
    private JTextField mapperPrefixTextField;
    private JComboBox iconBox;
    private PluginState settings;

    public SettingDialog(PluginState state) {
        this.init(state);
    }

    private void init(PluginState state) {
        this.settings = state;
        Profile profile = state.getProfile();
        this.rootComponent = new JPanel(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        bag.anchor = 18;
        bag.gridx = 0;
        bag.gridy = 0;
        bag.weightx = 0.1D;
        bag.weighty = 1.0D;
        this.databaseLabel = new JLabel("database:");
        Font font = new Font("Monospaced", 0, 15);
        this.databaseLabel.setFont(font);
        this.rootComponent.add(this.databaseLabel, bag);
        this.databaseCombox = new JComboBox();
        this.databaseCombox.addItem("MySql");
        this.databaseCombox.addItem("Oracle");
        this.databaseCombox.addItem("Sqlite");

        for(int i = 0; i < this.databaseCombox.getItemCount(); ++i) {
            Object itemAt = this.databaseCombox.getItemAt(i);
            if (itemAt.equals(profile.getDatabase())) {
                this.databaseCombox.setSelectedIndex(i);
                break;
            }
        }

        bag.gridx = 1;
        bag.weightx = 0.5D;
        this.rootComponent.add(this.databaseCombox, bag);
        bag.gridy = 1;
        bag.gridx = 0;
        bag.weighty = 1.0D;
        this.addMapperAnnotationCheckBox = new JCheckBox("add @Mapper to mybatis interface", profile.getAddMapperAnnotation());
        this.rootComponent.add(this.addMapperAnnotationCheckBox, bag);
        bag.gridy = 2;
        bag.gridx = 0;
        this.useGeneratedKeysCheckBox = new JCheckBox("use generated keys", profile.getUseGeneratedKeys());
        this.rootComponent.add(this.useGeneratedKeysCheckBox, bag);
        bag.gridy = 3;
        bag.gridx = 0;
        this.mysqlUsingWithDashCheckBox = new JCheckBox("mysql use with `", profile.getMysqlUseWithDash());
        this.rootComponent.add(this.mysqlUsingWithDashCheckBox, bag);
        bag.gridy = 4;
        bag.gridx = 0;
        this.useWithIfTestCheckBox = new JCheckBox("generate with if test", profile.getGenerateWithIfTest());
        this.rootComponent.add(this.useWithIfTestCheckBox, bag);
        bag.gridy = 5;
        bag.gridx = 0;
        this.generateMethodXmlInServiceInterfaceCheckBOx = new JCheckBox("generate method xml in service interface", profile.getGenerateMethodInServiceInterface());
        this.rootComponent.add(this.generateMethodXmlInServiceInterfaceCheckBOx, bag);
        bag.gridy = 6;
        bag.gridx = 0;
        this.generateMethodXmlInServiceCheckBox = new JCheckBox("generate method xml in service", profile.getGenerateMethodInService());
        this.rootComponent.add(this.generateMethodXmlInServiceCheckBox, bag);
        bag.gridy = 7;
        bag.gridx = 0;
        this.rootComponent.add(new JLabel("mapper suffix"), bag);
        bag.gridx = 1;
        this.mapperPrefixTextField = new JTextField(profile.getMapperPrefix());
        this.rootComponent.add(this.mapperPrefixTextField, bag);
        bag.gridy = 8;
        bag.gridx = 0;
        this.rootComponent.add(new JLabel("mapper icon"), bag);
        bag.gridx = 1;
        this.iconBox = new JComboBox();
        IconEnum[] values = IconEnum.values();
        IconEnum[] var11 = values;
        int var7 = values.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            IconEnum value = var11[var8];
            this.iconBox.addItem(value.getIconName());
        }

        this.iconBox.setSelectedItem(profile.getIconName());
        this.rootComponent.add(this.iconBox, bag);
        bag.gridy = 9;
        bag.gridx = 0;
        bag.weighty = 10000.0D;
        this.rootComponent.add(new JPanel(), bag);
    }

    public JPanel getRootComponent() {
        return this.rootComponent;
    }

    public void importFrom(PluginState setting) {
        this.settings = setting;
        this.setData(setting.getDefaultProfile());
    }

    public void setData(Profile data) {
        this.databaseCombox.setSelectedItem(data.getDatabase());
        this.addMapperAnnotationCheckBox.setSelected(data.getAddMapperAnnotation());
        this.useGeneratedKeysCheckBox.setSelected(data.getUseGeneratedKeys());
        this.mysqlUsingWithDashCheckBox.setSelected(data.getMysqlUseWithDash());
        this.useWithIfTestCheckBox.setSelected(data.getGenerateWithIfTest());
        this.generateMethodXmlInServiceInterfaceCheckBOx.setSelected(data.getGenerateMethodInServiceInterface());
        this.generateMethodXmlInServiceCheckBox.setSelected(data.getGenerateMethodInService());
        this.mapperPrefixTextField.setText(data.getMapperPrefix());
        this.iconBox.setSelectedItem(data.getIconName());
    }

    public boolean isSettingModified(PluginState state) {
        this.getData(this.settings.getDefaultProfile());
        return !this.settings.equals(state);
    }

    public PluginState getSettings() {
        this.getData(this.settings.getDefaultProfile());
        return this.settings;
    }

    private void getData(Profile defaultProfile) {
        defaultProfile.setAddMapperAnnotation(this.addMapperAnnotationCheckBox.isSelected());
        defaultProfile.setDatabase((String)this.databaseCombox.getSelectedItem());
        defaultProfile.setUseGeneratedKeys(this.useGeneratedKeysCheckBox.isSelected());
        defaultProfile.setMysqlUseWithDash(this.mysqlUsingWithDashCheckBox.isSelected());
        defaultProfile.setGenerateWithIfTest(this.useWithIfTestCheckBox.isSelected());
        defaultProfile.setGenerateMethodInServiceInterface(this.generateMethodXmlInServiceInterfaceCheckBOx.isSelected());
        defaultProfile.setGenerateMethodInService(this.generateMethodXmlInServiceCheckBox.isSelected());
        defaultProfile.setMapperPrefix(this.mapperPrefixTextField.getText());
        String iconName = (String)this.iconBox.getSelectedItem();
        defaultProfile.setIconName(iconName);
    }
}
