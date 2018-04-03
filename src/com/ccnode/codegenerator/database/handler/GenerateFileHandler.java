package com.ccnode.codegenerator.database.handler;

import com.ccnode.codegenerator.database.ClassValidateResult;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GenerateFileHandler extends JTableRecommendHandler {
    @NotNull
    String generateSql(List<GenCodeProp> var1, GenCodeProp var2, String var3, List<List<String>> var4, List<List<String>> var5);

    @NotNull
    ClassValidateResult validateCurrentClass(PsiClass var1);
}

