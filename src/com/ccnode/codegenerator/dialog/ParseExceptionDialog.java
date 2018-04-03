//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;

public class ParseExceptionDialog extends DialogWrapper {
    private String methodName;
    private Integer start;
    private Integer end;
    private String errorMsg;

    public ParseExceptionDialog(@Nullable Project project, String methodName, Integer start, Integer end, String errorMsg) {
        super(project, true);
        this.methodName = methodName;
        this.start = start;
        this.end = end;
        this.errorMsg = errorMsg;
        this.setTitle("parse methodname catch exception");
        this.setOKActionEnabled(false);
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        bag.fill = 2;
        bag.anchor = 17;
        bag.gridx = 0;
        bag.gridy = 0;
        bag.insets = new Insets(0, 0, 5, 0);
        if (this.start != null && this.end != null) {
            jPanel.add(new JLabel("methodname: " + this.methodName.substring(0, this.start)), bag);
            bag.gridx = 1;
            JLabel errorPart = new JLabel(this.methodName.substring(this.start, this.end));
            errorPart.setForeground(Color.RED);
            errorPart.setOpaque(true);
            jPanel.add(errorPart, bag);
            bag.gridx = 2;
            jPanel.add(new JLabel(this.methodName.substring(this.end)), bag);
        } else {
            jPanel.add(new JLabel("methodname: " + this.methodName), bag);
        }

        bag.gridx = 0;
        bag.gridy = 1;
        jPanel.add(new JLabel(this.errorMsg), bag);
        return jPanel;
    }
}
