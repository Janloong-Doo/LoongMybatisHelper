//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.util;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class GenCodeUtil {
    public static final String ONE_RETRACT = "\t";
    public static final String TWO_RETRACT = "\t\t";
    public static final String THREE_RETRACT = "\t\t\t";
    public static final String FOUR_RETRACT = "\t\t\t\t";
    public static String MYSQL_TYPE = "";
    public static String PACKAGE_LINE = "";

    public GenCodeUtil() {
    }

    public static List<String> grapOld(@NotNull List<String> oldList, @NotNull String startKeyWord, @NotNull String endKeyWord) {
        if (oldList == null) {
            $$$reportNull$$$0(0);
        }

        if (startKeyWord == null) {
            $$$reportNull$$$0(1);
        }

        if (endKeyWord == null) {
            $$$reportNull$$$0(2);
        }

        int startIndex = -1;
        int endIndex = -1;
        int i = 0;

        for(Iterator var6 = oldList.iterator(); var6.hasNext(); ++i) {
            String line = (String)var6.next();
            if (sqlContain(line, startKeyWord)) {
                startIndex = i;
            }

            if (sqlContain(line, endKeyWord)) {
                endIndex = i;
            }
        }

        if (startIndex != -1 && endIndex != -1) {
            return oldList.subList(startIndex, endIndex + 1);
        } else {
            return Lists.newArrayList();
        }
    }

    public static String firstCharUpper(String prop) {
        return prop.substring(0, 1).toUpperCase() + prop.substring(1);
    }

    public static String pathToPackage(String path) {
        path = path.replace("/", ".");
        path = path.replace("\\", ".");
        if (path.startsWith("src.main.java.")) {
            path = path.replace("src.main.java.", "");
        }

        if (path.startsWith("src.main.")) {
            path = path.replace("src.main.", "");
        }

        if (path.startsWith("src.")) {
            path = path.replace("src.", "");
        }

        if (path.startsWith(".")) {
            path = path.substring(1, path.length());
        }

        return path;
    }

    public static boolean sqlContain(String sequence, @NotNull String word) {
        if (word == null) {
            $$$reportNull$$$0(3);
        }

        return StringUtils.isBlank(sequence) ? false : StringUtils.containsIgnoreCase(StringUtils.deleteWhitespace(sequence), StringUtils.deleteWhitespace(word));
    }

    public static String getUnderScoreWithComma(String value) {
        if (value == null) {
            return "";
        } else {
            String to = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
            return "`" + to + "`";
        }
    }

    public static String getUnderScore(String value) {
        return value == null ? "" : CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
    }

    public static String getLowerCamel(String value) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, value);
    }

    public static String getUpperStart(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static String getCamelFromUnderScore(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
    }

    public static String getUpperCamelFromUnderScore(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
    }

    public static void main(String[] args) {
    }
}
