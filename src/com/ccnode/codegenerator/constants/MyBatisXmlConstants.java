package com.ccnode.codegenerator.constants;


import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href ="mailto: janloongdoo@gmail.com">Janloong</a>
 * @date 2018-01-02 11:57
 */
public class MyBatisXmlConstants {
    public static final String RESULTMAP = "resultMap";
    public static final String RESULT_TYPE = "resultType";
    public static final String SELECT = "select";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SQL = "sql";
    public static final String REFID = "refid";
    public static final String INCLUDE = "include";
    public static final String ID = "id";
    public static final String IF = "if";
    public static final String PROPERTY = "property";
    public static final String COLUMN = "column";
    public static final String KEY_PROPERTY = "keyProperty";
    public static final String RESULT = "result";
    public static final String TEST = "test";
    public static final String TYPE = "type";
    public static final String COLLECTION = "collection";
    public static final String NAMESPACE = "namespace";
    public static final String MAPPER = "mapper";
    public static Set<String> mapperMethodSet = new HashSet<String>() {
        {
            this.add("insert");
            this.add("update");
            this.add("delete");
            this.add("select");
        }
    };

    public MyBatisXmlConstants() {
    }
}
