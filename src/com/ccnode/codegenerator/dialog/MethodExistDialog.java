//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class MethodExistDialog extends DialogWrapper {
    private final Project myProject;
    private final String existMethodValue;

    public MethodExistDialog(@Nullable Project project, String existMethodValue) {
        super(project, true);
        this.myProject = project;
        this.existMethodValue = existMethodValue;
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jpanel = new JPanel(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        bag.fill = 2;
        bag.anchor = 17;
        bag.gridx = 0;
        bag.gridy = 0;
        bag.insets = new Insets(0, 0, 5, 0);
        jpanel.add(new JLabel("the method sql already exist in mapper,the existed value is:"), bag);
        bag.gridx = 0;
        bag.gridy = 1;
        JTextArea comp = new JTextArea(this.existMethodValue);
        comp.setEditable(false);
        jpanel.add(comp, bag);
        bag.fill = 2;
        bag.gridy = 2;
        jpanel.add(new JLabel("do you want to override them?"), bag);
        return jpanel;
    }
}
