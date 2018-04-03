//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.dailog;

import com.ccnode.codegenerator.methodnameparser.parsedresult.base.QueryRule;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;

public class AddIfTestDialog extends DialogWrapper {
    private List<QueryRule> myQueryRule;

    public AddIfTestDialog(Project project, List<QueryRule> queryRules) {
        super(project, true);
        this.setTitle("add if test to queryRules");
        this.myQueryRule = queryRules;
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

        for(Iterator var3 = this.myQueryRule.iterator(); var3.hasNext(); ++bag.gridy) {
            QueryRule queryRule = (QueryRule)var3.next();
            JCheckBox jCheckBox = new JCheckBox();
            bag.gridx = 0;
            jCheckBox.addActionListener((e) -> {
                if (jCheckBox.isSelected()) {
                    queryRule.setUseIfTest(true);
                } else {
                    queryRule.setUseIfTest(false);
                }

            });
            jpanel.add(jCheckBox, bag);
            bag.gridx = 1;
            JLabel label = new JLabel(queryRule.getProp());
            jpanel.add(label, bag);
        }

        return jpanel;
    }
}
