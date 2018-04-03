//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.genmethodxml;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class GenMethodResult {
    private PsiElement cursorElement;
    private boolean hasCursor;
    private PsiFile cursorFile;

    public GenMethodResult() {
    }

    public boolean isHasCursor() {
        return this.hasCursor;
    }

    public void setHasCursor(boolean hasCursor) {
        this.hasCursor = hasCursor;
    }

    public PsiFile getCursorFile() {
        return this.cursorFile;
    }

    public void setCursorFile(PsiFile cursorFile) {
        this.cursorFile = cursorFile;
    }

    public PsiElement getCursorElement() {
        return this.cursorElement;
    }

    public void setCursorElement(PsiElement cursorElement) {
        this.cursorElement = cursorElement;
    }
}
