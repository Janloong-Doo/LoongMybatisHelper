//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ccnode.codegenerator.database.handler.utils;

import com.ccnode.codegenerator.dialog.datatype.TypeProps;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class TypePropUtils {
    public TypePropUtils() {
    }

    @Nullable
    public static List<TypeProps> generateFromDefaultMap(List<TypeProps> fromMapTypes) {
        if (fromMapTypes == null) {
            return null;
        } else {
            List<TypeProps> typePropslist = Lists.newArrayList();
            Iterator var2 = fromMapTypes.iterator();

            while(var2.hasNext()) {
                TypeProps fromMapType = (TypeProps)var2.next();
                typePropslist.add(convert(fromMapType));
            }

            return typePropslist;
        }
    }

    private static TypeProps convert(TypeProps fromMapType) {
        TypeProps typeProps = new TypeProps();
        typeProps.setOrder(fromMapType.getOrder());
        typeProps.setIndex(fromMapType.getIndex());
        typeProps.setHasDefaultValue(fromMapType.getHasDefaultValue());
        typeProps.setPrimary(fromMapType.getPrimary());
        typeProps.setDefaultType(fromMapType.getDefaultType());
        typeProps.setSize(fromMapType.getSize());
        typeProps.setCanBeNull(fromMapType.getCanBeNull());
        typeProps.setUnique(fromMapType.getUnique());
        typeProps.setDefaultValue(fromMapType.getDefaultValue());
        return typeProps;
    }
}
