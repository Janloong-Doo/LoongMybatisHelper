//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view;

import com.ccnode.codegenerator.constants.MyBatisXmlConstants;
import com.ccnode.codegenerator.showxmlsql.ShowSqlDialog;
import com.ccnode.codegenerator.util.IconUtils;
import com.ccnode.codegenerator.util.MyPsiXmlUtils;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class MybatiXmlShowSqlAction extends AnAction {
    public MybatiXmlShowSqlAction() {
        super((String)null, (String)null, IconUtils.useSmall());
    }

    public void actionPerformed(AnActionEvent e) {
        XmlTag theTag = this.extractXmlTag(e);
        if (theTag != null) {
            PsiMethod methodOfXmlTag = MyPsiXmlUtils.findMethodOfXmlTag(theTag);
            if (methodOfXmlTag == null) {
                Messages.showErrorDialog(e.getProject(), "please provide method for current xml", "can't find method in class of current xml");
            } else {
                Map<String, String> paramMap = PsiClassUtil.extractMybatisParamForXmlSql(methodOfXmlTag);
                ShowSqlDialog dialog = new ShowSqlDialog(e.getProject(), paramMap, theTag);
                boolean b = dialog.showAndGet();
                if (!b) {
                    ;
                }
            }
        }
    }

    public void update(AnActionEvent e) {
        if (this.extractXmlTag(e) == null) {
            e.getPresentation().setVisible(false);
        }
    }

    @Nullable
    private XmlTag extractXmlTag(AnActionEvent e) {
        PsiFile data = (PsiFile)e.getData(PlatformDataKeys.PSI_FILE);
        if (data != null && data.isWritable() && data instanceof XmlFile) {
            XmlFile theFile = (XmlFile)data;
            XmlTag rootTag = theFile.getRootTag();
            if (rootTag != null && "mapper".equals(rootTag.getName())) {
                XmlTag[] subTags = rootTag.getSubTags();
                Caret data2 = (Caret)e.getData(CommonDataKeys.CARET);
                int selectionStart = data2.getSelectionStart();
                XmlTag[] var8 = subTags;
                int var9 = subTags.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    XmlTag subTag = var8[var10];
                    if (subTag.getTextRange().getStartOffset() <= selectionStart && subTag.getTextRange().getEndOffset() >= selectionStart && MyBatisXmlConstants.mapperMethodSet.contains(subTag.getName())) {
                        return subTag;
                    }
                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
