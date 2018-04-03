//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parser;

import com.ccnode.codegenerator.dialog.ParseTypeEnum;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDelete;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDeleteDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.delete.ParsedDeleteError;
import java.util.ArrayList;
import java.util.List;

public class DeleteParser extends BaseParser {
    private List<ParsedDelete> parsedDeletes = new ArrayList();
    private List<ParsedDeleteError> errors = new ArrayList();

    public DeleteParser(String methodName, List<String> props) {
        super(methodName, props);
    }

    public ParsedDeleteDto parse() {
        int state = 0;
        int len = "delete".length();
        ParsedDelete parsedDelete = new ParsedDelete();
        this.parseMethods(state, this.methodName, len, parsedDelete);
        ParsedDeleteDto dto = new ParsedDeleteDto();
        dto.setParsedDeletes(this.parsedDeletes);
        dto.setErrors(this.errors);
        return dto;
    }

    private void parseMethods(int state, String methodName, int len, ParsedDelete parsedDelete) {
        if (methodName.length() == len) {
            if (this.isValidState(state)) {
                this.parsedDeletes.add(parsedDelete);
            } else {
                ParsedDeleteError error = new ParsedDeleteError();
                error.setParsedDelete(parsedDelete);
                error.setRemaining("");
                error.setLastState(state);
                this.errors.add(error);
            }

        } else {
            String remaining;
            boolean newParseDelete;
            remaining = methodName.substring(len);
            newParseDelete = false;
            String[] var7;
            int var8;
            int var9;
            String comp;
            ParsedDelete clone;
            label72:
            switch(state) {
                case 0:
                    if (remaining.startsWith("by")) {
                        ParsedDelete newDelete = parsedDelete.clone();
                        newDelete.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(1, remaining, "by".length(), newDelete);
                        newParseDelete = true;
                    }
                    break;
                case 1:
                    int i = 0;

                    while(true) {
                        if (i >= this.props.length) {
                            break label72;
                        }

                        if (remaining.startsWith(this.lowerProps[i])) {
                            ParsedDelete clone = parsedDelete.clone();
                            clone.addQueryProp(this.props[i]);
                            clone.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(2, remaining, this.props[i].length(), clone);
                            newParseDelete = true;
                        }

                        ++i;
                    }
                case 2:
                    var7 = linkOp;
                    var8 = var7.length;

                    for(var9 = 0; var9 < var8; ++var9) {
                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = parsedDelete.clone();
                            clone.addConnector(comp);
                            clone.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(1, remaining, comp.length(), clone);
                            newParseDelete = true;
                        }
                    }

                    var7 = compareOp;
                    var8 = var7.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= var8) {
                            break label72;
                        }

                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = parsedDelete.clone();
                            clone.addQueryOperator(comp);
                            clone.addParsePart(ParseTypeEnum.COMPARATOR, comp);
                            this.parseMethods(3, remaining, comp.length(), clone);
                            newParseDelete = true;
                        }

                        ++var9;
                    }
                case 3:
                    var7 = linkOp;
                    var8 = var7.length;

                    for(var9 = 0; var9 < var8; ++var9) {
                        comp = var7[var9];
                        if (remaining.startsWith(comp)) {
                            clone = parsedDelete.clone();
                            clone.addConnector(comp);
                            clone.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(1, remaining, comp.length(), clone);
                            newParseDelete = true;
                        }
                    }
            }

            if (!newParseDelete) {
                ParsedDeleteError error = new ParsedDeleteError();
                error.setParsedDelete(parsedDelete);
                error.setRemaining(remaining);
                error.setLastState(state);
                this.errors.add(error);
            }

        }
    }

    private boolean isValidState(int state) {
        return state == 0 || state == 2 || state == 3;
    }
}
