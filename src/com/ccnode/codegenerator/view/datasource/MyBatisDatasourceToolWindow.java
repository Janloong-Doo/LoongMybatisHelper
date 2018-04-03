//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.datasource;

import com.ccnode.codegenerator.util.IconUtils;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentFactory.SERVICE;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

public class MyBatisDatasourceToolWindow implements ToolWindowFactory, DumbAware {
    private JPanel myToolWindowContent;
    private ToolWindow myToolWindow;

    public MyBatisDatasourceToolWindow() {
    }

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }

        if (toolWindow == null) {
            $$$reportNull$$$0(1);
        }

        this.myToolWindowContent = (new MyBastisDatasourceForm(project)).getMyDatasourcePanel();
        this.myToolWindow = toolWindow;
        ContentFactory instance = SERVICE.getInstance();
        Content content = instance.createContent(this.myToolWindowContent, "", false);
        content.setIcon(IconUtils.useMyBatisIcon());
        toolWindow.getContentManager().addContent(content);
    }

    public void init(ToolWindow window) {
    }

    public boolean shouldBeAvailable(@NotNull Project project) {
        if (project == null) {
            $$$reportNull$$$0(2);
        }

        return true;
    }

    public boolean isDoNotActivateOnStart() {
        return false;
    }
}
