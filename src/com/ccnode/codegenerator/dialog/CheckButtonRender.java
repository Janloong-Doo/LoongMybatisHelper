//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.dialog;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class CheckButtonRender extends JCheckBox implements TableCellRenderer {
    CheckButtonRender() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setSelected(value != null && (Boolean)value);
        return this;
    }
}
