//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.datasourceToolWindow;

import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseConnector;
import com.ccnode.codegenerator.view.completion.MysqlCompleteCacheInteface;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

public class MyBatisDatasourceConfigView extends DialogWrapper {
    private JPanel jpanel;
    private JTextField urlField;
    private JTextField usernamefield;
    private JTextField passwordField;
    private JButton testConnectionButton;
    private JComboBox datasourceCombox;
    private JLabel testConnectionText;
    private JTextField databaseText;
    private JLabel databaseLabel;
    private NewDatabaseInfo newDatabaseInfo;
    private Project myProject;
    private List<NewDatabaseInfo> existingDatabaseInfo;

    public MyBatisDatasourceConfigView(@Nullable Project project, boolean canBeParent, List<NewDatabaseInfo> existingDatabaseInfo) {
        super(project, canBeParent);
        this.myProject = project;
        this.existingDatabaseInfo = existingDatabaseInfo;
        this.$$$setupUI$$$();
        this.setTitle("add database config");
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        this.testConnectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean b = DatabaseConnector.checkConnection((String)MyBatisDatasourceConfigView.this.datasourceCombox.getSelectedItem(), MyBatisDatasourceConfigView.this.urlField.getText(), MyBatisDatasourceConfigView.this.usernamefield.getText(), MyBatisDatasourceConfigView.this.passwordField.getText(), MyBatisDatasourceConfigView.this.databaseText.getText());
                if (!b) {
                    MyBatisDatasourceConfigView.this.testConnectionText.setText("failed");
                } else {
                    MyBatisDatasourceConfigView.this.testConnectionText.setText("success");
                }

                Timer t = new Timer(4000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        MyBatisDatasourceConfigView.this.testConnectionText.setText((String)null);
                    }
                });
                t.setRepeats(false);
                t.start();
            }
        });
        return this.jpanel;
    }

    protected void doOKAction() {
        if (StringUtils.isEmpty(this.databaseText.getText())) {
            Messages.showErrorDialog("the database should not empyt", "database is empty");
        } else {
            boolean b = DatabaseConnector.checkConnection((String)this.datasourceCombox.getSelectedItem(), this.urlField.getText(), this.usernamefield.getText(), this.passwordField.getText(), this.databaseText.getText());
            if (b) {
                this.newDatabaseInfo = new NewDatabaseInfo();
                this.newDatabaseInfo.setDatabaseType((String)this.datasourceCombox.getSelectedItem());
                this.newDatabaseInfo.setUrl(this.urlField.getText());
                this.newDatabaseInfo.setUserName(this.usernamefield.getText());
                this.newDatabaseInfo.setPassword(this.passwordField.getText());
                this.newDatabaseInfo.setDatabase(this.databaseText.getText());
                MysqlCompleteCacheInteface service = (MysqlCompleteCacheInteface)ServiceManager.getService(this.myProject, MysqlCompleteCacheInteface.class);
                service.cleanAll();
                service.addDatabaseCache(this.newDatabaseInfo);
                super.doOKAction();
            } else {
                Messages.showErrorDialog(this.myProject, "make sure you can connect to the database", "database connect fail");
            }

        }
    }

    public NewDatabaseInfo getNewDatabaseInfo() {
        return this.newDatabaseInfo;
    }
}
