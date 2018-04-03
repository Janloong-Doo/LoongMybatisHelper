//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.rename;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenameResultMapSqlProcessor extends RenamePsiElementProcessor {
    public RenameResultMapSqlProcessor() {
    }

    public boolean canProcessElement(@NotNull PsiElement element) {
        if (element == null) {
            $$$reportNull$$$0(0);
        }

        return element instanceof XmlAttributeValue;
    }

    public void renameElement(PsiElement element, String newName, UsageInfo[] usages, @Nullable RefactoringElementListener listener) throws IncorrectOperationException {
        System.out.println("hello");
    }
}
