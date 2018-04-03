//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import org.jetbrains.annotations.Nullable;

public class ChooseParsedResultToUseDialog extends DialogWrapper {
    private List<String> xmlTagAndInfos;
    private Integer choosedIndex = 0;

    public Integer getChoosedIndex() {
        return this.choosedIndex;
    }

    public ChooseParsedResultToUseDialog(@Nullable Project project, List<String> tags) {
        super(project, true);
        this.xmlTagAndInfos = tags;
        this.setTitle("exist mutiple parse result");
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
        jpanel.add(new JLabel("please choose one"), bag);
        ButtonGroup buttonGroup = new ButtonGroup();

        for(int i = 0; i < this.xmlTagAndInfos.size(); ++i) {
            bag.gridx = 0;
            ++bag.gridy;
            JTextArea comp = new JTextArea((String)this.xmlTagAndInfos.get(i));
            comp.setEditable(false);
            jpanel.add(comp, bag);
            bag.gridx = 1;
            JRadioButton jRadioButton = new JRadioButton("", true);
            jRadioButton.setActionCommand(String.valueOf(i));
            jRadioButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    ChooseParsedResultToUseDialog.this.choosedIndex = Integer.parseInt(actionCommand);
                }
            });
            buttonGroup.add(jRadioButton);
            jpanel.add(jRadioButton, bag);
        }

        bag.fill = 2;
        bag.gridy = 2;
        return jpanel;
    }
}
