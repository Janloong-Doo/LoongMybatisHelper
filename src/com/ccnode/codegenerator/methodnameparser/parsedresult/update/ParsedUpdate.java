//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.parsedresult.update;

import com.ccnode.codegenerator.methodnameparser.parsedresult.base.ParsedBase;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class ParsedUpdate extends ParsedBase {
    private List<UpdateField> updateProps;

    public ParsedUpdate() {
    }

    public void addUpdateProps(String prop) {
        if (this.updateProps == null) {
            this.updateProps = new ArrayList();
        }

        UpdateField field;
        if (this.updateProps.size() == 0) {
            field = new UpdateField();
            field.setProp(prop);
            this.updateProps.add(field);
        } else {
            field = (UpdateField)this.updateProps.get(this.updateProps.size() - 1);
            if (field.getProp() == null) {
                field.setProp(prop);
            } else {
                UpdateField newField = new UpdateField();
                newField.setProp(prop);
                this.updateProps.add(newField);
            }
        }

    }

    public void addPrefix(String prefix) {
        if (this.updateProps == null) {
            this.updateProps = new ArrayList();
        }

        UpdateField field = new UpdateField();
        field.setPrefix(prefix);
        this.updateProps.add(field);
    }

    public ParsedUpdate clone() {
        return (ParsedUpdate)Cloner.standard().deepClone(this);
    }

    public List<UpdateField> getUpdateProps() {
        return this.updateProps;
    }
}
