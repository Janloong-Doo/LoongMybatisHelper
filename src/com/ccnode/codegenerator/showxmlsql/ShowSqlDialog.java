//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.showxmlsql;

import com.ccnode.codegenerator.pojo.TextFieldAndCheckBox;
import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.DialogWrapper.DialogWrapperAction;
import com.intellij.psi.xml.XmlTag;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShowSqlDialog extends DialogWrapper {
    private Map<String, String> myParamMap;
    private Map<String, TextFieldAndCheckBox> textFieldMap = Maps.newHashMap();
    private static Map<String, String> fullTypeDefaultValues = new HashMap<String, String>() {
        {
            this.put("java.lang.Integer", "0");
            this.put("java.lang.String", "'hehe'");
            this.put("java.math.BigDecimal", "100");
            this.put("java.lang.Long", "1222113");
            this.put("java.lang.Byte", "0");
            this.put("java.lang.Short", "2334");
            this.put("java.lang.Boolean", "0");
        }
    };
    private static Map<String, String> userChooseValues = Maps.newHashMap();
    private XmlTag myXmlTag;

    public ShowSqlDialog(Project project, Map<String, String> paramMap, XmlTag tag) {
        super(project, true);
        this.myParamMap = paramMap;
        this.myXmlTag = tag;
        this.setTitle("show sql of the current tag");
        this.init();
    }

    @Nullable
    protected JComponent createCenterPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        GridBagConstraints bag = new GridBagConstraints();
        bag.gridx = 0;
        bag.gridy = 0;
        bag.fill = 2;

        for(Iterator var3 = this.myParamMap.keySet().iterator(); var3.hasNext(); ++bag.gridy) {
            String oneParam = (String)var3.next();
            jPanel.add(new JLabel(oneParam), bag);
            bag.gridx = 1;
            JTextField comp = new JTextField(getDefaultValue((String)this.myParamMap.get(oneParam)));
            comp.setColumns(20);
            jPanel.add(comp, bag);
            bag.gridx = 2;
            JCheckBox jCheckBox = new JCheckBox("null value", false);
            jPanel.add(jCheckBox, bag);
            TextFieldAndCheckBox textFieldAndCheckBox = new TextFieldAndCheckBox();
            textFieldAndCheckBox.setJTextField(comp);
            textFieldAndCheckBox.setJCheckBox(jCheckBox);
            this.textFieldMap.put(oneParam, textFieldAndCheckBox);
            bag.gridx = 0;
        }

        return jPanel;
    }

    private static String getDefaultValue(String fullType) {
        String s = (String)fullTypeDefaultValues.get(fullType);
        return s != null ? s : "";
    }

    @NotNull
    protected Action[] createActions() {
        Action copySqlAction = new ShowSqlDialog.CopySqlAction("copy sql");
        Action[] actions = super.createActions();
        Action[] allAction = new Action[actions.length + 1];
        allAction[0] = copySqlAction;

        for(int i = 1; i < allAction.length; ++i) {
            allAction[i] = actions[i - 1];
        }

        if (allAction == null) {
            $$$reportNull$$$0(0);
        }

        return allAction;
    }

    private XmlContext extractContextFromXmlTag(XmlTag myXmlTag) {
        XmlContext xmlContext = new XmlContext();
        Map<String, String> sqlIdMaps = Maps.newHashMap();
        XmlTag parentTag = myXmlTag.getParentTag();
        XmlTag[] subTags = parentTag.getSubTags();
        XmlTag[] var6 = subTags;
        int var7 = subTags.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            XmlTag subTag = var6[var8];
            if (subTag.getName().equals("sql")) {
                sqlIdMaps.put(subTag.getAttributeValue("id"), subTag.getValue().getText());
            }
        }

        xmlContext.setSqlIdMap(sqlIdMaps);
        return xmlContext;
    }

    protected void doOKAction() {
        this.extractValues();
        super.doOKAction();
    }

    private void extractValues() {
        Iterator var1 = this.textFieldMap.keySet().iterator();

        while(var1.hasNext()) {
            String key = (String)var1.next();
            TextFieldAndCheckBox textFieldAndCheckBox = (TextFieldAndCheckBox)this.textFieldMap.get(key);
            JCheckBox jCheckBox = textFieldAndCheckBox.getJCheckBox();
            if (jCheckBox.isSelected()) {
                userChooseValues.put(key, (Object)null);
            } else {
                userChooseValues.put(key, textFieldAndCheckBox.getJTextField().getText());
            }
        }

    }

    public static Map<String, String> getUserChooseValues() {
        return userChooseValues;
    }

    public static void setUserChooseValues(Map<String, String> userChooseValues) {
        userChooseValues = userChooseValues;
    }

    protected class CopySqlAction extends DialogWrapperAction {
        protected void doAction(ActionEvent e) {
            this.this$0.extractValues();
            XmlContext context = this.this$0.extractContextFromXmlTag(this.this$0.myXmlTag);
            XmlParseResult parseResult = XmlParser.parseXmlWithContext(this.this$0.myXmlTag.getValue().getText(), this.this$0.myParamMap, context);
            if (parseResult.getIsValid()) {
                System.out.println(parseResult.getSql());
            } else {
                System.out.println(parseResult.getErrorText());
            }

        }

        protected CopySqlAction(@NotNull String name) {
            if (name == null) {
                $$$reportNull$$$0(0);
            }

            this.this$0 = ShowSqlDialog.this;
            super(ShowSqlDialog.this, name);
        }
    }
}
