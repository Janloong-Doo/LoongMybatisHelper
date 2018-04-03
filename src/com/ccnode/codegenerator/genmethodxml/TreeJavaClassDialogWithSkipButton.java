//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genmethodxml;

import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TreeJavaClassDialogWithSkipButton extends TreeJavaClassChooserDialog {
    public TreeJavaClassDialogWithSkipButton(String title, @NotNull Project project, GlobalSearchScope scope, ClassFilter classFilter, @Nullable PsiClass initialClass) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }

        super(title, project, scope, classFilter, (PsiClass)null, initialClass, true);
        this.setCancelButtonText("skip");
    }
}
