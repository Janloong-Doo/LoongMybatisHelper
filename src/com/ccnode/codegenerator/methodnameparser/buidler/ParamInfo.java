//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.methodnameparser.buidler;

import java.beans.ConstructorProperties;

public class ParamInfo {
    private String paramType;
    private String paramAnno;
    private String paramValue;
    private String paramFullType;

    @ConstructorProperties({"paramType", "paramAnno", "paramValue", "paramFullType"})
    ParamInfo(String paramType, String paramAnno, String paramValue, String paramFullType) {
        this.paramType = paramType;
        this.paramAnno = paramAnno;
        this.paramValue = paramValue;
        this.paramFullType = paramFullType;
    }

    public static ParamInfo.ParamInfoBuilder builder() {
        return new ParamInfo.ParamInfoBuilder();
    }

    public String getParamType() {
        return this.paramType;
    }

    public String getParamAnno() {
        return this.paramAnno;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public String getParamFullType() {
        return this.paramFullType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public void setParamAnno(String paramAnno) {
        this.paramAnno = paramAnno;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public void setParamFullType(String paramFullType) {
        this.paramFullType = paramFullType;
    }

    public static class ParamInfoBuilder {
        private String paramType;
        private String paramAnno;
        private String paramValue;
        private String paramFullType;

        ParamInfoBuilder() {
        }

        public ParamInfo.ParamInfoBuilder paramType(String paramType) {
            this.paramType = paramType;
            return this;
        }

        public ParamInfo.ParamInfoBuilder paramAnno(String paramAnno) {
            this.paramAnno = paramAnno;
            return this;
        }

        public ParamInfo.ParamInfoBuilder paramValue(String paramValue) {
            this.paramValue = paramValue;
            return this;
        }

        public ParamInfo.ParamInfoBuilder paramFullType(String paramFullType) {
            this.paramFullType = paramFullType;
            return this;
        }

        public ParamInfo build() {
            return new ParamInfo(this.paramType, this.paramAnno, this.paramValue, this.paramFullType);
        }

        public String toString() {
            return "ParamInfo.ParamInfoBuilder(paramType=" + this.paramType + ", paramAnno=" + this.paramAnno + ", paramValue=" + this.paramValue + ", paramFullType=" + this.paramFullType + ")";
        }
    }
}
