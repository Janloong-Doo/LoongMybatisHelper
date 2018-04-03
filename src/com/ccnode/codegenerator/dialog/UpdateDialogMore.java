//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.dialog.datatype.ClassFieldInfo;
import com.ccnode.codegenerator.dialog.dto.MapperDto;
import com.ccnode.codegenerator.dialog.dto.mybatis.ClassMapperMethod;
import com.ccnode.codegenerator.dialog.dto.mybatis.ColumnAndField;
import com.ccnode.codegenerator.dialog.dto.mybatis.JCheckWithMapperSql;
import com.ccnode.codegenerator.dialog.dto.mybatis.JCheckWithResultMap;
import com.ccnode.codegenerator.dialog.dto.mybatis.JcheckWithMapperMethod;
import com.ccnode.codegenerator.dialog.dto.mybatis.MapperMethod;
import com.ccnode.codegenerator.dialog.dto.mybatis.MapperMethodEnum;
import com.ccnode.codegenerator.dialog.dto.mybatis.MapperSql;
import com.ccnode.codegenerator.dialog.dto.mybatis.ResultMap;
import com.ccnode.codegenerator.enums.MethodName;
import com.ccnode.codegenerator.pojo.FieldToColumnRelation;
import com.ccnode.codegenerator.util.DateUtil;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

public class UpdateDialogMore extends DialogWrapper {
    private final Set<String> defaultMethodSet;
    public static final String PARAMANNOSTART = "@Param(\"";
    private Project myProject;
    private PsiClass myClass;
    private XmlFile myXmlFile;
    private List<ClassFieldInfo> newAddFields;
    private List<ColumnAndField> deletedFields;
    private PsiClass myDaoClass;
    private List<ClassFieldInfo> propFields;
    private List<ColumnAndField> existingFields;
    private String message;
    private MapperDto mapperDto;
    private JTable myTable;
    private JLabel sqlLable;
    private JLabel sqlPathLable;
    private JRadioButton sqlFileRaidio;
    private JButton sqlOpenFolder;
    private JTextField sqlNameText;
    private JTextField sqlPathText;
    private List<JCheckWithResultMap> jCheckWithResultMaps;
    private List<JCheckWithMapperSql> jCheckWithMapperSqls;
    private List<JcheckWithMapperMethod> jcheckWithMapperMethods;

