//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import javax.swing.Icon;

public class GenCodeAction extends AnAction {
    public GenCodeAction() {
        super("Text _Boxes");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project)event.getData(PlatformDataKeys.PROJECT);
        Messages.showMessageDialog(project, "please use alt+insert (generate mybatis files) on domain Class instead\n(ctrl+N on mac. same key shortcut for generate getter setter method)", "action not supported", (Icon)null);
    }
}
