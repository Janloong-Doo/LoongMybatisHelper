//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.enums.IconEnum;
import com.ccnode.codegenerator.myconfigurable.ConfigChangeListener;
import com.ccnode.codegenerator.myconfigurable.MyBatisCodeHelperApplicationComponent;
import com.ccnode.codegenerator.myconfigurable.MyConfigurable;
import com.ccnode.codegenerator.myconfigurable.PluginState;
import com.ccnode.codegenerator.myconfigurable.Profile;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconUtils {
    private static ImageIcon methodIcon;
    private static ImageIcon iconForMenu;
    private static ImageIcon mapperIcon;
    private static ImageIcon mapperXmlIcon;
    private static MyBatisCodeHelperApplicationComponent myBatisCodeHelperApplicationComponent;

    public IconUtils() {
    }

    public static Icon useMyBatisIcon() {
        return methodIcon;
    }

    public static Icon mapperIcon() {
        if (mapperIcon == null) {
            Profile defaultProfile = myBatisCodeHelperApplicationComponent.getState().getDefaultProfile();
            String iconName = defaultProfile.getIconName();
            IconEnum iconEnum = IconEnum.getIconEnum(iconName);
            if (iconEnum == null) {
                mapperIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(IconEnum.DEFAULT.getMapperIconPlace()));
            } else {
                mapperIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(iconEnum.getMapperIconPlace()));
            }
        }

        return mapperIcon;
    }

    public static Icon mapperxmlIcon() {
        if (mapperXmlIcon == null) {
            Profile defaultProfile = myBatisCodeHelperApplicationComponent.getState().getDefaultProfile();
            String iconName = defaultProfile.getIconName();
            IconEnum iconEnum = IconEnum.getIconEnum(iconName);
            if (iconEnum == null) {
                mapperIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(IconEnum.DEFAULT.getMapperIconPlace()));
            } else {
                mapperXmlIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(iconEnum.getMapperXmlIconPlace()));
            }
        }

        return mapperXmlIcon;
    }

    public static Icon useSmall() {
        return iconForMenu;
    }

    public static void setIcon(String name) {
        IconEnum iconEnum = IconEnum.getIconEnum(name);
        mapperXmlIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(iconEnum.getMapperXmlIconPlace()));
        mapperIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource(iconEnum.getMapperIconPlace()));
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        Project[] var3 = openProjects;
        int var4 = openProjects.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Project openProject = var3[var5];
            FileEditorManager fileEditorManager = FileEditorManager.getInstance(openProject);
            VirtualFile[] selectedFiles = fileEditorManager.getSelectedFiles();
            VirtualFile theFile = null;
            if (selectedFiles != null) {
                theFile = selectedFiles[0];
            }

            VirtualFile[] openFiles = fileEditorManager.getOpenFiles();
            VirtualFile[] var11 = openFiles;
            int var12 = openFiles.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                VirtualFile openFile = var11[var13];
                PsiFile file = PsiManager.getInstance(openProject).findFile(openFile);
                if (file != null) {
                    fileEditorManager.closeFile(openFile);
                    fileEditorManager.openFile(openFile, false);
                }
            }

            if (theFile != null) {
                fileEditorManager.openFile(theFile, true);
            }
        }

    }

    static {
        MyConfigurable.registerListener(new ConfigChangeListener() {
            public void onConfigChange(PluginState oldState, PluginState newState) {
                String oldIconName = oldState.getDefaultProfile().getIconName();
                String newIconName = newState.getDefaultProfile().getIconName();
                if (!oldIconName.equals(newIconName)) {
                    IconUtils.setIcon(newIconName);
                }

            }
        });
        methodIcon = new ImageIcon(IconUtils.class.getClassLoader().getResource("icon/mybatis_new_6.png"));
        iconForMenu = new ImageIcon(IconUtils.class.getClassLoader().getResource("icon/mybatis_new_7.png"));
        mapperIcon = null;
        mapperXmlIcon = null;
        myBatisCodeHelperApplicationComponent = MyBatisCodeHelperApplicationComponent.getInstance();
    }
}
