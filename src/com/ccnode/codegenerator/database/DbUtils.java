package com.ccnode.codegenerator.database;


import com.ccnode.codegenerator.methodnameparser.parsedresult.find.FetchProp;

import java.util.Map;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 12:02
 */
public class DbUtils {
    public DbUtils() {
    }

    public static String buildSelectFunctionVal(FetchProp prop) {
        return prop.getFetchFunction() + prop.getFetchProp();
    }

    public static String getReturnClassFromFunction(Map<String, String> fieldMap, String fetchFunction, String fetchProp) {
        byte var4 = -1;
        switch(fetchFunction.hashCode()) {
            case 96978:
                if (fetchFunction.equals("avg")) {
                    var4 = 2;
                }
                break;
            case 107876:
                if (fetchFunction.equals("max")) {
                    var4 = 0;
                }
                break;
            case 108114:
                if (fetchFunction.equals("min")) {
                    var4 = 1;
                }
                break;
            case 114251:
                if (fetchFunction.equals("sum")) {
                    var4 = 3;
                }
        }

        switch(var4) {
            case 0:
            case 1:
                return (String)fieldMap.get(fetchProp);
            case 2:
            case 3:
                return "java.math.BigDecimal";
            default:
                return null;
        }
    }
}
