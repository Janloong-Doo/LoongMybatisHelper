//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parser;

import com.ccnode.codegenerator.dialog.ParseTypeEnum;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdate;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdateDto;
import com.ccnode.codegenerator.methodnameparser.parsedresult.update.ParsedUpdateError;
import java.util.ArrayList;
import java.util.List;

public class UpdateParser extends BaseParser {
    private List<ParsedUpdate> parsedUpdates = new ArrayList();
    private List<ParsedUpdateError> errors = new ArrayList();

    public UpdateParser(String methodName, List<String> props) {
        super(methodName, props);
    }

    public ParsedUpdateDto parse() {
        int state = 0;
        int len = "update".length();
        ParsedUpdate parsedUpdate = new ParsedUpdate();
        this.parseMethods(state, this.methodName, len, parsedUpdate);
        ParsedUpdateDto dto = new ParsedUpdateDto();
        dto.setUpdateList(this.parsedUpdates);
        dto.setErrorList(this.errors);
        return dto;
    }

    private void parseMethods(int state, String methodName, int len, ParsedUpdate parsedUpdate) {
        if (methodName.length() == len) {
            if (this.isValidState(state)) {
                this.parsedUpdates.add(parsedUpdate);
            } else {
                ParsedUpdateError error = new ParsedUpdateError();
                error.setParsedUpdate(parsedUpdate);
                error.setLastState(state);
                error.setRemaining("");
                this.errors.add(error);
            }

        } else {
            String remaining;
            boolean newParseUpdate;
            remaining = methodName.substring(len);
            newParseUpdate = false;
            int i;
            ParsedUpdate newUpdate;
            int var9;
            String comp;
            ParsedUpdate newUpdate;
            String[] var13;
            int var14;
            label115:
            switch(state) {
                case 0:
                    for(i = 0; i < this.props.length; ++i) {
                        if (remaining.startsWith(this.lowerProps[i])) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addUpdateProps(this.props[i]);
                            newUpdate.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(1, remaining, this.props[i].length(), newUpdate);
                            newParseUpdate = true;
                        }
                    }

                    var13 = updatePrefixs;
                    var14 = var13.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= var14) {
                            break label115;
                        }

                        comp = var13[var9];
                        if (remaining.startsWith(comp)) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addPrefix(comp);
                            newUpdate.addParsePart(ParseTypeEnum.INCORDEC, comp);
                            this.parseMethods(5, remaining, comp.length(), newUpdate);
                            newParseUpdate = true;
                        }

                        ++var9;
                    }
                case 1:
                    ParsedUpdate newUpdate;
                    if (remaining.startsWith("and")) {
                        newUpdate = parsedUpdate.clone();
                        newUpdate.addParsePart(ParseTypeEnum.AND, "and");
                        this.parseMethods(0, remaining, "and".length(), newUpdate);
                        newParseUpdate = true;
                    }

                    if (remaining.startsWith("by")) {
                        newUpdate = parsedUpdate.clone();
                        newUpdate.addParsePart(ParseTypeEnum.BY, "by");
                        this.parseMethods(2, remaining, "by".length(), newUpdate);
                        newParseUpdate = true;
                    }
                    break;
                case 2:
                    i = 0;

                    while(true) {
                        if (i >= this.props.length) {
                            break label115;
                        }

                        if (remaining.startsWith(this.lowerProps[i])) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addQueryProp(this.props[i]);
                            newUpdate.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(3, remaining, this.props[i].length(), newUpdate);
                            newParseUpdate = true;
                        }

                        ++i;
                    }
                case 3:
                    var13 = linkOp;
                    var14 = var13.length;

                    for(var9 = 0; var9 < var14; ++var9) {
                        comp = var13[var9];
                        if (remaining.startsWith(comp)) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addConnector(comp);
                            newUpdate.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(2, remaining, comp.length(), newUpdate);
                            newParseUpdate = true;
                        }
                    }

                    var13 = compareOp;
                    var14 = var13.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= var14) {
                            break label115;
                        }

                        comp = var13[var9];
                        if (remaining.startsWith(comp)) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addQueryOperator(comp);
                            newUpdate.addParsePart(ParseTypeEnum.COMPARATOR, comp);
                            this.parseMethods(4, remaining, comp.length(), newUpdate);
                            newParseUpdate = true;
                        }

                        ++var9;
                    }
                case 4:
                    var13 = linkOp;
                    var14 = var13.length;
                    var9 = 0;

                    while(true) {
                        if (var9 >= var14) {
                            break label115;
                        }

                        comp = var13[var9];
                        if (remaining.startsWith(comp)) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addConnector(comp);
                            newUpdate.addParsePart(ParseTypeEnum.LINKOP, comp);
                            this.parseMethods(2, remaining, comp.length(), newUpdate);
                            newParseUpdate = true;
                        }

                        ++var9;
                    }
                case 5:
                    for(i = 0; i < this.props.length; ++i) {
                        if (remaining.startsWith(this.lowerProps[i])) {
                            newUpdate = parsedUpdate.clone();
                            newUpdate.addUpdateProps(this.props[i]);
                            newUpdate.addParsePart(ParseTypeEnum.PROPERTY, this.props[i]);
                            this.parseMethods(1, remaining, this.props[i].length(), newUpdate);
                            newParseUpdate = true;
                        }
                    }
            }

            if (!newParseUpdate) {
                ParsedUpdateError error = new ParsedUpdateError();
                error.setParsedUpdate(parsedUpdate);
                error.setRemaining(remaining);
                error.setLastState(state);
                this.errors.add(error);
            }

        }
    }

    private boolean isValidState(int state) {
        return state == 1 || state == 3 || state == 4;
    }
}
