//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parser;

import java.util.List;

public class BaseParser {
    protected String methodName;
    protected String[] props;
    protected String[] lowerProps;
    protected static String[] linkOp = new String[]{"and", "or"};
    protected static String[] compareOp = new String[]{"between", "greaterthan", "greaterthanorequalto", "lessthanorequalto", "lessthan", "betweenorequalto", "isnotnull", "isnull", "notnull", "notlike", "like", "notin", "not", "in", "startingwith", "endingwith", "before", "after", "containing"};
    protected static String[] functionOp = new String[]{"max", "min", "avg", "sum"};
    protected static String[] order = new String[]{"asc", "desc"};
    protected static String[] updatePrefixs = new String[]{"inc", "dec"};

    public BaseParser(String methodName, List<String> props) {
        this.methodName = methodName;
        this.props = new String[props.size()];
        this.lowerProps = new String[props.size()];

        for(int i = 0; i < props.size(); ++i) {
            this.props[i] = (String)props.get(i);
            this.lowerProps[i] = ((String)props.get(i)).toLowerCase();
        }

    }
}
