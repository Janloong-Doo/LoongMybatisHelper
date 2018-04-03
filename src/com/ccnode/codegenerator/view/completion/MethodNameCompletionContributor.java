//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.view.completion;

import com.ccnode.codegenerator.methodnameparser.buidler.ParsedMethodEnum;
import com.ccnode.codegenerator.pojo.DomainClassInfo;
import com.ccnode.codegenerator.util.MethodNameCheckReuslt;
import com.ccnode.codegenerator.util.MethodNameUtil;
import com.ccnode.codegenerator.util.PsiClassUtil;
import com.ccnode.codegenerator.util.PsiElementUtil;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class MethodNameCompletionContributor extends CompletionContributor {
    private static List<String> textEndList = new ArrayList<String>() {
        {
            this.add("find");
            this.add("update");
            this.add("and");
            this.add("by");
            this.add("count");
        }
    };

    public MethodNameCompletionContributor() {
    }

    public void beforeCompletion(@NotNull CompletionInitializationContext context) {
        if (context == null) {
            $$$reportNull$$$0(0);
        }

    }

    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters == null) {
            $$$reportNull$$$0(1);
        }

        if (result == null) {
            $$$reportNull$$$0(2);
        }

        if (parameters.getCompletionType() == CompletionType.BASIC) {
            PsiElement element = parameters.getPosition();
            PsiElement originalPosition = parameters.getOriginalPosition();
            PsiFile topLevelFile = InjectedLanguageUtil.getTopLevelFile(element);
            if (topLevelFile != null && topLevelFile instanceof PsiJavaFile) {
                PsiClass containingClass = PsiElementUtil.getContainingClass(originalPosition);
                if (containingClass != null && containingClass.isInterface()) {
                    String text = originalPosition.getText();
                    MethodNameCheckReuslt methodNameResult = MethodNameUtil.checkValidTextStarter(text);
                    if (methodNameResult.isValid()) {
                        ParsedMethodEnum parsedMethodEnum = methodNameResult.getParsedMethodEnum();
                        DomainClassInfo domainClassInfo = PsiClassUtil.getDomainClassInfo(containingClass);
                        if (domainClassInfo != null) {
                            PsiClass pojoClass = domainClassInfo.getDomainClass();
                            if (pojoClass != null) {
                                List<String> strings = PsiClassUtil.extractProps(pojoClass);
                                List<String> formatProps = new ArrayList();
                                Iterator var14 = strings.iterator();

                                while(var14.hasNext()) {
                                    String s = (String)var14.next();
                                    formatProps.add(s.substring(0, 1).toUpperCase() + s.substring(1));
                                }

                                String lower = text.toLowerCase();
                                boolean defaultrecommed = false;
                                Iterator var16 = textEndList.iterator();

                                while(true) {
                                    do {
                                        do {
                                            Iterator var18;
                                            String after;
                                            LookupElementBuilder builder;
                                            if (!var16.hasNext()) {
                                                if (defaultrecommed) {
                                                    return;
                                                }

                                                List<String> afterlower = new ArrayList();
                                                if (lower.indexOf("by") != -1) {
                                                    if (lower.endsWith("g")) {
                                                        afterlower.add("reaterThan");
                                                        afterlower.add("reaterThanOrEqualTo");
                                                    }

                                                    if (lower.endsWith("l")) {
                                                        afterlower.add("essThan");
                                                        afterlower.add("essThanOrEqualTo");
                                                        afterlower.add("ike");
                                                    }

                                                    if (lower.endsWith("b")) {
                                                        afterlower.add("etween");
                                                        afterlower.add("etweenOrEqualTo");
                                                        afterlower.add("efore");
                                                    }

                                                    if (lower.endsWith("i")) {
                                                        afterlower.add("n");
                                                        afterlower.add("sNotNull");
                                                    }

                                                    if (lower.endsWith("n")) {
                                                        afterlower.add("otIn");
                                                        afterlower.add("otLike");
                                                        afterlower.add("ot");
                                                        afterlower.add("otNull");
                                                    }

                                                    if (lower.endsWith("o")) {
                                                        afterlower.add("r");
                                                    }

                                                    if (lower.endsWith("a")) {
                                                        afterlower.add("fter");
                                                    }

                                                    if (lower.endsWith("s")) {
                                                        afterlower.add("tartingwith");
                                                    }

                                                    if (lower.endsWith("e")) {
                                                        afterlower.add("ndingwith");
                                                    }

                                                    if (lower.endsWith("c")) {
                                                        afterlower.add("ontaining");
                                                    }
                                                }

                                                if (parsedMethodEnum.equals(ParsedMethodEnum.FIND)) {
                                                    if (lower.endsWith("w")) {
                                                        afterlower.add("ithPage");
                                                    }

                                                    if (lower.endsWith("m")) {
                                                        afterlower.add("ax");
                                                        afterlower.add("in");
                                                    }

                                                    if (lower.endsWith("a")) {
                                                        afterlower.add("vg");
                                                        afterlower.add("nd");
                                                    }

                                                    if (lower.endsWith("s")) {
                                                        afterlower.add("um");
                                                    }

                                                    if (lower.indexOf("orderby") != -1 && lower.endsWith("d")) {
                                                        afterlower.add("esc");
                                                    }
                                                }

                                                if (parsedMethodEnum.equals(ParsedMethodEnum.FIND) || parsedMethodEnum.equals(ParsedMethodEnum.COUNT)) {
                                                    if (lower.endsWith("o")) {
                                                        afterlower.add("rderBy");
                                                    }

                                                    if ("findd".equals(lower) || "countd".equals(lower)) {
                                                        afterlower.add("istinct");
                                                    }
                                                }

                                                if (parsedMethodEnum.equals(ParsedMethodEnum.UPDATE)) {
                                                    if (lower.endsWith("i")) {
                                                        afterlower.add("nc");
                                                    }

                                                    if (lower.endsWith("d")) {
                                                        afterlower.add("ec");
                                                    }
                                                }

                                                char u = Character.toLowerCase(text.charAt(text.length() - 1));
                                                var18 = strings.iterator();

                                                while(var18.hasNext()) {
                                                    after = (String)var18.next();
                                                    if (Character.toLowerCase(after.charAt(0)) == u && after.length() > 1) {
                                                        afterlower.add(after.substring(1));
                                                    }
                                                }

                                                if (afterlower.size() > 0) {
                                                    var18 = afterlower.iterator();

                                                    while(var18.hasNext()) {
                                                        after = (String)var18.next();
                                                        builder = LookupElementBuilder.create(text + after);
                                                        result.addElement(builder);
                                                    }
                                                }

                                                return;
                                            }

                                            String end = (String)var16.next();
                                            if (lower.endsWith(end)) {
                                                defaultrecommed = true;
                                                var18 = formatProps.iterator();

                                                while(var18.hasNext()) {
                                                    after = (String)var18.next();
                                                    builder = LookupElementBuilder.create(text + after);
                                                    result.addElement(builder);
                                                }
                                            }

                                            if ("find".equals(lower)) {
                                                result.addElement(LookupElementBuilder.create(text + "First"));
                                                result.addElement(LookupElementBuilder.create(text + "One"));
                                            }
                                        } while(!parsedMethodEnum.equals(ParsedMethodEnum.UPDATE));
                                    } while(!"update".equals(lower) && !lower.endsWith("and"));

                                    result.addElement(LookupElementBuilder.create(text + "Inc"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