    protected void doOKAction() {
        try {
            this.validateInput();
        } catch (Exception var12) {
            Messages.showErrorDialog(this.myProject, var12.getMessage(), "validate fail");
            return;
        }

        List<GenCodeProp> newAddedProps = new ArrayList();

        for(int i = 0; i < this.newAddFields.size(); ++i) {
            GenCodeProp prop = MyJTable.getGenCodeProp(i, this.myTable);
            newAddedProps.add(prop);
        }

        PsiDocumentManager manager = PsiDocumentManager.getInstance(this.myProject);
        XmlDocument xmlDocument = this.myXmlFile.getDocument();
        this.jCheckWithResultMaps.forEach((item) -> {
            if (item.getjCheckBox().isSelected()) {
                this.handleWithResultMap(newAddedProps, this.deletedFields, item.getResultMap());
            }

        });
        this.jCheckWithMapperSqls.forEach((item) -> {
            if (item.getjCheckBox().isSelected()) {
                this.handleWithSql(newAddedProps, this.deletedFields, item.getMapperSql());
            }

        });
        List<ColumnAndField> finalDisplayFieldAndFormatedColumn = extractFinalField(this.existingFields, newAddedProps, this.deletedFields);
        String tableName = this.extractTableName();
        this.jcheckWithMapperMethods.forEach((item) -> {
            if (item.getjCheckBox().isSelected()) {
                this.handleWithMapperMethod(this.myClass, finalDisplayFieldAndFormatedColumn, tableName, item.getMapperMethod(), item.getClassMapperMethod(), manager);
            }

        });
        if (this.sqlFileRaidio.isSelected()) {
            List<String> retList = new ArrayList();
            String updateSql = this.generateUpdateSql(newAddedProps, tableName, this.deletedFields);
            retList.add(updateSql);
            String sqlFileName = this.sqlNameText.getText().trim();
            String sqlFileFolder = this.sqlPathText.getText().trim();

            try {
                String filePath = sqlFileFolder + "/" + sqlFileName;
                Files.write(Paths.get(filePath), retList, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException var11) {
                throw new RuntimeException("can't write file " + sqlFileName + " to path " + sqlFileFolder + "/" + sqlFileName);
            }
        }

        VirtualFileManager.getInstance().syncRefresh();
        Messages.showMessageDialog(this.myProject, "generate files success", "success", Messages.getInformationIcon());
        super.doOKAction();
    }

    private String generateUpdateSql(List<GenCodeProp> newAddedProps, String tableName, List<ColumnAndField> deletedFields) {
        return DatabaseComponenent.currentHandler().getUpdateFieldHandler().generateUpdateSql(newAddedProps, tableName, deletedFields);
    }

    private static List<ColumnAndField> extractFinalField(List<ColumnAndField> existingFields, List<GenCodeProp> newAddedProps, List<ColumnAndField> deletedFields) {
        List<ColumnAndField> finalFields = Lists.newArrayList();
        boolean deleted = false;
        Iterator var5 = existingFields.iterator();

        ColumnAndField finalField;
        while(var5.hasNext()) {
            finalField = (ColumnAndField)var5.next();
            Iterator var7 = deletedFields.iterator();

            while(var7.hasNext()) {
                ColumnAndField deletedField = (ColumnAndField)var7.next();
                if (deletedField.getField().equals(finalField.getField())) {
                    deleted = true;
                    break;
                }
            }

            if (!deleted) {
                finalFields.add(finalField);
            }
        }

        var5 = newAddedProps.iterator();

        while(var5.hasNext()) {
            GenCodeProp newAddedProp = (GenCodeProp)var5.next();
            ColumnAndField e = new ColumnAndField();
            e.setColumn(newAddedProp.getColumnName());
            e.setField(newAddedProp.getFieldName());
            finalFields.add(e);
        }

        var5 = finalFields.iterator();

        while(var5.hasNext()) {
            finalField = (ColumnAndField)var5.next();
            finalField.setColumn(DatabaseComponenent.formatColumn(finalField.getColumn()));
        }

        return finalFields;
    }

    private String extractTableName() {
        String tableName = "";
        XmlTag[] var2 = this.myXmlFile.getRootTag().getSubTags();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            XmlTag tag = var2[var4];
            if ("insert".equalsIgnoreCase(tag.getName())) {
                String insertText = tag.getValue().getText();
                tableName = MapperUtil.extractTable(insertText);
                if (tableName != null) {
                    return tableName;
                }
            }
        }

        return tableName;
    }

