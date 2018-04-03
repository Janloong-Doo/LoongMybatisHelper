//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

class MyComboBoxEditor extends DefaultCellEditor {
    private Map<Integer, String[]> itemMap = new HashMap();
    private Map<String, List<TypeProps>> fieldTypeMap;

    public MyComboBoxEditor(JComboBox comboBox, Map<String, List<TypeProps>> fieldTypeMap) {
        super(comboBox);
        this.fieldTypeMap = fieldTypeMap;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JComboBox editorComponent = (JComboBox)this.editorComponent;
        editorComponent.removeAllItems();
        if (this.itemMap.get(row) == null) {
            Object fieldName = table.getValueAt(row, 0);
            List<TypeProps> typePropsList = (List)this.fieldTypeMap.get(fieldName);
            Iterator var9 = typePropsList.iterator();

            while(var9.hasNext()) {
                TypeProps recommend = (TypeProps)var9.next();
                editorComponent.addItem(recommend.getDefaultType());
            }

            this.itemMap.put(row, typePropsList.stream().map((item) -> {
                return item.getDefaultType();
            }).toArray((size) -> {
                return new String[size];
            }));
        } else {
            String[] var12 = (String[])this.itemMap.get(row);
            int var13 = var12.length;

            for(int var14 = 0; var14 < var13; ++var14) {
                String recommend = var12[var14];
                editorComponent.addItem(recommend);
            }
        }

        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
