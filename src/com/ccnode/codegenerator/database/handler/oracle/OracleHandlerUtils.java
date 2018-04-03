//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.oracle;

import com.ccnode.codegenerator.database.handler.utils.TypePropUtils;
import com.ccnode.codegenerator.dialog.GenCodeProp;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTypesUtil;
import java.util.List;
import java.util.Map;

public class OracleHandlerUtils {
    private static Map<String, List<TypeProps>> oracleTypeProps = Maps.newHashMap();

    public OracleHandlerUtils() {
    }

    public static List<TypeProps> newArrayListWithOrder(TypeProps... typePropArray) {
        List<TypeProps> typePropslist = Lists.newArrayList();

        for(int i = 0; i < typePropArray.length; ++i) {
            typePropArray[i].setOrder(i);
            typePropslist.add(typePropArray[i]);
        }

        return typePropslist;
    }

    public static List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField field) {
        String canonicalText = field.getType().getCanonicalText();
        List<TypeProps> fromMapTypes = (List)oracleTypeProps.get(canonicalText);
        List<TypeProps> typePropss = TypePropUtils.generateFromDefaultMap(fromMapTypes);
        if (typePropss != null) {
            if ("id".equals(field.getName())) {
                ((TypeProps)typePropss.get(0)).setPrimary(true);
                ((TypeProps)typePropss.get(0)).setHasDefaultValue(false);
            }

            return typePropss;
        } else {
            PsiClass psiClass = PsiTypesUtil.getPsiClass(field.getType());
            if (psiClass != null && psiClass.isEnum()) {
                return newArrayListWithOrder(new TypeProps("VARCHAR2", "50", "''"), new TypeProps("CHAR", (String)null, (String)null));
            } else {
                throw new RuntimeException("field type not support, the field is:" + field.getName() + " the type is:" + field.getType().getCanonicalText());
            }
        }
    }

    public static List<TypeProps> getTypePropByQulitifiedName(String name) {
        return (List)oracleTypeProps.get(name);
    }

    public static String extractJdbcType(GenCodeProp primaryProp) {
        String var1 = primaryProp.getFiledType();
        byte var2 = -1;
        switch(var1.hashCode()) {
            case -1981034679:
                if (var1.equals("NUMBER")) {
                    var2 = 0;
                }
                break;
            case -472293131:
                if (var1.equals("VARCHAR2")) {
                    var2 = 3;
                }
                break;
            case 2067286:
                if (var1.equals("CHAR")) {
                    var2 = 2;
                }
                break;
            case 2090926:
                if (var1.equals("DATE")) {
                    var2 = 1;
                }
        }

        switch(var2) {
            case 0:
                return "NUMERIC";
            case 1:
                return "DATE";
            case 2:
                return "CHAR";
            case 3:
                return "VARCHAR";
            default:
                throw new RuntimeException("the primary key must be string or number");
        }
    }

    static {
        TypeProps integerSqlTypeProps = new TypeProps("NUMBER", "12", "-1");
        oracleTypeProps.put("java.lang.Integer", newArrayListWithOrder(integerSqlTypeProps));
        TypeProps LONGNUMBER = new TypeProps("NUMBER", "24", "-1");
        oracleTypeProps.put("java.lang.Long", newArrayListWithOrder(LONGNUMBER));
        TypeProps FLOATNUMBER = new TypeProps("NUMBER", "10,2", "-1");
        oracleTypeProps.put("java.lang.Float", newArrayListWithOrder(FLOATNUMBER));
        TypeProps DOUBLENUMBER = new TypeProps("NUMBER", "16.4", "-1");
        oracleTypeProps.put("java.lang.Double", newArrayListWithOrder(DOUBLENUMBER));
        TypeProps BOOLEAN = new TypeProps("boolean", (String)null, "-1");
        oracleTypeProps.put("java.lang.Boolean", newArrayListWithOrder(BOOLEAN));
        TypeProps DECIMAL = new TypeProps("NUMBER", "13,4", "-1");
        oracleTypeProps.put("java.math.BigDecimal", newArrayListWithOrder(DECIMAL));
        TypeProps MEDIUMINT = new TypeProps("NUMBER", "12", "-1");
        TypeProps SMALLINT = new TypeProps("NUMBER", "5", "-1");
        oracleTypeProps.put("java.lang.Short", newArrayListWithOrder(SMALLINT, MEDIUMINT));
        TypeProps DATE = new TypeProps("DATE", "", "SYSDATE");
        TypeProps TIMESTAMP = new TypeProps("TIMESTAMP", (String)null, "SYSDATE");
        oracleTypeProps.put("java.util.Date", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalDate", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.sql.Date", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.sql.Timestamp", newArrayListWithOrder(TIMESTAMP, DATE));
        oracleTypeProps.put("java.sql.Time", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.OffsetDateTime", newArrayListWithOrder(DATE, TIMESTAMP));
        oracleTypeProps.put("java.time.OffsetTime", newArrayListWithOrder(TIMESTAMP));
        oracleTypeProps.put("java.time.ZonedDateTime", newArrayListWithOrder(DATE, TIMESTAMP));
        TypeProps VARCHAR2 = new TypeProps("VARCHAR2", "50", "''");
        TypeProps CHAR = new TypeProps("CHAR", "50", (String)null);
        oracleTypeProps.put("java.lang.String", newArrayListWithOrder(VARCHAR2, CHAR));
        TypeProps BYTE_NUMBER = new TypeProps("NUMBER", "8", "-1");
        oracleTypeProps.put("java.lang.Byte", newArrayListWithOrder(BYTE_NUMBER));
    }
}
