//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.rits.cloning.Cloner;

public class MyCloner {
    private MyCloner() {
    }

    public static Cloner getCloner() {
        return MyCloner.HelperHolder.myCloner;
    }

    private static class HelperHolder {
        private static final Cloner myCloner = new Cloner();

        private HelperHolder() {
        }
    }
}
