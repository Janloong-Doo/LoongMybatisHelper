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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class MyComboBoxRender implements TableCellRenderer {
    private Map<Integer, JComboBox> jComboBoxMap = new HashMap();
    private Map<String, List<TypeProps>> fieldTypeMap;

    public MyComboBoxRender(Map<String, List<TypeProps>> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComboBox jComboBox;
        if (this.jComboBoxMap.get(row) == null) {
            jComboBox = new JComboBox();
            Object fieldName = table.getValueAt(row, 0);
            List<TypeProps> typePropsList = (List)this.fieldTypeMap.get(fieldName);
            Iterator var10 = typePropsList.iterator();

            while(var10.hasNext()) {
                TypeProps getType = (TypeProps)var10.next();
                jComboBox.addItem(getType.getDefaultType());
            }

            this.jComboBoxMap.put(row, jComboBox);
        }

        jComboBox = (JComboBox)this.jComboBoxMap.get(row);
        if (value != null) {
            jComboBox.setSelectedItem(value);
        }

        return jComboBox;
    }
}
