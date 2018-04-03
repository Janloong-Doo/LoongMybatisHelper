//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.mysql;

import com.ccnode.codegenerator.database.handler.utils.TypePropUtils;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MysqlHandlerUtils {
    private static Map<String, List<TypeProps>> mysqlTypeProps = Maps.newHashMap();

    public MysqlHandlerUtils() {
    }

    public static List<TypeProps> newArrayListWithOrder(TypeProps... typePropArray) {
        List<TypeProps> typePropslist = Lists.newArrayList();

        for (int i = 0; i < typePropArray.length; ++i) {
            typePropArray[i].setOrder(i);
            typePropslist.add(typePropArray[i]);
        }

        return typePropslist;
    }

    private static String unsigned(String type) {
        return type + "_" + "UNSIGNED";
    }

    @NotNull
    public static List<TypeProps> getRecommendDatabaseTypeOfFieldType(PsiField psiField) {
        String canonicalText = psiField.getType().getCanonicalText();
        List<TypeProps> fromMapTypes = (List) mysqlTypeProps.get(canonicalText);
        List<TypeProps> typePropss = TypePropUtils.generateFromDefaultMap(fromMapTypes);
        if (typePropss == null) {
            PsiClass psiClass = PsiTypesUtil.getPsiClass(psiField.getType());
            if (psiClass != null && psiClass.isEnum()) {
                List var10000 = newArrayListWithOrder(new TypeProps("VARCHAR", "50", "''"), new TypeProps("INT", "11", "-1"), new TypeProps("SMALLINT", "5", "-1"), new TypeProps("MEDIUMINT", "7", "-1"), new TypeProps("TINYINT", "3", "0"));
                if (var10000 == null) {
                    throw new RuntimeException("MysqlHandlerUtils getRecommendDatabaseTypeOfFieldType 有错误啊");
                    //$$$reportNull$$$0(2);
                    //ExternalProjectsStructure.ErrorLevel.ERROR
                }

                return var10000;
            } else {
                throw new RuntimeException("field type not support, the field is:" + psiField.getName() + " the type is:" + psiField.getType().getCanonicalText());
            }
        } else {
            if ("id".equals(psiField.getName())) {
                ((TypeProps) typePropss.get(0)).setPrimary(true);
                ((TypeProps) typePropss.get(0)).setHasDefaultValue(false);
            } else if ("updatetime".equalsIgnoreCase(psiField.getName())) {
                Iterator var4 = typePropss.iterator();

                while (var4.hasNext()) {
                    TypeProps props = (TypeProps) var4.next();
                    if (props.getDefaultType().equals("TIMESTAMP")) {
                        props.setOrder(-1);
                        break;
                    }
                }

                if (typePropss == null) {
                    //$$$reportNull$$$0(0);
                    throw new RuntimeException("MysqlHandlerUtils getRecommendDatabaseTypeOfFieldType 有错误啊");

                }


                return typePropss;
            }

            if (typePropss == null) {
                //$$$reportNull$$$0(1);
                throw new RuntimeException("MysqlHandlerUtils getRecommendDatabaseTypeOfFieldType 有错误啊");

            }

            return typePropss;
        }
    }

    public static UnsignedCheckResult checkUnsigned(String chooseType) {
        UnsignedCheckResult result = new UnsignedCheckResult();
        String[] split = chooseType.split("_");
        result.setType(split[0]);
        if (split.length == 2 && split[1].equals("UNSIGNED")) {
            result.setUnsigned(true);
            return result;
        } else {
            result.setUnsigned(false);
            return result;
        }
    }

    public static List<TypeProps> getTypePropsByQulifiType(String type) {
        return (List) mysqlTypeProps.get(type);
    }

    static {
        TypeProps integerSqlTypeProps = new TypeProps("INT", "11", "-1");
        TypeProps unsignedIntegerTypeProps = new TypeProps(unsigned("INT"), "11", "0");
        mysqlTypeProps.put("java.lang.Integer", newArrayListWithOrder(integerSqlTypeProps, unsignedIntegerTypeProps));
        TypeProps bigIntType = new TypeProps("BIGINT", "15", "-1");
        TypeProps unsignedBigIntType = new TypeProps(unsigned("BIGINT"), "15", "0");
        mysqlTypeProps.put("java.lang.Long", newArrayListWithOrder(bigIntType, unsignedBigIntType));
        TypeProps FLOAT = new TypeProps("FLOAT", "10,2", "-1.0");
        mysqlTypeProps.put("java.lang.Float", newArrayListWithOrder(FLOAT));
        TypeProps DOUBLE = new TypeProps("DOUBLE", "16,4", "-1.0");
        mysqlTypeProps.put("java.lang.Double", newArrayListWithOrder(DOUBLE));
        TypeProps TINYINT = new TypeProps("TINYINT", "3", "0");
        TypeProps UNSIGNED_TINYINT = new TypeProps(unsigned("TINYINT"), "3", "0");
        TypeProps BIT = new TypeProps("BIT", "1", "0");
        mysqlTypeProps.put("java.lang.Boolean", newArrayListWithOrder(TINYINT, UNSIGNED_TINYINT, BIT));
        TypeProps DECIMAL = new TypeProps("DECIMAL", "13,4", "-1");
        mysqlTypeProps.put("java.math.BigDecimal", newArrayListWithOrder(DECIMAL));
        TypeProps MEDIUMINT = new TypeProps("MEDIUMINT", "7", "-1");
        TypeProps unsignedMediumInt = new TypeProps(unsigned("MEDIUMINT"), "7", "0");
        TypeProps SMALLINT = new TypeProps("SMALLINT", "5", "-1");
        TypeProps unsignedSMALLINT = new TypeProps(unsigned("SMALLINT"), "5", "0");
        mysqlTypeProps.put("java.lang.Short", newArrayListWithOrder(SMALLINT, unsignedSMALLINT, MEDIUMINT, unsignedMediumInt));
        mysqlTypeProps.put("java.lang.Byte", newArrayListWithOrder(TINYINT, UNSIGNED_TINYINT));
        TypeProps DATETIME = new TypeProps("DATETIME", "", "'1000-01-01 00:00:00'");
        TypeProps DATE = new TypeProps("DATE", "", "'1000-01-01'");
        TypeProps TIMESTAMP = new TypeProps("TIMESTAMP", (String) null, "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
        TypeProps TIME = new TypeProps("TIME", (String) null, "'12:00'");
        mysqlTypeProps.put("java.util.Date", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.LocalDate", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.LocalTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.sql.Date", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.sql.Timestamp", newArrayListWithOrder(TIMESTAMP, DATE, DATETIME, TIME));
        mysqlTypeProps.put("java.sql.Time", newArrayListWithOrder(TIME, DATE, TIMESTAMP, DATETIME));
        mysqlTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.LocalTime", newArrayListWithOrder(TIME, DATETIME, DATE, TIMESTAMP));
        mysqlTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.OffsetDateTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        mysqlTypeProps.put("java.time.OffsetTime", newArrayListWithOrder(TIME, DATETIME, TIMESTAMP, DATETIME));
        mysqlTypeProps.put("java.time.ZonedDateTime", newArrayListWithOrder(DATETIME, DATE, TIMESTAMP, TIME));
        TypeProps VARCHAR = new TypeProps("VARCHAR", "50", "''");
        TypeProps TEXT = new TypeProps("TEXT", (String) null, (String) null);
        TypeProps MEDIUMTEXT = new TypeProps("MEDIUMTEXT", (String) null, "''");
        TypeProps LONGTEXT = new TypeProps("LONGTEXT", (String) null, "''");
        TypeProps TINYTEXT = new TypeProps("TINYTEXT", (String) null, "''");
        TypeProps CHAR = new TypeProps("CHAR", "10", (String) null);
        mysqlTypeProps.put("java.lang.String", newArrayListWithOrder(VARCHAR, TEXT, CHAR, MEDIUMTEXT, LONGTEXT, TINYTEXT));
        TypeProps BLOB = new TypeProps("BLOB", "", "''");
        TypeProps TINYBLOB = new TypeProps("TINYBLOB", "", "''");
        TypeProps MEDIUMBLOB = new TypeProps("MEDIUMBLOB", "", "''");
        TypeProps LONGBLOB = new TypeProps("LONGBLOB", "", "''");
        mysqlTypeProps.put("java.lang.Byte[]", newArrayListWithOrder(BLOB, MEDIUMBLOB, LONGBLOB, TINYBLOB));
    }
}
