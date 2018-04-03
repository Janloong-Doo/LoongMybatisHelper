//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parser;

import com.ccnode.codegenerator.dialog.ParseTypeEnum;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFind;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFindDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.find.ParsedFindError;
import java.util.ArrayList;
import java.util.List;

public class FindParser extends BaseParser {
    private List<ParsedFind> finds = new ArrayList();
    private List<ParsedFindError> errors = new ArrayList();

    public FindParser(String methodName, List<String> props) {
        super(methodName, props);
    }

    public ParsedFindDto parse() {
        int state = 0;
        int len = "find".length();
        ParsedFind parsedFind = new ParsedFind();
        this.parseMethods(state, this.methodName, len, parsedFind);
        ParsedFindDto dto = new ParsedFindDto();
        dto.setParsedFinds(this.finds);
        dto.setParsedFindErrors(this.errors);
        return dto;
    }

    private void parseMethods(int state, String methodName, int len, ParsedFind parsedFind) {
        if (methodName.length() == len) {
            if (this.isValidEndState(state)) {
                this.finds.add(parsedFind);
            } else {
                ParsedFindError error = new ParsedFindError();
                error.setParsedFind(parsedFind);
                error.setLastState(state);
                error.setRemaining("");
                this.errors.add(error);
            }

        } else {
            String remaining;
            boolean newParseFind;
            remaining = methodName.substring(len);
            newParseFind = false;
            int limitCount;
            ParsedFind newFind;
            int var9;
            String function;
            ParsedFind newFind;
            ParsedFind newFind;
            String[] var14;
            int i;
            label288:
            switch(state) {
                case 0:
                    if (remaining.startsWith("distinct")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.setDistinct(true);
                        newFind.addParsePart(ParseTypeEnum.DISTINCT, "distinct");
                        this.parseMethods(1, remaining, "distinct".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("first")) {
                        if (remaining.length() == "first".length()) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.setLimit(1);
                            newFind.setReturnList(false);
                            newFind.addParsePart(ParseTypeEnum.FIRST, "first");
                            this.parseMethods(2, remaining, "first".length(), newFind);
                            newParseFind = true;
                        } else {
                            limitCount = 0;

                            for(i = "first".length(); i < remaining.length(); ++i) {
                                char c = remaining.charAt(i);
                                if (c < '0' || c > '9') {
                                    break;
                                }

                                limitCount = limitCount * 10 + (c - 48);
                            }

                            if (limitCount == 0) {
                                limitCount = 1;
                            }

                            ParsedFind newFind = this.createParseFind(parsedFind);
                            newFind.setLimit(limitCount);
                            newFind.setReturnList(true);
                            newFind.addParsePart(ParseTypeEnum.FIRST, remaining.substring(0, i));
                            this.parseMethods(2, remaining, i, newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("one")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.setReturnList(false);
                        newFind.addParsePart(ParseTypeEnum.ONE, "one");
                        this.parseMethods(2, remaining, "one".length(), newFind);
                        newParseFind = true;
                    }

                    for(limitCount = 0; limitCount < this.props.length; ++limitCount) {
                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFetchProps(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(3, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("orderby")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.ORDERBY, "orderby");
                        this.parseMethods(4, remaining, "orderby".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("by")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(8, remaining, "by".length(), newFind);
                        newParseFind = true;
                    }

                    var14 = functionOp;
                    i = var14.length;

                    for(var9 = 0; var9 < i; ++var9) {
                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFunction(function);
                            newFind.addParsePart(ParseTypeEnum.FUNCTION, function);
                            this.parseMethods(11, remaining, function.length(), newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 1:
                    limitCount = 0;

                    while(true) {
                        if (limitCount >= this.props.length) {
                            break label288;
                        }

                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFetchProps(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(3, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }

                        ++limitCount;
                    }
                case 2:
                    for(limitCount = 0; limitCount < this.props.length; ++limitCount) {
                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFetchProps(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(3, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("orderby")) {
                        newFind = this.createParseFind(parsedFind);
                        this.parseMethods(4, remaining, "orderby".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("by")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(8, remaining, "by".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 3:
                    if (remaining.startsWith("and")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.AND, "and");
                        this.parseMethods(7, remaining, "and".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("by")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(8, remaining, "by".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("orderby")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.ORDERBY, "orderby");
                        this.parseMethods(4, remaining, "orderby".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 4:
                    limitCount = 0;

                    while(true) {
                        if (limitCount >= this.props.length) {
                            break label288;
                        }

                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addOrderByProp(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(5, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }

                        ++limitCount;
                    }
                case 5:
                    var14 = order;
                    i = var14.length;

                    for(var9 = 0; var9 < i; ++var9) {
                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addOrderByPropOrder(function);
                            newFind.addParsePart(ParseTypeEnum.ORDER, function);
                            this.parseMethods(6, remaining, function.length(), newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("and")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.AND, "and");
                        this.parseMethods(4, remaining, "and".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 6:
                    if (remaining.startsWith("and")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.AND, "and");
                        this.parseMethods(4, remaining, "and".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 7:
                    for(limitCount = 0; limitCount < this.props.length; ++limitCount) {
                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFetchProps(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(3, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }
                    }

                    var14 = functionOp;
                    i = var14.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= i) {
                            break label288;
                        }

                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.setReturnList(false);
                            newFind.addFunction(function);
                            newFind.addParsePart(ParseTypeEnum.FUNCTION, function);
                            this.parseMethods(11, remaining, function.length(), newFind);
                            newParseFind = true;
                        }

                        ++var9;
                    }
                case 8:
                    limitCount = 0;

                    while(true) {
                        if (limitCount >= this.props.length) {
                            break label288;
                        }

                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addQueryProp(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(9, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }

                        ++limitCount;
                    }
                case 9:
                    var14 = compareOp;
                    i = var14.length;

                    for(var9 = 0; var9 < i; ++var9) {
                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addParsePart(ParseTypeEnum.COMPARATOR, function);
                            newFind.addQueryOperator(function);
                            this.parseMethods(10, remaining, function.length(), newFind);
                            newParseFind = true;
                        }
                    }

                    var14 = linkOp;
                    i = var14.length;

                    for(var9 = 0; var9 < i; ++var9) {
                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addConnector(function);
                            newFind.addParsePart(ParseTypeEnum.LINKOP, function);
                            this.parseMethods(8, remaining, function.length(), newFind);
                        }
                    }

                    if (remaining.startsWith("orderby")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.ORDERBY, "orderby");
                        this.parseMethods(4, remaining, "orderby".length(), newFind);
                        newParseFind = true;
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 10:
                    if (remaining.startsWith("orderby")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.ORDERBY, "orderby");
                        this.parseMethods(4, remaining, "orderby".length(), newFind);
                        newParseFind = true;
                    }

                    var14 = linkOp;
                    i = var14.length;

                    for(var9 = 0; var9 < i; ++var9) {
                        function = var14[var9];
                        if (remaining.startsWith(function)) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addConnector(function);
                            newFind.addParsePart(ParseTypeEnum.LINKOP, function);
                            this.parseMethods(8, remaining, function.length(), newFind);
                            newParseFind = true;
                        }
                    }

                    if (remaining.startsWith("withpage")) {
                        newFind = this.createParseFind(parsedFind);
                        newFind.addParsePart(ParseTypeEnum.PAGE_QUERY, "withpage");
                        newFind.setWithPage(true);
                        this.parseMethods(12, remaining, "withpage".length(), newFind);
                        newParseFind = true;
                    }
                    break;
                case 11:
                    for(limitCount = 0; limitCount < this.props.length; ++limitCount) {
                        if (remaining.startsWith(this.lowerProps[limitCount])) {
                            newFind = this.createParseFind(parsedFind);
                            newFind.addFunctionProp(this.props[limitCount]);
                            newFind.addParsePart(ParseTypeEnum.PROPERTY, this.props[limitCount]);
                            this.parseMethods(3, remaining, this.props[limitCount].length(), newFind);
                            newParseFind = true;
                        }
                    }
            }

            if (!newParseFind) {
                ParsedFindError error = new ParsedFindError();
                error.setParsedFind(parsedFind);
                error.setRemaining(remaining);
                error.setLastState(state);
                this.errors.add(error);
            }

        }
    }

    private ParsedFind createParseFind(ParsedFind parsedFind) {
        return parsedFind.clone();
    }

    private boolean isValidEndState(int state) {
        return state == 0 || state == 2 || state == 3 || state == 5 || state == 6 || state == 9 || state == 10 || state == 12;
    }
}
