package com.ccnode.codegenerator.database;


import com.ccnode.codegenerator.dialog.MapperUtil;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.FetchProp;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import com.ccnode.codegenerator.util.GenCodeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.util.PsiClassUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:03
 */
public class GenClassDialog extends DialogWrapper {
    private Project myProject;
    private List<FetchProp> fetchPropList;
    private Map<String, String> fieldMap;
    private String methodName;
    private String classPackageName;
    private List<FieldInfo> fieldInfoList;
    private List<ColumnAndField> columnAndFields = new ArrayList();
    private FieldToColumnRelation relation;
    private JTable myJtable;
    private JTextField classFolderText;
    private String modulePath;
    private JTextField resultMapText;
    private JTextField classNameText;
    private String classQutifiedName;
    private GenClassInfo genClassInfo;
    private FieldToColumnRelation extractFieldToColumnRelation;

    public GenClassDialog(Project project, List<FetchProp> props, Map<String, String> fieldMap, String methodName, FieldToColumnRelation relation, PsiClass srcClass) {
        super(project, true);
        this.myProject = project;
        this.fetchPropList = props;
        this.fieldMap = fieldMap;
        this.methodName = methodName;
        this.relation = relation;
        this.fieldInfoList = this.buildClassInfo(props, fieldMap, relation);
        this.classFolderText = new JTextField(srcClass.getContainingFile().getVirtualFile().getParent().getPath());
        this.classNameText = new JTextField(GenCodeUtil.getUpperStart(methodName + "Result"));
        this.resultMapText = new JTextField(methodName + "Result");
        this.modulePath = PsiClassUtil.getModuleSrcPathOfClass(srcClass);
        this.myJtable = new JTable(extractValue(this.fieldInfoList), new String[]{"columnName", "fieldName", "fieldType"}) {
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        this.myJtable.getTableHeader().setReorderingAllowed(false);
        this.setTitle("create new Class for the result");
        this.init();
    }

    private static Object[][] extractValue(List<FieldInfo> fieldInfos) {
        Object[][] values = new Object[fieldInfos.size()][];

        for(int i = 0; i < fieldInfos.size(); ++i) {
            values[i] = new String[3];
            values[i][0] = ((FieldInfo)fieldInfos.get(i)).getColumnName();
            values[i][1] = ((FieldInfo)fieldInfos.get(i)).getFieldName();
            values[i][2] = ((FieldInfo)fieldInfos.get(i)).getFieldType();
        }

        return values;
    }

    private List<FieldInfo> buildClassInfo(List<FetchProp> props, Map<String, String> fieldMap, FieldToColumnRelation relation) {
        ArrayList<FieldInfo> fieldInfos = new ArrayList();
        Iterator var5 = props.iterator();

        while(var5.hasNext()) {
            FetchProp prop = (FetchProp)var5.next();
            if (prop.getFetchFunction() == null) {
                FieldInfo fieldInfo = new FieldInfo();
                String columnName = relation.getPropColumn(prop.getFetchProp());
                fieldInfo.setFieldName(prop.getFetchProp());
                fieldInfo.setFieldType((String)fieldMap.get(prop.getFetchProp()));
                fieldInfo.setColumnName(columnName);
                fieldInfos.add(fieldInfo);
            } else {
                String column = DbUtils.buildSelectFunctionVal(prop);
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setColumnName(column);
                fieldInfo.setFieldName(column);
                fieldInfo.setFieldType(DbUtils.getReturnClassFromFunction(fieldMap, prop.getFetchFunction(), prop.getFetchProp()));
                fieldInfos.add(fieldInfo);
            }
        }

        return fieldInfos;
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        int mygridy = 0;
        bag.anchor = 18;
        bag.fill = 1;
        bag.gridwidth = 1;
        bag.weightx = 1.0D;
        bag.weighty = 1.0D;
        byte var10001 = mygridy;
        int var5 = mygridy + 1;
        bag.gridy = var10001;
        bag.gridx = 0;
        jPanel.add(new JLabel("className"), bag);
        bag.gridx = 1;
        jPanel.add(this.classNameText, bag);
        bag.gridy = 1;
        bag.gridx = 0;
        jPanel.add(new JLabel("resultMapId:"), bag);
        bag.gridx = 1;
        jPanel.add(this.resultMapText, bag);
        bag.gridy = 3;
        bag.gridx = 0;
        jPanel.add(new JLabel(" class location:"), bag);
        bag.gridx = 1;
        jPanel.add(this.classFolderText, bag);
        bag.gridy = 4;
        bag.gridx = 1;
        JScrollPane jScrollPane = new JScrollPane(this.myJtable);
        jPanel.add(jScrollPane, bag);
        return jPanel;
    }

    protected void doOKAction() {
        String className = this.classNameText.getText();
        if (StringUtils.isBlank(className)) {
            Messages.showErrorDialog(this.myProject, "the className is empty, please reinput", "validefail");
        } else {
            String folder = this.classFolderText.getText();
            //String packageToModule = PsiClassUtil.getPackageToModule(folder, this.modulePath);
            String packageToModule = PsiClassUtil.getPackageToModule(folder, this.modulePath);
            int rowCount = this.myJtable.getRowCount();
            Set<String> importList = Sets.newHashSet();
            GenClassInfo info = new GenClassInfo();
            List<NewClassFieldInfo> fieldInfos = Lists.newArrayList();
            this.extractFieldToColumnRelation = new FieldToColumnRelation();
            this.extractFieldToColumnRelation.setResultMapId(this.resultMapText.getText());
            Map<String, String> fieldToColumnMap = new LinkedHashMap();
            this.extractFieldToColumnRelation.setFiledToColumnMap(fieldToColumnMap);

            for(int i = 0; i < rowCount; ++i) {
                String columnName = (String)this.myJtable.getValueAt(i, 0);
                String fieldName = (String)this.myJtable.getValueAt(i, 1);
                String fieldType = (String)this.myJtable.getValueAt(i, 2);
                fieldToColumnMap.put(fieldName, columnName);
                NewClassFieldInfo e = new NewClassFieldInfo();
                e.setFieldName(fieldName);
                e.setFieldShortType(MapperUtil.extractClassShortName(fieldType));
                if (this.checkIsNeedImport(fieldType)) {
                    importList.add(fieldType);
                }

                fieldInfos.add(e);
            }

            info.setClassFullPath(folder + "/" + className + ".java");
            info.setClassFieldInfos(fieldInfos);
            info.setClassName(className);
            info.setClassPackageName(packageToModule);
            info.setImportList(importList);
            this.genClassInfo = info;
            this.classQutifiedName = packageToModule + "." + className;
            super.doOKAction();
        }
    }

    private boolean checkIsNeedImport(String s) {
        return s != null && !s.startsWith("java.lang");
    }

    public List<ColumnAndField> getColumnAndFields() {
        return this.columnAndFields;
    }

    public String getClassQutifiedName() {
        return this.classQutifiedName;
    }

    public FieldToColumnRelation getExtractFieldToColumnRelation() {
        return this.extractFieldToColumnRelation;
    }

    public GenClassInfo getGenClassInfo() {
        return this.genClassInfo;
    }
}