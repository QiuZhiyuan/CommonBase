package com.qiu.base.lib.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        return getValueByName(typeName);
    }

    public static ValueType getValueType(@NonNull Method method) {
        final String typeName = method.getReturnType().getSimpleName();
        return getValueByName(typeName);
    }

    private static ValueType getValueByName(@NonNull String name) {
        switch (name) {
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

    @Nullable
    public static Method getGetMethod(@NonNull Field field, @NonNull Class<?> clz) {
        final String methodName = getPossibleMethodName("get", field.getName());
        try {
            return clz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static Method getSetMethod(@NonNull Field field, @NonNull Class<?> clz) {
        final String methodName = getPossibleMethodName("set", field.getName());
        try {
            return clz.getMethod(methodName, field.getType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static String getPossibleMethodName(@NonNull String methodStart,
            @NonNull String name) {
        if (name.length() == 0) {
            return name;
        }
        if (name.length() == 1) {
            return methodStart + name.toUpperCase();
        }
        final char start = name.charAt(0);
        if (isUpperStyle(start)) {
            return methodStart + name;
        } else if (start == 'm') {
            final char second = name.charAt(1);
            if (isUpperStyle(second)) {
                return methodStart + name.substring(1);
            } else {
                return methodStart + "M" + name.substring(1);
            }
        } else {
            return methodStart + String.valueOf(start).toUpperCase() + name.substring(1);
        }
    }

    private static boolean isUpperStyle(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private static boolean isLowerStyle(char ch) {
        return ch >= 'a' && ch <= 'z';
    }
}
