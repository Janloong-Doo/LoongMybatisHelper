//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.enums.UrlManager;
import com.intellij.ide.browsers.BrowserLauncher;
import com.intellij.ide.browsers.WebBrowserManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ShowLearnMoreAction extends AnAction {
    public ShowLearnMoreAction() {
    }

    public void actionPerformed(AnActionEvent event) {
        BrowserLauncher.getInstance().browse(UrlManager.getMainPage(), WebBrowserManager.getInstance().getFirstActiveBrowser());
    }
}
