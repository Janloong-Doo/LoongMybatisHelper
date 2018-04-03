//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.datasource;

import com.ccnode.codegenerator.datasourceToolWindow.DatasourceState;
import com.ccnode.codegenerator.datasourceToolWindow.MyBatisDatasourceConfigView;
import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseConnector;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatasourceConnectionProperty;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.TableColumnInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.TableInfo;
import com.ccnode.codegenerator.mybatisGenerator.MybatisGeneratorForm;
import com.ccnode.codegenerator.view.completion.MysqlCompleteCacheInteface;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class MyBastisDatasourceForm {
    private JPanel myDatasourcePanel;
    private JButton addButton;
    private JButton refreshButton;
    private JButton configButton;
    private JButton consoleButton;
    private JTree datasourceTree;
    private Project myProject;
    private DatasourceState myState;
    private DefaultMutableTreeNode selectedNode;
    private Map<DefaultMutableTreeNode, NewDatabaseInfo> nodeDatabaseMap;
    private List<NewDatabaseInfo> myNewDatabaseInfos;

    public MyBastisDatasourceForm(Project project) {
        this.$$$setupUI$$$();
        this.nodeDatabaseMap = new HashMap();
        this.myNewDatabaseInfos = new ArrayList();
        this.myProject = project;
        MyBatisDatasourceComponent component = (MyBatisDatasourceComponent)this.myProject.getComponent(MyBatisDatasourceComponent.class);
        this.myState = component.getState();
        this.myNewDatabaseInfos = this.myState.getDatabaseInfos();
        this.addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyBatisDatasourceConfigView myBatisDatasourceConfigView = new MyBatisDatasourceConfigView(MyBastisDatasourceForm.this.myProject, false, MyBastisDatasourceForm.this.myNewDatabaseInfos);
                boolean b = myBatisDatasourceConfigView.showAndGet();
                if (b) {
                    NewDatabaseInfo newDatabaseInfo = myBatisDatasourceConfigView.getNewDatabaseInfo();
                    MyBastisDatasourceForm.this.myState.setActiveDatabaseInfo(newDatabaseInfo);
                    DatabaseInfo dataBaseInfoFromConnection = DatabaseConnector.getDataBaseInfoFromConnection(new DatasourceConnectionProperty(newDatabaseInfo.getDatabaseType(), newDatabaseInfo.getUrl(), newDatabaseInfo.getUserName(), newDatabaseInfo.getPassword(), newDatabaseInfo.getDatabase()));
                    DefaultMutableTreeNode top = new DefaultMutableTreeNode(dataBaseInfoFromConnection.getDatabaseName());
                    MyBastisDatasourceForm.this.nodeDatabaseMap.put(top, newDatabaseInfo);
                    MyBastisDatasourceForm.this.createNodes(top, dataBaseInfoFromConnection.getTableInfoList(), newDatabaseInfo);
                    DefaultTreeModel model = (DefaultTreeModel)MyBastisDatasourceForm.this.datasourceTree.getModel();
                    DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                    model.insertNodeInto(top, root, root.getChildCount());
                    MyBastisDatasourceForm.this.myNewDatabaseInfos.add(newDatabaseInfo);
                }
            }
        });
    }

    private void createNodes(DefaultMutableTreeNode top, List<TableInfo> tableInfos, NewDatabaseInfo info) {
        Iterator var4 = tableInfos.iterator();

        while(var4.hasNext()) {
            TableInfo tableInfo = (TableInfo)var4.next();
            DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(tableInfo.getTableName());
            this.nodeDatabaseMap.put(defaultMutableTreeNode, info);
            top.add(defaultMutableTreeNode);
        }

    }

    private void createUIComponents() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Data sources");
        this.datasourceTree = new JTree(top);
    }

    public JPanel getMyDatasourcePanel() {
        DefaultTreeModel model = (DefaultTreeModel)this.datasourceTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        Iterator var3 = this.myNewDatabaseInfos.iterator();

        while(var3.hasNext()) {
            NewDatabaseInfo databaseInfo = (NewDatabaseInfo)var3.next();
            DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(databaseInfo.getDatabase());
            DatabaseInfo info = DatabaseConnector.getDataBaseInfoFromConnection(new DatasourceConnectionProperty(databaseInfo.getDatabaseType(), databaseInfo.getUrl(), databaseInfo.getUserName(), databaseInfo.getPassword(), databaseInfo.getDatabase()));
            if (info != null) {
                this.createNodes(newChild, info.getTableInfoList(), databaseInfo);
                this.nodeDatabaseMap.put(newChild, databaseInfo);
                root.add(newChild);
            }
        }

        this.datasourceTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath pathForLocation = MyBastisDatasourceForm.this.datasourceTree.getPathForLocation(e.getX(), e.getY());
                    if (pathForLocation != null) {
                        MyBastisDatasourceForm.this.selectedNode = (DefaultMutableTreeNode)pathForLocation.getLastPathComponent();
                        MyBastisDatasourceForm.this.datasourceTree.getSelectionModel().setSelectionPath(pathForLocation);
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem mybatisGeneratorMenu = new JMenuItem("mybatis generator");
                        mybatisGeneratorMenu.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                DefaultMutableTreeNode parent = (DefaultMutableTreeNode)MyBastisDatasourceForm.this.selectedNode.getParent();
                                Object userObject = MyBastisDatasourceForm.this.selectedNode.getUserObject();
                                if (!(userObject instanceof String)) {
                                    System.out.println("hehe");
                                } else {
                                    String tableName = (String)userObject;
                                    if (MyBastisDatasourceForm.this.nodeDatabaseMap.containsKey(parent)) {
                                        NewDatabaseInfo info = (NewDatabaseInfo)MyBastisDatasourceForm.this.nodeDatabaseMap.get(parent);
                                        List<TableColumnInfo> tableColumnInfo = DatabaseConnector.getTableColumnInfo(info, tableName);
                                        MybatisGeneratorForm form = new MybatisGeneratorForm(info, tableName, tableColumnInfo, MyBastisDatasourceForm.this.myProject);
                                        boolean b = form.showAndGet();
                                        if (!b) {
                                            return;
                                        }
                                    }

                                }
                            }
                        });
                        JMenuItem setAsActiveDatasource = new JMenuItem("set current datasource as active");
                        JMenuItem removeDataSource = new JMenuItem("remove database");
                        removeDataSource.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                DefaultMutableTreeNode parent = (DefaultMutableTreeNode)MyBastisDatasourceForm.this.selectedNode.getParent();
                                if (MyBastisDatasourceForm.this.nodeDatabaseMap.containsKey(parent)) {
                                    MyBastisDatasourceForm.this.removeNodeFromTree(parent);
                                } else {
                                    MyBastisDatasourceForm.this.removeNodeFromTree(MyBastisDatasourceForm.this.selectedNode);
                                }

                            }
                        });
                        setAsActiveDatasource.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                NewDatabaseInfo info = (NewDatabaseInfo)MyBastisDatasourceForm.this.nodeDatabaseMap.get(MyBastisDatasourceForm.this.selectedNode);
                                if (info != null) {
                                    MysqlCompleteCacheInteface service = (MysqlCompleteCacheInteface)ServiceManager.getService(MyBastisDatasourceForm.this.myProject, MysqlCompleteCacheInteface.class);
                                    service.cleanAll();
                                    service.addDatabaseCache(info);
                                    MyBastisDatasourceForm.this.myState.setActiveDatabaseInfo(info);
                                    Messages.showMessageDialog("success", "success", (Icon)null);
                                } else {
                                    Messages.showErrorDialog(MyBastisDatasourceForm.this.myProject, "can't find database for it", "fail");
                                }
                            }
                        });
                        mybatisGeneratorMenu.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.out.println(MyBastisDatasourceForm.this.nodeDatabaseMap.get(MyBastisDatasourceForm.this.selectedNode));
                            }
                        });
                        popupMenu.add(mybatisGeneratorMenu);
                        popupMenu.add(setAsActiveDatasource);
                        if (MyBastisDatasourceForm.this.nodeDatabaseMap.containsKey(MyBastisDatasourceForm.this.selectedNode)) {
                            popupMenu.add(removeDataSource);
                        }

                        popupMenu.show(MyBastisDatasourceForm.this.datasourceTree, e.getX(), e.getY());
                    }

                    System.out.println("right mouse");
                }

            }
        });
        return this.myDatasourcePanel;
    }

    private void removeNodeFromTree(DefaultMutableTreeNode parent) {
        NewDatabaseInfo o = (NewDatabaseInfo)this.nodeDatabaseMap.get(parent);
        this.myNewDatabaseInfos.remove(o);
        this.nodeDatabaseMap.remove(parent);
        if (o.equals(this.myState.getActiveDatabaseInfo())) {
            this.myState.setActiveDatabaseInfo((NewDatabaseInfo)null);
        }

        ((MysqlCompleteCacheInteface)ServiceManager.getService(this.myProject, MysqlCompleteCacheInteface.class)).cleanAll();
        ((DefaultTreeModel)this.datasourceTree.getModel()).removeNodeFromParent(parent);
    }
}
