//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.sqlparse;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import java.util.List;

public class MethodRecommendCache {
    private static String[] sqlMethods = new String[]{"ABS", "COUNT", "DISTINCT", "AVG", "COUNT", "DISTINCT", "FIRST", "FORMAT", "LAST", "LCASE", "LEN", "MAX", "MIN", "MID", "NOW", "ROUND", "SUM", "TOP", "UCASE"};

    public MethodRecommendCache() {
    }

    public static List<LookupElement> getRecommends(String currentWordStart) {
        List<LookupElement> methodRecommends = Lists.newArrayList();
        String[] var2 = sqlMethods;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            final String sqlMethod = var2[var4];
            methodRecommends.add(LookupElementBuilder.create(currentWordStart + sqlMethod + "()").withRenderer(new LookupElementRenderer<LookupElement>() {
                public void renderElement(LookupElement element, LookupElementPresentation presentation) {
                    presentation.setItemText(sqlMethod);
                    presentation.setTypeText("function");
                }
            }).withInsertHandler(new InsertHandler<LookupElement>() {
                public void handleInsert(InsertionContext context, LookupElement item) {
                    context.getEditor().getCaretModel().moveToOffset(context.getTailOffset() - 1);
                }
            }));
        }

        return methodRecommends;
    }
}
