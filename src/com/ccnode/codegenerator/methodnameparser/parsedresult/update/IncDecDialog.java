//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.update;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class IncDecDialog extends DialogWrapper {
    private List<UpdateField> myUsePrefixProps;
    private Project myProject;
    private Map<String, IncDecUserResult> resultMap = new HashMap();

    public IncDecDialog(List<UpdateField> usePrefixProps, Project project) {
        super(project, true);
        this.myUsePrefixProps = usePrefixProps;
        this.myProject = project;
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        Iterator var3 = this.myUsePrefixProps.iterator();

        while(var3.hasNext()) {
            final UpdateField myUsePrefixProp = (UpdateField)var3.next();
            ++con.gridy;
            con.gridx = 0;
            JLabel jLabel = new JLabel(myUsePrefixProp.getPrefix() + " value for " + myUsePrefixProp.getProp());
            jPanel.add(jLabel, con);
            con.gridx = 1;
            final JTextField jTextField = new JTextField(20);
            jPanel.add(jTextField, con);
            con.gridx = 2;
            final JCheckBox jCheckBox = new JCheckBox("use param");
            jPanel.add(jCheckBox, con);
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    if (StringUtils.isNotBlank(jTextField.getText())) {
                        jCheckBox.setSelected(false);
                        IncDecUserResult result = new IncDecUserResult();
                        result.setUseParam(false);
                        result.setValue(jTextField.getText());
                        IncDecDialog.this.resultMap.put(myUsePrefixProp.getProp(), result);
                    }

                }

                public void removeUpdate(DocumentEvent e) {
                    if (StringUtils.isNotBlank(jTextField.getText())) {
                        jCheckBox.setSelected(false);
                        IncDecUserResult result = new IncDecUserResult();
                        result.setUseParam(false);
                        result.setValue(jTextField.getText());
                        IncDecDialog.this.resultMap.put(myUsePrefixProp.getProp(), result);
                    }

                }

                public void changedUpdate(DocumentEvent e) {
                    if (StringUtils.isNotBlank(jTextField.getText())) {
                        jCheckBox.setSelected(false);
                        IncDecUserResult result = new IncDecUserResult();
                        result.setUseParam(false);
                        result.setValue(jTextField.getText());
                        IncDecDialog.this.resultMap.put(myUsePrefixProp.getProp(), result);
                    }

                }
            });
            jCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (jCheckBox.isSelected()) {
                        jTextField.setText("");
                        IncDecUserResult result = new IncDecUserResult();
                        result.setUseParam(true);
                        IncDecDialog.this.resultMap.put(myUsePrefixProp.getProp(), result);
                    }

                }
            });
        }

        return jPanel;
    }

    protected void doOKAction() {
        Iterator var1 = this.myUsePrefixProps.iterator();

        UpdateField myUsePrefixProp;
        do {
            if (!var1.hasNext()) {
                super.doOKAction();
                return;
            }

            myUsePrefixProp = (UpdateField)var1.next();
        } while(this.resultMap.get(myUsePrefixProp.getProp()) != null);

        Messages.showErrorDialog(this.myProject, "please add value for " + myUsePrefixProp.getProp(), "error");
    }

    public Map<String, IncDecUserResult> getResultMap() {
        return this.resultMap;
    }

    public void setResultMap(Map<String, IncDecUserResult> resultMap) {
        this.resultMap = resultMap;
    }
}
