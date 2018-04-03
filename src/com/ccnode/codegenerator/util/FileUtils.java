//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.ccnode.codegenerator.dialog.InsertFileProp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtils {
    public FileUtils() {
    }

    public static void writeFiles(InsertFileProp prop, List<String> retList) {
        try {
            File file = new File(prop.getFullPath());
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            Files.write(Paths.get(prop.getFullPath()), retList, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException var3) {
            throw new RuntimeException("can't write file " + prop.getName() + " to path " + prop.getFullPath(), var3);
        }
    }

    public static void writeFiles(String fullPath, List<String> retList, String fileName) {
        try {
            File file = new File(fullPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            Files.write(Paths.get(fullPath), retList, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException var4) {
            throw new RuntimeException("can't write file " + fileName + " to path " + fullPath, var4);
        }
    }
}
