//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.util.CollectionUtils;
import com.ccnode.codegenerator.util.IconUtils;
import com.ccnode.codegenerator.validate.PPValidator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisGeneratorAction extends AnAction implements DumbAware {
    public MybatisGeneratorAction() {
        super(IconUtils.useSmall());
    }

    public void actionPerformed(AnActionEvent e) {
        boolean validate = PPValidator.validate(e.getProject());
        if (validate) {
            PsiFile data = (PsiFile)e.getData(CommonDataKeys.PSI_FILE);
            if (data != null && data instanceof XmlFile) {
                XmlFile s = (XmlFile)data;
                s.getVirtualFile().refresh(false, false);
                String path = s.getVirtualFile().getPath();
                List<String> warnings = new ArrayList();
                boolean overwrite = true;
                File configFile = new File(path);
                ConfigurationParser cp = new ConfigurationParser(warnings);
                Configuration config = null;

                try {
                    config = cp.parseConfiguration(configFile);
                } catch (IOException var20) {
                    Messages.showErrorDialog(e.getProject(), printStack(var20), "parse config file error");
                    return;
                } catch (XMLParserException var21) {
                    Messages.showErrorDialog(e.getProject(), printStack(var21), "parse config file error");
                    return;
                }

                DefaultShellCallback callback = new DefaultShellCallback(overwrite);
                MyBatisGenerator myBatisGenerator = null;

                try {
                    myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
                } catch (InvalidConfigurationException var19) {
                    Messages.showErrorDialog(e.getProject(), printStack(var19), "config error");
                    return;
                }

                try {
                    myBatisGenerator.generate((ProgressCallback)null);
                } catch (SQLException var16) {
                    Messages.showErrorDialog(e.getProject(), printStack(var16), "error");
                    return;
                } catch (IOException var17) {
                    Messages.showErrorDialog(e.getProject(), printStack(var17), "error");
                    return;
                } catch (InterruptedException var18) {
                    Messages.showErrorDialog(e.getProject(), printStack(var18), "error");
                    return;
                }

                VirtualFileManager.getInstance().syncRefresh();
                if (CollectionUtils.isEmpty(warnings)) {
                    Messages.showInfoMessage(e.getProject(), "generate success", "success");
                } else {
                    String m = "";

                    String warning;
                    for(Iterator var14 = warnings.iterator(); var14.hasNext(); m = m + warning + "\n") {
                        warning = (String)var14.next();
                    }

                    Messages.showInfoMessage(e.getProject(), m, "warning");
                }

            }
        }
    }

    public void update(AnActionEvent e) {
        PsiFile data = (PsiFile)e.getData(CommonDataKeys.PSI_FILE);
        if (data == null || !(data instanceof XmlFile)) {
            e.getPresentation().setVisible(false);
        }

    }

    public static String printStack(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
