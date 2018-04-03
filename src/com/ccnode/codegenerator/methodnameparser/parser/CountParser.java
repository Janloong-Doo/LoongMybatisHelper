//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parser;

import com.ccnode.codegenerator.dialog.ParseTypeEnum;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCount;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCountDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.count.ParsedCountError;
import java.util.ArrayList;
import java.util.List;

public class CountParser extends BaseParser {
    private List<ParsedCount> counts = new ArrayList();
    private List<ParsedCountError> errors = new ArrayList();

    public CountParser(String methodName, List<String> props) {
        super(methodName, props);
    }

    public ParsedCountDto parse() {
        int state = 0;
        int len = "count".length();
        ParsedCount count = new ParsedCount();
        this.parseMethods(state, this.methodName, len, count);
        ParsedCountDto dto = new ParsedCountDto();
        dto.setParsedCounts(this.counts);
        dto.setErrors(this.errors);
        return dto;
    }

    private void parseMethods(int state, String methodName, int len, ParsedCount count) {
        if (methodName.length() == len) {
            if (this.isValidEndState(state)) {
                this.counts.add(count);
            } else {
                ParsedCountError error = new ParsedCountError();
                error.setParsedCount(count);
                error.setLastState(state);
                error.setRemaining("");
                this.errors.add(error);
            }

        } else {
            String remaining;
            boolean newParsedCount;
            remaining = methodName.substring(len);
            newParsedCount = false;
            String[] var7;
            int var8;
            int var9;
            String comp;
            ParsedCount clone;
            int i;
            ParsedCount clone;
            ParsedCount clone;
            label112:
            switch(state) {
                case 0:
                    for(i = 0; i < this.props.length; ++i) {
                        if (remaining.startsWith(this.lowerProps[i])) {
                            clone = count.clone();
                            clone.addFetchProps(this.props[i]);
                            clone.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(1, remaining, this.props[i].length(), clone);
                            newParsedCount = true;
                        }
                    }

                    if (remaining.startsWith("distinct")) {
                        clone = count.clone();
                        clone.setDistinct(true);
                        clone.addParsePart(ParseTypeEnum.DISTINCT, "distinct");
                        this.parseMethods(2, remaining, "distinct".length(), clone);
                        newParsedCount = true;
                    }

                    if (remaining.startsWith("by")) {
                        clone = count.clone();
                        clone.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(4, remaining, "by".length(), clone);
                        newParsedCount = true;
                    }
                    break;
                case 1:
                    if (remaining.startsWith("by")) {
                        clone = count.clone();
                        clone.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(4, remaining, "by".length(), clone);
                        newParsedCount = true;
                    }
                    break;
                case 2:
                    i = 0;

                    while(true) {
                        if (i >= this.props.length) {
                            break label112;
                        }

                        if (remaining.startsWith(this.lowerProps[i])) {
                            clone = count.clone();
                            clone.addFetchProps(this.props[i]);
                            clone.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(3, remaining, this.props[i].length(), clone);
                            newParsedCount = true;
                        }

                        ++i;
                    }
                case 3:
                    if (remaining.startsWith("and")) {
                        clone = count.clone();
                        clone.addParsePart(ParseTypeEnum.AND, "and");
                        this.parseMethods(2, remaining, "and".length(), clone);
                        newParsedCount = true;
                    }

                    if (remaining.startsWith("by")) {
                        clone = count.clone();
                        clone.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(4, remaining, "by".length(), clone);
                        newParsedCount = true;
                    }
                    break;
                case 4:
                    i = 0;

                    while(true) {
                        if (i >= this.props.length) {
                            break label112;
                        }

                        if (remaining.startsWith(this.lowerProps[i])) {
                            clone = count.clone();
                            clone.addQueryProp(this.props[i]);
                            clone.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(5, remaining, this.props[i].length(), clone);
                            newParsedCount = true;
                        }

                        ++i;
                    }
                case 5:
                    var7 = linkOp;
                    var8 = var7.length;

                    for(var9 = 0; var9 < var8; ++var9) {
                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = count.clone();
                            clone.addConnector(comp);
                            clone.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(4, remaining, comp.length(), clone);
                            newParsedCount = true;
                        }
                    }

                    var7 = compareOp;
                    var8 = var7.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= var8) {
                            break label112;
                        }

                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = count.clone();
                            clone.addQueryOperator(comp);
                            clone.addParsePart(ParseTypeEnum.COMPARATOR, comp);
                            this.parseMethods(6, remaining, comp.length(), clone);
                            newParsedCount = true;
                        }

                        ++var9;
                    }
                case 6:
                    var7 = linkOp;
                    var8 = var7.length;

                    for(var9 = 0; var9 < var8; ++var9) {
                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = count.clone();
                            clone.addConnector(comp);
                            clone.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(4, remaining, comp.length(), clone);
                            newParsedCount = true;
                        }
                    }
            }

            if (!newParsedCount) {
                ParsedCountError error = new ParsedCountError();
                error.setParsedCount(count);
                error.setRemaining(remaining);
                error.setLastState(state);
                this.errors.add(error);
            }

        }
    }

    private boolean isValidEndState(int state) {
        return state == 0 || state == 1 || state == 3 || state == 5 || state == 6;
    }

    public static void main(String[] args) {
        String methodName = "count";
        List<String> props = new ArrayList();
        props.add("id");
        props.add("name");
        props.add("username");
        ParsedCountDto parse = (new CountParser(methodName.toLowerCase(), props)).parse();
        parse.getParsedCounts();
    }
}
