//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.datasource;

import com.ccnode.codegenerator.datasourceToolWindow.DatasourceState;
import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.ccnode.codegenerator.validate.PPValidator;
import com.ccnode.codegenerator.view.completion.MysqlCompleteCacheInteface;
import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications.Bus;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.event.HyperlinkEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "MyBatisCodeHelperDatasource",
        storages = {@Storage(
                id = "MyBatisCodeHelperDatasource",
                file = "MyBatisCodeHelperDatasource.xml"
        )}
)
public class MyBatisDatasourceComponent implements ProjectComponent, PersistentStateComponent<DatasourceState> {
    private DatasourceState state;
    private Project myProject;

    public MyBatisDatasourceComponent(Project project) {
        this.myProject = project;
    }

    public void projectOpened() {
        (new Thread(() -> {
            boolean valid = PPValidator.isValid();
            if (!valid) {
                Notification notification = new Notification("mybatisCodeHelper", "MybatisCodeHelperPro plugin is not activated yet", "you can <a href='enter key'>enter key</a> or you can go <a href='http://brucege.com/pay/view'>here</a> to buy the product", NotificationType.ERROR, new NotificationListener() {
                    public void hyperlinkUpdate(@NotNull Notification notification, @NotNull HyperlinkEvent event) {
                        if (notification == null) {
                            $$$reportNull$$$0(0);
                        }

                        if (event == null) {
                            $$$reportNull$$$0(1);
                        }

                        String description = event.getDescription();
                        if ("enter key".equals(description)) {
                            boolean validate = PPValidator.validate((Project)null);
                            if (validate) {
                                Messages.showMessageDialog(MyBatisDatasourceComponent.this.myProject, "success", "validate success", (Icon)null);
                            }
                        } else {
                            URL url = event.getURL();
                            BrowserUtil.browse(url);
                        }

                    }
                });
                Bus.notify(notification, this.myProject);
                if (this.state == null) {
                    return;
                }

                NewDatabaseInfo activeDatabaseInfo = this.state.getActiveDatabaseInfo();
                if (activeDatabaseInfo != null) {
                    ((MysqlCompleteCacheInteface)ServiceManager.getService(this.myProject, MysqlCompleteCacheInteface.class)).addDatabaseCache(activeDatabaseInfo);
                }
            }

        })).start();
    }

    public void projectClosed() {
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        if ("MyBatisDataSourceComponent" == null) {
            $$$reportNull$$$0(0);
        }

        return "MyBatisDataSourceComponent";
    }

    @Nullable
    public DatasourceState getState() {
        if (this.state == null) {
            DatasourceState datasourceState = new DatasourceState();
            datasourceState.setDatabaseInfos(new ArrayList());
            this.state = datasourceState;
        }

        if (this.state.getDatabaseInfos() == null) {
            this.state.setDatabaseInfos(new ArrayList());
        }

        return this.state;
    }

    public void loadState(DatasourceState state) {
        this.state = state;
    }
}
