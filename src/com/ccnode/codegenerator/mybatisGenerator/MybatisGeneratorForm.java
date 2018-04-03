//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.mybatisGenerator;

import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseConnector;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.TableColumnInfo;
import com.ccnode.codegenerator.myconfigurable.MyBatisCodeHelperApplicationComponent;
import com.ccnode.codegenerator.myconfigurable.Profile;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.jetbrains.annotations.Nullable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGeneratorForm extends DialogWrapper {
    private NewDatabaseInfo myInfo;
    private String myTableName;
    private List<TableColumnInfo> myColumnInfo;
    private JTextField javaModelPackage;
    private JTextField javaModelName;
    private JTextField javaMapperPackgeTextField;
    private JTextField xmlMapperPackageTextField;
    private JTextField javaModelPath;
    private JTextField javaMapperPath;
    private JTextField xmlMapperPath;
    private Project myProject;

    public MybatisGeneratorForm(NewDatabaseInfo info, String tableName, List<TableColumnInfo> tableColumnInfo, Project myProject) {
        super(myProject, true);
        this.setTitle("run mybatis generator for " + tableName);
        this.myInfo = info;
        this.myTableName = tableName;
        this.myColumnInfo = tableColumnInfo;
        this.myProject = myProject;
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        Border border = jPanel.getBorder();
        new EmptyBorder(10, 10, 10, 10);
        GridBagLayout panelGridBagLayout = new GridBagLayout();
        panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
        panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        panelGridBagLayout.columnWeights = new double[]{0.0D, 1.0D, 4.9E-324D};
        panelGridBagLayout.rowWeights = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 4.9E-324D};
        jPanel.setLayout(panelGridBagLayout);
        this.javaModelName = new JTextField(GenCodeUtil.getUpperCamelFromUnderScore(this.myTableName));
        Profile profile = MyBatisCodeHelperApplicationComponent.getInstance().getState().getProfile();
        this.javaModelPackage = new JTextField(profile.getJavaModelPackage() == null ? "com.aa.model" : profile.getJavaModelPackage());
        this.javaMapperPackgeTextField = new JTextField(profile.getJavaMapperPackage() == null ? "com.aa.mapper" : profile.getJavaMapperPackage());
        this.xmlMapperPackageTextField = new JTextField(profile.getXmlMapperPackage() == null ? "com.aa.mapper" : profile.getXmlMapperPackage());
        String projectFilePath = this.myProject.getBasePath();
        this.javaModelPath = new JTextField(profile.getJavaModelPath() == null ? projectFilePath + "/src/main/java" : profile.getJavaModelPath());
        this.javaMapperPath = new JTextField(profile.getJavaMapperPath() == null ? projectFilePath + "/src/main/java" : profile.getJavaMapperPath());
        this.xmlMapperPath = new JTextField(profile.getXmlMapperPath() == null ? projectFilePath + "/src/main/resources" : profile.getXmlMapperPath());
        this.addLabelAndTextField("java model name", 0, jPanel, this.javaModelName);
        this.addLabelAndTextField("java model package", 1, jPanel, this.javaModelPackage);
        this.addLabelAndTextField("java mapper pakcage:", 2, jPanel, this.javaMapperPackgeTextField);
        this.addLabelAndTextField("xml mapper package:", 3, jPanel, this.xmlMapperPackageTextField);
        this.addLabelAndTextField("java model path", 4, jPanel, this.javaModelPath);
        this.addLabelAndTextField("java mapper path", 5, jPanel, this.javaMapperPath);
        this.addLabelAndTextField("xml mapper path", 6, jPanel, this.xmlMapperPath);
        return jPanel;
    }

    private void addLabelAndTextField(String labelText, int yPos, JPanel jPanel, JTextField javaModelTextField) {
        JLabel faxLabel = new JLabel(labelText);
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = 1;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = yPos;
        jPanel.add(faxLabel, gridBagConstraintForLabel);
        GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
        gridBagConstraintForTextField.fill = 1;
        gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForTextField.gridx = 1;
        gridBagConstraintForTextField.gridy = yPos;
        jPanel.add(javaModelTextField, gridBagConstraintForTextField);
        javaModelTextField.setColumns(25);
    }

    protected void doOKAction() {
        this.generateFiles();
        VirtualFileManager.getInstance().syncRefresh();
        Messages.showMessageDialog(this.myProject, "generate files success", "success", Messages.getInformationIcon());
        super.doOKAction();
    }

    private void generateFiles() {
        List<String> warnings = new ArrayList();
        boolean overwrite = true;
        Configuration config = new Configuration();
        Context context = new Context(ModelType.FLAT);
        context.setId("mySql");
        context.setTargetRuntime("MyBatis3");
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnectionConfiguration.setConnectionURL(DatabaseConnector.buildUrl("MySql", this.myInfo.getUrl(), this.myInfo.getDatabase()));
        jdbcConnectionConfiguration.setUserId(this.myInfo.getUserName());
        jdbcConnectionConfiguration.setPassword(this.myInfo.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        Profile profile = MyBatisCodeHelperApplicationComponent.getInstance().getState().getProfile();
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(this.javaModelPackage.getText());
        profile.setJavaModelPackage(this.javaModelPackage.getText());
        javaModelGeneratorConfiguration.setTargetProject(this.javaModelPath.getText());
        profile.setJavaModelPath(this.javaModelPath.getText());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(this.xmlMapperPackageTextField.getText());
        profile.setXmlMapperPackage(this.xmlMapperPackageTextField.getText());
        sqlMapGeneratorConfiguration.setTargetProject(this.xmlMapperPath.getText());
        profile.setXmlMapperPath(this.xmlMapperPath.getText());
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage(this.javaMapperPackgeTextField.getText());
        profile.setJavaMapperPackage(this.javaMapperPackgeTextField.getText());
        javaClientGeneratorConfiguration.setTargetProject(this.javaMapperPath.getText());
        profile.setJavaMapperPath(this.javaMapperPath.getText());
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        TableConfiguration tc = new TableConfiguration(context);
        tc.setTableName(this.myTableName);
        tc.setSchema((String)null);
        tc.setDomainObjectName(this.javaModelName.getText());
        tc.setCountByExampleStatementEnabled(false);
        tc.setSelectByExampleStatementEnabled(false);
        tc.setUpdateByExampleStatementEnabled(false);
        tc.setDeleteByExampleStatementEnabled(false);
        context.addTableConfiguration(tc);
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
        config.addContext(context);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;

        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (Exception var19) {
            var19.printStackTrace();
        }

        try {
            myBatisGenerator.generate((ProgressCallback)null);
        } catch (SQLException var16) {
            var16.printStackTrace();
        } catch (IOException var17) {
            var17.printStackTrace();
        } catch (InterruptedException var18) {
            var18.printStackTrace();
        }

        Iterator var14 = warnings.iterator();

        while(var14.hasNext()) {
            String warning = (String)var14.next();
            System.out.println(warning);
        }

    }
}
