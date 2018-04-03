//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.database.DatabaseComponenent;
import com.ccnode.codegenerator.database.handler.utils.JdbcTypeUtils;
import com.ccnode.codegenerator.dialog.datatype.ClassFieldInfo;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.ccnode.codegenerator.dialog.exception.NotStringException;
import com.ccnode.codegenerator.util.GenCodeUtil;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import org.jetbrains.annotations.NotNull;

class MyJTable extends JTable {
    public static final String COLUMNUNIQUE = "unique";
    public static final String FILEDCOLUMN = "filed";
    public static final String COLUMN_NAMECOLUMN = "columnName";
    public static final String TYPECOLUMN = "type";
    public static final String PRIMARYCOLUMN = "primary";
    public static final String LENGTHCOLUMN = "length";
    public static final String CANBENULLCOLUMN = "canBeNull";
    public static final String HAS_DEFAULTVALUE_COLUMN = "hasDefaultValue";
    public static final String DEFAULT_VALUE_COLUMN = "defaultValue";
    public static final String INDEX_COLUMN = "index";
    public static final String COMMENT_COLUMN = "comment";
    public static final int FIELDCOLUMNINDEX = 0;
    public static final int COLUMN_NAMECOLUMNINDEX = 1;
    public static final int TYPECOLUMNINDEX = 2;
    public static final int LENGTHCOLUMNINDEX = 3;
    public static final int UNIQUECOLUMNINDEX = 4;
    public static final int PRIMARYCOLUMNINDEX = 5;
    public static final int CANBENULLCOLUMNINDEX = 6;
    public static final int HAS_DEFAULTVALUE_COLUMNINDEX = 7;
    public static final int DEFAULT_VALUECOLUMNINDEX = 8;
    public static final int INDEXCOLUMNINDEX = 9;
    public static final int COMMENT_COLUMN_INDEX = 10;
    public static String[] columnNames = new String[]{"filed", "columnName", "type", "length", "unique", "primary", "canBeNull", "hasDefaultValue", "defaultValue", "index", "comment"};
    private Map<String, List<TypeProps>> myFieldTypeMap;

    public MyJTable(List<ClassFieldInfo> propFields) {
        super(getDatas(propFields), columnNames);
        this.myFieldTypeMap = GenCodeDialogUtil.extractMap(propFields);
        this.getTableHeader().setReorderingAllowed(false);
        this.getColumn("unique").setCellRenderer(new CheckButtonRender());
        this.getColumn("unique").setCellEditor(new CheckButtonEditor(new JCheckBox()));
        this.getColumn("primary").setCellRenderer(new CheckButtonRender());
        this.getColumn("primary").setCellEditor(new CheckButtonEditor(new JCheckBox()));
        this.getColumn("canBeNull").setCellRenderer(new CheckButtonRender());
        this.getColumn("canBeNull").setCellEditor(new CheckButtonEditor(new JCheckBox()));
        this.getColumn("hasDefaultValue").setCellRenderer(new CheckButtonRender());
        this.getColumn("hasDefaultValue").setCellEditor(new CheckButtonEditor(new JCheckBox()));
        this.getColumn("index").setCellRenderer(new CheckButtonRender());
        this.getColumn("index").setCellEditor(new CheckButtonEditor(new JCheckBox()));
        this.getColumn("type").setCellRenderer(new MyComboBoxRender(this.myFieldTypeMap));
        this.getColumn("type").setCellEditor(new MyComboBoxEditor(new JComboBox(), this.myFieldTypeMap));
        this.setRowHeight(25);
        this.setFillsViewportHeight(true);
        this.setAutoResizeMode(4);
    }

