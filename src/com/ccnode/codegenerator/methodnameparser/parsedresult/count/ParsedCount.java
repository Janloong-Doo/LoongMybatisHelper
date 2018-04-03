//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.count;

import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedBase;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class ParsedCount extends ParsedBase {
    private boolean distinct = false;
    private List<String> fetchProps;

    public ParsedCount() {
    }

    public void addFetchProps(String props) {
        if (this.fetchProps == null) {
            this.fetchProps = new ArrayList();
        }

        this.fetchProps.add(props);
    }

    public List<String> getFetchProps() {
        return this.fetchProps;
    }

    public void setFetchProps(List<String> fetchProps) {
        this.fetchProps = fetchProps;
    }

    public ParsedCount clone() {
        return (ParsedCount)Cloner.standard().deepClone(this);
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
}
