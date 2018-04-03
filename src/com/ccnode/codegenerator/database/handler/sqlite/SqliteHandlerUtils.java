//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.sqlite;

import com.ccnode.codegenerator.database.handler.utils.TypePropUtils;
import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTypesUtil;
import java.util.List;
import java.util.Map;

public class SqliteHandlerUtils {
    private static Map<String, List<TypeProps>> sqliteTypeProps = Maps.newHashMap();

    public SqliteHandlerUtils() {
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
        List<TypeProps> fromMapTypes = (List)sqliteTypeProps.get(canonicalText);
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
                return newArrayListWithOrder(new TypeProps("TEXT", "50", "''"), new TypeProps("NUMERIC", (String)null, (String)null));
            } else {
                throw new RuntimeException("field type not support, the field is:" + field.getName() + " the type is:" + field.getType().getCanonicalText());
            }
        }
    }

    public static List<TypeProps> getTypePropByQulitifiedName(String name) {
        return (List)sqliteTypeProps.get(name);
    }

    static {
        TypeProps integerSqlTypeProps = new TypeProps("INTEGER", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Integer", newArrayListWithOrder(integerSqlTypeProps));
        TypeProps LONGNUMBER = new TypeProps("INTEGER", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Long", newArrayListWithOrder(LONGNUMBER));
        TypeProps FLOATNUMBER = new TypeProps("REAL", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Float", newArrayListWithOrder(FLOATNUMBER));
        TypeProps DOUBLENUMBER = new TypeProps("REAL", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Double", newArrayListWithOrder(DOUBLENUMBER));
        TypeProps BOOLEAN = new TypeProps("INTEGER", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Boolean", newArrayListWithOrder(BOOLEAN));
        TypeProps DECIMAL = new TypeProps("NUMERIC", (String)null, "-1");
        sqliteTypeProps.put("java.math.BigDecimal", newArrayListWithOrder(DECIMAL));
        TypeProps MEDIUMINT = new TypeProps("INTEGER", (String)null, "-1");
        TypeProps SMALLINT = new TypeProps("INTEGER", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Short", newArrayListWithOrder(SMALLINT, MEDIUMINT));
        TypeProps DATE = new TypeProps("TEXT", (String)null, "CURRENT_TIMESTAMP");
        TypeProps TIMESTAMP = new TypeProps("INTEGER", (String)null, "CURRENT_TIMESTAMP");
        TypeProps DATE_ASREAL = new TypeProps("REAL", (String)null, "CURRENT_TIMESTAMP");
        sqliteTypeProps.put("java.util.Date", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalDate", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.sql.Date", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.sql.Timestamp", newArrayListWithOrder(TIMESTAMP, DATE));
        sqliteTypeProps.put("java.sql.Time", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.LocalDateTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.OffsetDateTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.OffsetTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        sqliteTypeProps.put("java.time.ZonedDateTime", newArrayListWithOrder(DATE, TIMESTAMP, DATE_ASREAL));
        TypeProps VARCHAR2 = new TypeProps("TEXT", (String)null, "''");
        TypeProps CHAR = new TypeProps("TEXT", (String)null, (String)null);
        sqliteTypeProps.put("java.lang.String", newArrayListWithOrder(VARCHAR2, CHAR));
        TypeProps BYTE_INTEGER = new TypeProps("INTEGER", (String)null, "-1");
        sqliteTypeProps.put("java.lang.Byte", newArrayListWithOrder(BYTE_INTEGER));
    }
}
