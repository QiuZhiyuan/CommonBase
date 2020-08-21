package com.qiu.base.lib.tools;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtils {

    private static final String TAG = ReflectUtils.class.getSimpleName();

    public enum ValueType {
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        CHAR,
        STRING,
    }

    public static ValueType getValueType(@NonNull Field field) {
        final String typeName = field.getType().getSimpleName();
        Logger.d(TAG, "Field name:" + field.getName() + " Type Name:" + typeName);
        switch (typeName) {
            case "short":
                return ValueType.SHORT;
            case "int":
                return ValueType.INT;
            case "long":
                return ValueType.LONG;
            default:
                return ValueType.STRING;
        }
    }

    @NonNull
    public static List<Field> getAllField(@NonNull Class<?> clz) {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(clz.getDeclaredFields()));
        if (clz.getSuperclass() != null) {
            fieldList.addAll(getAllField(clz.getSuperclass()));
        }
        return fieldList;
    }
}
