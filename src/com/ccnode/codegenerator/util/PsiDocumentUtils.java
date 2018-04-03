//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.pojo.GeneratedMethodDTO;
import com.google.common.collect.Lists;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackageStatement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.apache.commons.lang.StringUtils;

public class PsiDocumentUtils {
    public PsiDocumentUtils() {
    }

    public static void commitAndSaveDocument(PsiDocumentManager psiDocumentManager, Document document) {
        if (document != null) {
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(document);
            psiDocumentManager.commitDocument(document);
            FileDocumentManager.getInstance().saveDocument(document);
        }

    }

    public static void commit(PsiDocumentManager psiDocumentManager, Document document) {
        if (document != null) {
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(document);
            psiDocumentManager.commitDocument(document);
        }

    }

    public static void addImportToFile(PsiDocumentManager psiDocumentManager, PsiJavaFile containingFile, Document document, Set<String> newImportList) {
        if (newImportList.size() > 0) {
            Iterator iterator = newImportList.iterator();

            while(iterator.hasNext()) {
                String u = (String)iterator.next();
                if (u.startsWith("java.lang")) {
                    iterator.remove();
                }
            }
        }

        if (newImportList.size() > 0) {
            PsiImportStatement[] importStatements = containingFile.getImportList().getImportStatements();
            Set<String> containedSet = new HashSet();
            PsiImportStatement[] var7 = importStatements;
            int var8 = importStatements.length;

            int start;
            for(start = 0; start < var8; ++start) {
                PsiImportStatement s = var7[start];
                containedSet.add(s.getQualifiedName());
            }

            StringBuilder newImportText = new StringBuilder();
            Iterator var14 = newImportList.iterator();

            while(var14.hasNext()) {
                String newImport = (String)var14.next();
                if (!containedSet.contains(newImport)) {
                    newImportText.append("\nimport " + newImport + ";");
                }
            }

            PsiPackageStatement packageStatement = containingFile.getPackageStatement();
            start = 0;
            if (packageStatement != null) {
                start = packageStatement.getTextLength() + packageStatement.getTextOffset();
            }

            String insertText = newImportText.toString();
            if (StringUtils.isNotBlank(insertText)) {
                WriteCommandAction.runWriteCommandAction(containingFile.getProject(), () -> {
                    document.insertString(start, insertText);
                    commitAndSaveDocument(psiDocumentManager, document);
                });
            }
        }

    }

    public static void addImportToFileNoSave(PsiDocumentManager psiDocumentManager, PsiJavaFile containingFile, Document document, Set<String> newImportList) {
        if (newImportList.size() > 0) {
            Iterator iterator = newImportList.iterator();

            label52:
            while(true) {
                String u;
                do {
                    if (!iterator.hasNext()) {
                        break label52;
                    }

                    u = (String)iterator.next();
                } while(u != null && !u.startsWith("java.lang"));

                iterator.remove();
            }
        }

        if (newImportList.size() > 0) {
            PsiImportStatement[] importStatements = containingFile.getImportList().getImportStatements();
            Set<String> containedSet = new HashSet();
            PsiImportStatement[] var7 = importStatements;
            int var8 = importStatements.length;

            int start;
            for(start = 0; start < var8; ++start) {
                PsiImportStatement s = var7[start];
                containedSet.add(s.getQualifiedName());
            }

            StringBuilder newImportText = new StringBuilder();
            Iterator var14 = newImportList.iterator();

            while(var14.hasNext()) {
                String newImport = (String)var14.next();
                if (!containedSet.contains(newImport)) {
                    newImportText.append("\nimport " + newImport + ";");
                }
            }

            PsiPackageStatement packageStatement = containingFile.getPackageStatement();
            start = 0;
            if (packageStatement != null) {
                start = packageStatement.getTextLength() + packageStatement.getTextOffset();
            }

            String insertText = newImportText.toString();
            if (StringUtils.isNotBlank(insertText)) {
                WriteCommandAction.runWriteCommandAction(containingFile.getProject(), () -> {
                    document.insertString(start, insertText);
                    commit(psiDocumentManager, document);
                });
            }
        }

    }

    public static void addMethodToClass(Project project, PsiClass psiClass, final PsiDocumentManager psiDocumentManager, List<GeneratedMethodDTO> dtos, Boolean addOverrideAnnotation) {
        final Document document = psiDocumentManager.getDocument(psiClass.getContainingFile());
        final String insertString = "";
        List<String> methods = Lists.newArrayList();
        dtos.forEach((info) -> {
            methods.add(info.getMethodText());
        });
        insertString = insertString + "\n\n";

        String method;
        for(Iterator var8 = methods.iterator(); var8.hasNext(); insertString = insertString + method + "\n") {
            method = (String)var8.next();
            if (addOverrideAnnotation) {
                insertString = insertString + "\t@Override\n";
            }
        }

        Iterator var16 = dtos.iterator();

        while(var16.hasNext()) {
            GeneratedMethodDTO dto = (GeneratedMethodDTO)var16.next();
            addImportToFileNoSave(psiDocumentManager, (PsiJavaFile)psiClass.getContainingFile(), document, dto.getImports());
        }

        PsiMethod[] methods1 = psiClass.getMethods();
        Integer start = document.getText().indexOf("{");
        PsiMethod[] var11 = methods1;
        int var12 = methods1.length;

        for(int var13 = 0; var13 < var12; ++var13) {
            PsiMethod method = var11[var13];
            int endOffset = method.getTextRange().getEndOffset();
            if (endOffset > start) {
                start = endOffset;
            }
        }

        final int theStart = start;
        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            public void run() {
                document.insertString(theStart, insertString);
                PsiDocumentUtils.commitAndSaveDocument(psiDocumentManager, document);
            }
        });
    }
}
