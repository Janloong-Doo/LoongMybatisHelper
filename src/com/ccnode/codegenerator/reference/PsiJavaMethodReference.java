//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiJavaMethodReference implements PsiReference {
    private PsiIdentifier myIdentifier;
    private XmlAttributeValue psiElement;

    public PsiJavaMethodReference(XmlAttributeValue element, PsiIdentifier identifier) {
        this.psiElement = element;
        this.myIdentifier = identifier;
    }

    public PsiElement getElement() {
        return this.psiElement;
    }

    public TextRange getRangeInElement() {
        String text = this.psiElement.getText();
        return text.startsWith("\"") && text.endsWith("\"") && text.length() > 2 ? new TextRange(1, this.psiElement.getTextLength() - 1) : new TextRange(0, this.psiElement.getTextLength());
    }

    @Nullable
    public PsiElement resolve() {
        PsiElement parent = this.myIdentifier.getParent();
        return (PsiElement)(parent != null && parent instanceof PsiMethod ? parent : this.myIdentifier);
    }

    @NotNull
    public String getCanonicalText() {
        String var10000 = this.myIdentifier.getText();
        if (var10000 == null) {
            $$$reportNull$$$0(0);
        }

        return var10000;
    }

    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        PsiElement parent = this.psiElement.getParent();
        if (!(parent instanceof XmlAttribute)) {
            return this.psiElement;
        } else {
            XmlAttribute attribute = (XmlAttribute)parent;
            attribute.setValue(newElementName);
            return attribute.getValueElement();
        }
    }

    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        if (element == null) {
            $$$reportNull$$$0(1);
        }

        return null;
    }

    public boolean isReferenceTo(PsiElement element) {
        return element == this.resolve();
    }

    @NotNull
    public Object[] getVariants() {
        Object[] var10000 = new Object[0];
        if (var10000 == null) {
            $$$reportNull$$$0(2);
        }

        return var10000;
    }

    public boolean isSoft() {
        return false;
    }
}
