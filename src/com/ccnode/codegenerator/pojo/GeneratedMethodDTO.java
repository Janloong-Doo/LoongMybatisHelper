//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.pojo;

import java.beans.ConstructorProperties;
import java.util.Set;

public class GeneratedMethodDTO {
    private Set<String> imports;
    private String methodText;

    @ConstructorProperties({"imports", "methodText"})
    GeneratedMethodDTO(Set<String> imports, String methodText) {
        this.imports = imports;
        this.methodText = methodText;
    }

    public static GeneratedMethodDTO.GeneratedMethodDTOBuilder builder() {
        return new GeneratedMethodDTO.GeneratedMethodDTOBuilder();
    }

    public Set<String> getImports() {
        return this.imports;
    }

    public String getMethodText() {
        return this.methodText;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public void setMethodText(String methodText) {
        this.methodText = methodText;
    }

    public static class GeneratedMethodDTOBuilder {
        private Set<String> imports;
        private String methodText;

        GeneratedMethodDTOBuilder() {
        }

        public GeneratedMethodDTO.GeneratedMethodDTOBuilder imports(Set<String> imports) {
            this.imports = imports;
            return this;
        }

        public GeneratedMethodDTO.GeneratedMethodDTOBuilder methodText(String methodText) {
            this.methodText = methodText;
            return this;
        }

        public GeneratedMethodDTO build() {
            return new GeneratedMethodDTO(this.imports, this.methodText);
        }

        public String toString() {
            return "GeneratedMethodDTO.GeneratedMethodDTOBuilder(imports=" + this.imports + ", methodText=" + this.methodText + ")";
        }
    }
}
