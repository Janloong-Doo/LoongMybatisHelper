package com.ccnode.codegenerator.database.handler;

import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface JTableRecommendHandler {
    @NotNull
    List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField var1);
}