    private void handleWithMapperMethod(PsiClass myClass, List<ColumnAndField> finalFields, String tableName, MapperMethod mapperMethod, ClassMapperMethod classMapperMethod, PsiDocumentManager manager) {
        String newValueText = MapperUtil.generateMapperMethod(myClass, finalFields, tableName, mapperMethod.getType(), classMapperMethod);
        if (newValueText != null) {
            String finalValue = newValueText.replaceAll("\r", "");
            finalValue = finalValue.replaceAll("\\t", "\t");
            if (classMapperMethod.getMethodName().equals(MethodName.update.name())) {
                String oldValue = mapperMethod.getXmlTag().getValue().getText();
                int where = oldValue.toLowerCase().lastIndexOf("where");
                if (where != -1) {
                    finalValue = finalValue + "\t\t" + oldValue.substring(where);
                }
            } else {
                finalValue = finalValue + "\t";
            }

            WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                TextRange valueTextRange = mapperMethod.getXmlTag().getValue().getTextRange();
                XmlTag tagForMethodName = MyPsiXmlUtils.findTagForMethodName(this.myXmlFile, mapperMethod.getXmlTag().getName());
                Document document = manager.getDocument(this.myXmlFile);
                document.replaceString(valueTextRange.getStartOffset(), valueTextRange.getEndOffset(), finalValue);
                manager.commitDocument(document);
            });
        }
    }

    private void handleWithSql(List<GenCodeProp> newAddedProps, List<ColumnAndField> deletedFields, MapperSql mapperSql) {
        String sqlText = mapperSql.getTag().getValue().getText();
        String newValueText = MapperUtil.generateSql(newAddedProps, deletedFields, sqlText, this.existingFields);
        if (newValueText != null) {
            WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                mapperSql.getTag().getValue().setText(newValueText);
            });
        }
    }

    private void handleWithResultMap(List<GenCodeProp> newAddedProps, List<ColumnAndField> deletedFields, ResultMap resultMap) {
        XmlTag[] var4 = resultMap.getTag().getSubTags();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            XmlTag tag = var4[var6];
            String property = tag.getAttributeValue("property");
            if (StringUtils.isNotBlank(property)) {
                Iterator var9 = deletedFields.iterator();

                while(var9.hasNext()) {
                    ColumnAndField columnAndField = (ColumnAndField)var9.next();
                    if (property.equalsIgnoreCase(columnAndField.getField())) {
                        WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                            tag.delete();
                        });
                    }
                }
            }
        }

        Iterator var11 = newAddedProps.iterator();

        while(var11.hasNext()) {
            GenCodeProp prop = (GenCodeProp)var11.next();
            XmlTag result = resultMap.getTag().createChildTag("result", "", (String)null, false);
            result.setAttribute("column", prop.getColumnName());
            result.setAttribute("property", prop.getFieldName());
            WriteCommandAction.runWriteCommandAction(this.myProject, () -> {
                resultMap.getTag().addSubTag(result, false);
            });
        }

    }

    private void validateInput() {
        for(int i = 0; i < this.newAddFields.size(); ++i) {
            Object valueAt = this.myTable.getValueAt(i, 1);
            String message = "column name is empty on row " + i;
            Validate.notNull(valueAt, message, new Object[0]);
            if (!(valueAt instanceof String)) {
                throw new RuntimeException(message);
            }

            Validate.notBlank((String)valueAt, message, new Object[0]);
        }

        if (this.sqlFileRaidio.isSelected()) {
            Validate.notBlank(this.sqlNameText.getText(), "sql name is empty", new Object[0]);
            Validate.notBlank(this.sqlPathText.getText(), "sql path is empty", new Object[0]);
        }

    }

    public UpdateDialogMore(Project project, PsiClass srcClass, XmlFile xmlFile, PsiClass nameSpaceDaoClass, List<PsiField> validFields) {
        super(project, true);
        this.defaultMethodSet = Sets.newHashSet(new String[]{MethodName.insert.name(), MethodName.insertList.name(), MethodName.update.name(), MethodName.insertSelective.name()});
        this.sqlLable = new JLabel("sql file name:");
        this.sqlPathLable = new JLabel("sql file path:");
        this.sqlFileRaidio = new JRadioButton("generate updated sql", true);
        this.sqlOpenFolder = new JButton("open folder");
        this.myProject = project;
        this.myClass = srcClass;
        this.myXmlFile = xmlFile;
        this.myDaoClass = nameSpaceDaoClass;
        this.propFields = PsiClassUtil.buildPropFieldInfo(validFields);
        this.initNeedUpdate();
        this.sqlNameText = new JTextField(srcClass.getName() + "_update_" + DateUtil.formatYYYYMMDD(new Date()) + ".sql");
        this.sqlPathText = new JTextField(this.myClass.getContainingFile().getVirtualFile().getParent().getPath());
        this.setTitle("update mapper xml");
        this.init();
    }

    private void initNeedUpdate() {
        this.mapperDto = this.parseXml();
        this.extractAddAndDelete();
        if (this.newAddFields.size() == 0 && this.deletedFields.size() == 0) {
            this.message = "there is no field to update or add, please check again with your resultMap";
        } else {
            this.myTable = new MyJTable(this.newAddFields) {
                public boolean isCellEditable(int row, int column) {
                    return super.isCellEditable(row, column) && column != 5;
                }
            };
            this.addWithCheckBoxs();
        }
    }

    private void addWithCheckBoxs() {
        this.jCheckWithResultMaps = new ArrayList();
        this.mapperDto.getResultMapList().forEach((item) -> {
            JCheckWithResultMap e = new JCheckWithResultMap();
            e.setjCheckBox(new JCheckBox("resultMap id=" + item.getId(), true));
            e.setResultMap(item);
            this.jCheckWithResultMaps.add(e);
        });
        this.jCheckWithMapperSqls = new ArrayList();
        this.mapperDto.getSqls().forEach((item) -> {
            XmlTag tag = item.getTag();
            String text = tag.getValue().getText();
            boolean containField = false;
            Iterator var5 = this.existingFields.iterator();

            while(var5.hasNext()) {
                ColumnAndField existingField = (ColumnAndField)var5.next();
                if (text.contains(existingField.getColumn())) {
                    containField = true;
                    break;
                }
            }

            if (containField) {
                JCheckWithMapperSql e = new JCheckWithMapperSql();
                e.setMapperSql(item);
                e.setjCheckBox(new JCheckBox("sql id=" + item.getId(), true));
                this.jCheckWithMapperSqls.add(e);
            }

        });
        this.jcheckWithMapperMethods = new ArrayList();
        List<ClassMapperMethod> methods = new ArrayList();
        PsiMethod[] allMethods = this.myDaoClass.getAllMethods();
        PsiMethod[] var3 = allMethods;
        int var4 = allMethods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PsiMethod method = var3[var5];
            PsiParameter[] parameters = method.getParameterList().getParameters();
            PsiParameter[] var8 = parameters;
            int var9 = parameters.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                PsiParameter psiParameter = var8[var10];
                String typeText = psiParameter.getType().getCanonicalText();
                if (typeText.contains(this.myClass.getQualifiedName())) {
                    ClassMapperMethod mapperMethod = new ClassMapperMethod();
                    mapperMethod.setMethodName(method.getName());
                    if (typeText.startsWith("java.util")) {
                        mapperMethod.setList(true);
                    }

                    if (psiParameter.getText().startsWith("@Param(\"")) {
                        mapperMethod.setParamAnno(this.extractParam(psiParameter.getText()));
                    }

                    methods.add(mapperMethod);
                    break;
                }
            }
        }

        methods.forEach((item) -> {
            MapperMethod mapperMethod = (MapperMethod)this.mapperDto.getMapperMethodMap().get(item.getMethodName());
            if (mapperMethod != null && this.defaultMethodSet.contains(item.getMethodName())) {
                JcheckWithMapperMethod e = new JcheckWithMapperMethod();
                e.setjCheckBox(new JCheckBox("replace " + mapperMethod.getType().name() + " mapper id " + item.getMethodName(), true));
                e.setClassMapperMethod(item);
                e.setMapperMethod(mapperMethod);
                this.jcheckWithMapperMethods.add(e);
            }

        });
    }

    private String extractParam(String typeText) {
        String paramAnno = "";

        for(int i = "@Param(\"".length(); typeText.charAt(i) != '"' && i < typeText.length(); ++i) {
            paramAnno = paramAnno + typeText.charAt(i);
        }

        return paramAnno;
    }

    private void extractAddAndDelete() {
        this.newAddFields = new ArrayList();
        this.deletedFields = new ArrayList();
        Set<String> allFieldMap = new HashSet();
        this.propFields.forEach((item) -> {
            allFieldMap.add(item.getFieldName().toLowerCase());
        });
        Set<String> existingMap = new HashSet();
        this.existingFields.forEach((item) -> {
            existingMap.add(item.getField().toLowerCase());
        });
        this.propFields.forEach((item) -> {
            if (!existingMap.contains(item.getFieldName().toLowerCase())) {
                this.newAddFields.add(item);
            }

        });
        this.existingFields.forEach((item) -> {
            if (!allFieldMap.contains(item.getField().toLowerCase())) {
                this.deletedFields.add(item);
            }

        });
    }

    private MapperDto parseXml() {
        XmlTag[] subTags = this.myXmlFile.getRootTag().getSubTags();
        MapperDto dto = new MapperDto();
        List<ResultMap> resultMaps = new ArrayList();
        List<MapperSql> sqls = new ArrayList();
        Map<String, MapperMethod> methodMap = new HashMap();
        XmlTag[] var6 = subTags;
        int var7 = subTags.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            XmlTag subTag = var6[var8];
            String name = subTag.getName();
            byte var12 = -1;
            switch(name.hashCode()) {
                case -1819569153:
                    if (name.equals("resultMap")) {
                        var12 = 0;
                    }
                    break;
                case -1335458389:
                    if (name.equals("delete")) {
                        var12 = 4;
                    }
                    break;
                case -1183792455:
                    if (name.equals("insert")) {
                        var12 = 2;
                    }
                    break;
                case -906021636:
                    if (name.equals("select")) {
                        var12 = 5;
                    }
                    break;
                case -838846263:
                    if (name.equals("update")) {
                        var12 = 3;
                    }
                    break;
                case 114126:
                    if (name.equals("sql")) {
                        var12 = 1;
                    }
            }

            MapperMethod s;
            String id;
            switch(var12) {
                case 0:
                    ResultMap resultMap = this.buildResultMap(subTag);
                    if (resultMap.getType().equals(this.myClass.getQualifiedName())) {
                        this.existingFields = this.extractFileds(subTag);
                    }

                    resultMaps.add(resultMap);
                    break;
                case 1:
                    MapperSql sql = this.buildSql(subTag);
                    sqls.add(sql);
                    break;
                case 2:
                    s = this.extractMethod(subTag, MapperMethodEnum.INSERT);
                    id = subTag.getAttributeValue("id");
                    methodMap.put(id, s);
                    break;
                case 3:
                    s = this.extractMethod(subTag, MapperMethodEnum.UPDATE);
                    id = subTag.getAttributeValue("id");
                    methodMap.put(id, s);
                    break;
                case 4:
                    s = this.extractMethod(subTag, MapperMethodEnum.DELETE);
                    id = subTag.getAttributeValue("id");
                    methodMap.put(id, s);
                    break;
                case 5:
                    s = this.extractMethod(subTag, MapperMethodEnum.SELECT);
                    id = subTag.getAttributeValue("id");
                    methodMap.put(id, s);
            }
        }

        if (this.existingFields == null) {
            GenerateResultMapDialog generateResultMapDialog = new GenerateResultMapDialog(this.myProject, PsiClassUtil.buildPropFields(this.myClass), this.myClass.getQualifiedName());
            boolean b = generateResultMapDialog.showAndGet();
            this.existingFields = new ArrayList();
            if (b) {
                FieldToColumnRelation relation = generateResultMapDialog.getRelation();
                Map<String, String> filedToColumnMap = relation.getFiledToColumnMap();
                Iterator var19 = filedToColumnMap.keySet().iterator();

                while(var19.hasNext()) {
                    String m = (String)var19.next();
                    ColumnAndField e = new ColumnAndField();
                    e.setColumn((String)filedToColumnMap.get(m));
                    e.setField(m);
                    this.existingFields.add(e);
                }
            }
        }

        dto.setResultMapList(resultMaps);
        dto.setMapperMethodMap(methodMap);
        dto.setSqls(sqls);
        return dto;
    }

    private MapperMethod extractMethod(XmlTag subTag, MapperMethodEnum insert) {
        MapperMethod mapperMethod = new MapperMethod();
        mapperMethod.setType(insert);
        mapperMethod.setXmlTag(subTag);
        return mapperMethod;
    }

    private MapperSql buildSql(XmlTag subTag) {
        String id = subTag.getAttributeValue("id");
        MapperSql sql = new MapperSql();
        sql.setId(id);
        sql.setTag(subTag);
        return sql;
    }

    private List<ColumnAndField> extractFileds(XmlTag subTag) {
        List<ColumnAndField> props = new ArrayList();
        XmlTag[] var3 = subTag.getSubTags();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            XmlTag tag = var3[var5];
            String property = tag.getAttributeValue("property");
            if (StringUtils.isNotBlank(property)) {
                ColumnAndField info = new ColumnAndField();
                info.setField(property);
                info.setColumn(tag.getAttributeValue("column"));
                props.add(info);
            }
        }

        return props;
    }

    private ResultMap buildResultMap(XmlTag subTag) {
        ResultMap resultMap = new ResultMap();
        String id = subTag.getAttributeValue("id");
        String type = subTag.getAttributeValue("type");
        resultMap.setId(id);
        resultMap.setType(type);
        resultMap.setTag(subTag);
        return resultMap;
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        if (this.message != null) {
            jPanel.add(new JLabel(this.message), bag);
            this.setOKActionEnabled(false);
            return jPanel;
        } else {
            bag.anchor = 18;
            bag.fill = 1;
            bag.weighty = 1.0D;
            ++bag.gridy;
            jPanel.add(new JLabel("the following are new added fields:"), bag);
            ++bag.gridy;
            bag.gridx = 0;
            JScrollPane jScrollPane = new JScrollPane(this.myTable);
            bag.gridwidth = 10;
            bag.weighty = 5.0D;
            jPanel.add(jScrollPane, bag);
            bag.weighty = 1.0D;
            bag.gridwidth = 1;
            Iterator var4;
            if (this.deletedFields.size() > 0) {
                ++bag.gridy;
                jPanel.add(new JLabel("the following are deleted fields:"), bag);
                ++bag.gridx;

                for(var4 = this.deletedFields.iterator(); var4.hasNext(); ++bag.gridx) {
                    ColumnAndField columnAndField = (ColumnAndField)var4.next();
                    jPanel.add(new JLabel(columnAndField.getField()), bag);
                }
            }

            bag.gridx = 0;
            ++bag.gridy;
            bag.insets = new Insets(10, 0, 5, 10);
            jPanel.add(new JLabel("choose the statement you want to update:"), bag);
            bag.insets = new Insets(3, 3, 3, 3);
            ++bag.gridy;
            var4 = this.jCheckWithResultMaps.iterator();

            while(var4.hasNext()) {
                JCheckWithResultMap resultMap = (JCheckWithResultMap)var4.next();
                ++bag.gridy;
                jPanel.add(resultMap.getjCheckBox(), bag);
            }

            ++bag.gridy;
            var4 = this.jCheckWithMapperSqls.iterator();

            while(var4.hasNext()) {
                JCheckWithMapperSql sql = (JCheckWithMapperSql)var4.next();
                ++bag.gridy;
                jPanel.add(sql.getjCheckBox(), bag);
            }

            ++bag.gridy;
            var4 = this.jcheckWithMapperMethods.iterator();

            while(var4.hasNext()) {
                JcheckWithMapperMethod method = (JcheckWithMapperMethod)var4.next();
                ++bag.gridy;
                jPanel.add(method.getjCheckBox(), bag);
            }

            ++bag.gridy;
            bag.gridx = 0;
            bag.weightx = 0.25D;
            jPanel.add(this.sqlFileRaidio, bag);
            bag.gridx = 1;
            jPanel.add(this.sqlLable, bag);
            bag.gridx = 2;
            bag.weightx = 1.0D;
            jPanel.add(this.sqlNameText, bag);
            bag.gridx = 3;
            jPanel.add(this.sqlPathLable, bag);
            bag.gridx = 4;
            jPanel.add(this.sqlPathText, bag);
            this.sqlOpenFolder.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor();
                    fcd.setShowFileSystemRoots(true);
                    fcd.setTitle("Choose a folder...");
                    fcd.setDescription("choose the path to store file");
                    fcd.setHideIgnored(false);
                    FileChooser.chooseFiles(fcd, UpdateDialogMore.this.myProject, UpdateDialogMore.this.myProject.getBaseDir(), new com.intellij.util.Consumer<List<VirtualFile>>() {
                        public void consume(List<VirtualFile> files) {
                            UpdateDialogMore.this.sqlPathText.setText(((VirtualFile)files.get(0)).getPath());
                        }
                    });
                }
            });
            bag.gridx = 5;
            jPanel.add(this.sqlOpenFolder, bag);
            return jPanel;
        }
    }
}
