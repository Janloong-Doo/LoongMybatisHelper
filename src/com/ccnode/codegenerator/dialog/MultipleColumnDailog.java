//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.dialog.datatype.ClassFieldInfo;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nullable;

public class MultipleColumnDailog extends DialogWrapper {
    private List<String> selectedFields = Lists.newArrayList();
    private List<String> classFields = Lists.newArrayList();
    private List<JCheckBox> checkBoxes = Lists.newArrayList();
    private JTextField jTextField = new JTextField("");

    public MultipleColumnDailog(List<ClassFieldInfo> propFields, Project myProject, String title) {
        super(myProject, false);
        Iterator var4 = propFields.iterator();

        while(var4.hasNext()) {
            ClassFieldInfo propField = (ClassFieldInfo)var4.next();
            this.classFields.add(propField.getFieldName());
        }

        this.setTitle(title);
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        final GridBagConstraints bag = new GridBagConstraints();
        this.jTextField.setEditable(false);
        this.jTextField.setColumns(20);
        jPanel.add(this.jTextField, bag);
        ++bag.gridx;
        JButton button = new JButton("clear");
        jPanel.add(button, bag);
        bag.gridx = 0;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = MultipleColumnDailog.this.jTextField.getText();
                if (text.length() != 0) {
                    String[] split = text.split(",");
                    if (split.length > 0) {
                        String[] var4 = split;
                        int var5 = split.length;

                        for(int var6 = 0; var6 < var5; ++var6) {
                            String s = var4[var6];
                            JCheckBox box = new JCheckBox(s);
                            ++bag.gridy;
                            jPanel.add(box, bag);
                            box.addActionListener((e1) -> {
                                if (box.isEnabled()) {
                                    jPanel.remove(box);
                                    String text1 = MultipleColumnDailog.this.jTextField.getText();
                                    if (text1.length() > 0) {
                                        MultipleColumnDailog.this.jTextField.setText(MultipleColumnDailog.this.jTextField.getText() + "," + box.getText());
                                    } else {
                                        MultipleColumnDailog.this.jTextField.setText(box.getText());
                                    }

                                    jPanel.revalidate();
                                    jPanel.repaint();
                                }

                            });
                        }

                        MultipleColumnDailog.this.jTextField.setText("");
                        MultipleColumnDailog.this.jTextField.setColumns(20);
                        jPanel.revalidate();
                        jPanel.repaint();
                    }

                }
            }
        });
        ++bag.gridy;
        bag.gridx = 0;
        Iterator var4 = this.classFields.iterator();

        while(var4.hasNext()) {
            String classField = (String)var4.next();
            ++bag.gridy;
            JCheckBox box = new JCheckBox(classField);
            box.addActionListener((e1) -> {
                if (box.isEnabled()) {
                    jPanel.remove(box);
                    String text1 = this.jTextField.getText();
                    if (text1.length() > 0) {
                        this.jTextField.setText(this.jTextField.getText() + "," + box.getText());
                    } else {
                        this.jTextField.setText(box.getText());
                    }

                    jPanel.revalidate();
                    jPanel.repaint();
                }

            });
            jPanel.add(box, bag);
            this.checkBoxes.add(box);
        }

        ++bag.gridy;
        return jPanel;
    }

    protected void doOKAction() {
        if (this.jTextField.getText().length() > 0) {
            String text = this.jTextField.getText();
            String[] split = text.split(",");
            String[] var3 = split;
            int var4 = split.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                this.selectedFields.add(s);
            }
        }

        super.doOKAction();
    }

    public List<String> getSelectedFields() {
        return this.selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }
}