    static Object[][] getDatas(List<ClassFieldInfo> propFields) {
        List<Object[]> ssList = new ArrayList();

        for(int i = 0; i < propFields.size(); ++i) {
            Object[] mm = new Object[columnNames.length];
            ClassFieldInfo info = (ClassFieldInfo)propFields.get(i);
            mm[0] = info.getFieldName();
            mm[1] = GenCodeUtil.getUnderScore(info.getFieldName());
            TypeProps typeProp = (TypeProps)DatabaseComponenent.currentHandler().getGenerateFileHandler().getRecommendDatabaseTypeOfFieldType(info.getPsiField()).stream().min(Comparator.comparingInt(TypeProps::getOrder)).get();
            if (typeProp != null) {
                mm[2] = typeProp.getDefaultType();
                mm[3] = typeProp.getSize();
                mm[4] = typeProp.getUnique();
                mm[5] = typeProp.getPrimary();
                mm[6] = typeProp.getCanBeNull();
                mm[8] = typeProp.getDefaultValue();
                mm[7] = typeProp.getHasDefaultValue();
                mm[9] = typeProp.getIndex();
                mm[10] = info.getComment();
                ssList.add(mm);
            }
        }

        Object[][] ss = new Object[ssList.size()][];
        ssList.toArray(ss);
        return ss;
    }

    static Boolean formatBoolean(Object unique) {
        if (unique == null) {
            return false;
        } else {
            return !(unique instanceof Boolean) ? false : (Boolean)unique;
        }
    }

    static String formatString(Object value) {
        if (value == null) {
            return "";
        } else if (!(value instanceof String)) {
            throw new NotStringException();
        } else {
            return ((String)value).trim();
        }
    }

    @NotNull
    static GenCodeProp getGenCodeProp(int i, JTable propTable) {
        GenCodeProp prop = new GenCodeProp();
        Object value = propTable.getValueAt(i, 0);
        prop.setFieldName(formatString(value));
        Object column = propTable.getValueAt(i, 1);
        prop.setColumnName(formatString(column));
        Object type = propTable.getValueAt(i, 2);
        prop.setFiledType(formatString(type));
        prop.setJdbcType(JdbcTypeUtils.extractFromDbType(prop.getFiledType()));
        Object length = propTable.getValueAt(i, 3);
        prop.setSize(formatString(length));
        Object unique = propTable.getValueAt(i, 4);
        prop.setUnique(formatBoolean(unique));
        Object primary = propTable.getValueAt(i, 5);
        prop.setPrimaryKey(formatBoolean(primary));
        Object canbenull = propTable.getValueAt(i, 6);
        prop.setCanBeNull(formatBoolean(canbenull));
        Object hasDefualtValue = propTable.getValueAt(i, 7);
        prop.setHasDefaultValue(formatBoolean(hasDefualtValue));
        Object isIndex = propTable.getValueAt(i, 9);
        prop.setIndex(formatBoolean(isIndex));
        Object defaultValue = propTable.getValueAt(i, 8);
        prop.setDefaultValue(formatString(defaultValue));
        Object commentVlaue = propTable.getValueAt(i, 10);
        prop.setComment(formatString(commentVlaue));
        if (prop == null) {
            $$$reportNull$$$0(0);
        }

        return prop;
    }

    public Dimension getPreferredScrollableViewportSize() {
        int headerHeight = this.getTableHeader().getPreferredSize().height;
        int height = headerHeight + 10 * this.getRowHeight();
        int width = this.getPreferredSize().width;
        return new Dimension(width, height);
    }

    public boolean isCellEditable(int row, int column) {
        return column != 0;
    }

    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
        if (column == 2) {
            List<TypeProps> typePropss = (List)this.myFieldTypeMap.get(this.getValueAt(row, 0));
            List<TypeProps> collect = (List)typePropss.stream().filter((typeProps) -> {
                return typeProps.getDefaultType().equals((String)aValue);
            }).collect(Collectors.toList());
            if (collect != null && collect.size() != 0) {
                TypeProps props = (TypeProps)collect.get(0);
                super.setValueAt(props.getSize(), row, 3);
                super.setValueAt(props.getDefaultValue(), row, 8);
            } else {
                super.setValueAt((Object)null, row, 3);
                super.setValueAt((Object)null, row, 8);
            }
        }

        if (column == 5 && aValue instanceof Boolean) {
            Boolean s = (Boolean)aValue;
            if (s) {
                for(int i = 0; i < this.getRowCount(); ++i) {
                    if (i != row) {
                        super.setValueAt(false, i, column);
                    }
                }

                super.setValueAt(false, row, 7);
                super.setValueAt(false, row, 9);
            } else {
                super.setValueAt(true, row, 7);
            }
        }

    }
}
