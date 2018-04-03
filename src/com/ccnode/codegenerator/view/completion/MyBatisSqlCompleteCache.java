//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import com.ccnode.codegenerator.datasourceToolWindow.NewDatabaseInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseConnector;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatabaseInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.DatasourceConnectionProperty;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.TableColumnInfo;
import com.ccnode.codegenerator.datasourceToolWindow.dbInfo.TableInfo;
import com.ccnode.codegenerator.sqlparse.TableNameAndFieldName;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MyBatisSqlCompleteCache implements MysqlCompleteCacheInteface {
    private List<String> tables = new ArrayList();
    private ArrayListMultimap<String, String> tableToField = ArrayListMultimap.create();
    private Map<CompleteField, String> fieldMap = new HashMap();

    public MyBatisSqlCompleteCache() {
    }

    public void addDatabaseCache(NewDatabaseInfo newDatabaseInfo) {
        DatabaseInfo root = DatabaseConnector.getDataBaseInfoFromConnection(new DatasourceConnectionProperty(newDatabaseInfo.getDatabaseType(), newDatabaseInfo.getUrl(), newDatabaseInfo.getUserName(), newDatabaseInfo.getPassword(), newDatabaseInfo.getDatabase()));
        if (root == null) {
            System.out.println("can't connect to db");
        } else {
            List<TableInfo> tableInfoList = root.getTableInfoList();
            if (tableInfoList != null && tableInfoList.size() != 0) {
                Iterator var4 = tableInfoList.iterator();

                while(true) {
                    TableInfo tableInfo;
                    List tableColumnInfos;
                    do {
                        do {
                            if (!var4.hasNext()) {
                                return;
                            }

                            tableInfo = (TableInfo)var4.next();
                            this.tables.add(tableInfo.getTableName());
                            tableColumnInfos = tableInfo.getTableColumnInfos();
                        } while(tableColumnInfos == null);
                    } while(tableColumnInfos.size() == 0);

                    Iterator var7 = tableColumnInfos.iterator();

                    while(var7.hasNext()) {
                        TableColumnInfo tableColumnInfo = (TableColumnInfo)var7.next();
                        this.tableToField.put(tableInfo.getTableName(), tableColumnInfo.getFieldName());
                        this.fieldMap.put(new CompleteField(newDatabaseInfo.getDatabaseType(), tableInfo.getTableName(), tableColumnInfo.getFieldName()), tableColumnInfo.getFieldType());
                    }
                }
            } else {
                System.out.println("there is no table exist in db");
            }
        }
    }

    public List<String> getRecommendFromCache(String currentText, String allText) {
        return new ArrayList();
    }

    public List<String> getAllTables() {
        return this.tables;
    }

    public List<String> getAllFields() {
        List<String> fields = Lists.newArrayList();
        Iterator var2 = this.tableToField.entries().iterator();

        while(var2.hasNext()) {
            Entry<String, String> map = (Entry)var2.next();
            fields.add(map.getValue());
        }

        return fields;
    }

    public List<TableNameAndFieldName> getAllFieldsWithTable() {
        List<TableNameAndFieldName> fields = Lists.newArrayList();
        Iterator var2 = this.tableToField.entries().iterator();

        while(var2.hasNext()) {
            Entry<String, String> map = (Entry)var2.next();
            TableNameAndFieldName tableNameAndFieldName = new TableNameAndFieldName();
            tableNameAndFieldName.setTableName((String)map.getKey());
            tableNameAndFieldName.setFieldName((String)map.getValue());
            fields.add(tableNameAndFieldName);
        }

        return fields;
    }

    public List<TableNameAndFieldName> getTableAllFields(String tableName) {
        List<String> strings = this.tableToField.get(tableName);
        List<TableNameAndFieldName> tableNameAndFieldNames = Lists.newArrayList();
        Iterator var4 = strings.iterator();

        while(var4.hasNext()) {
            String string = (String)var4.next();
            TableNameAndFieldName tableNameAndFieldName = new TableNameAndFieldName();
            tableNameAndFieldName.setTableName(tableName);
            tableNameAndFieldName.setFieldName(string);
            tableNameAndFieldNames.add(tableNameAndFieldName);
        }

        return tableNameAndFieldNames;
    }

    public String getFieldType() {
        return null;
    }

    public void cleanAll() {
        this.fieldMap = new HashMap();
        this.tables = Lists.newArrayList();
        this.tableToField = ArrayListMultimap.create();
    }
}
