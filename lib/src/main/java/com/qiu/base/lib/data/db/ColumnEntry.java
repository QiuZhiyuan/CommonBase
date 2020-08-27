package com.qiu.base.lib.data.db;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ColumnEntry {
    private static final String TAG = ColumnEntry.class.getSimpleName();
    @NonNull
    public final Field mField;
    @Nullable
    public final Method mSetMethod;
    @Nullable
    public final Method mGetMethod;
    @NonNull
    public final Column mColumn;

    public ColumnEntry(@NonNull Column column, @NonNull Field field,
            @Nullable Method setMethod, @Nullable Method getMethod) {
        mField = field;
        mColumn = column;
        mSetMethod = setMethod;
        mGetMethod = getMethod;
    }

    public void setValueFromCursor(@NonNull Object t, @NonNull Cursor cursor)
            throws InvocationTargetException, IllegalAccessException {
        final int index = cursor.getColumnIndex(mColumn.name());
        if (mSetMethod != null) {
            final ReflectUtils.ValueType type = ReflectUtils.getParamsType(mSetMethod);
            if (type == null) {
                Logger.e(TAG, "setMethod type is null");
                return;
            }
            switch (type) {
                case SHORT:
                    mSetMethod.invoke(t, cursor.getShort(index));
                    break;
                case INT:
                    mSetMethod.invoke(t, cursor.getInt(index));
                    break;
                case LONG:
                    mSetMethod.invoke(t, cursor.getLong(index));
                    break;
                case FLOAT:
                    mSetMethod.invoke(t, cursor.getFloat(index));
                    break;
                case DOUBLE:
                    mSetMethod.invoke(t, cursor.getDouble(index));
                    break;
                case STRING:
                    mSetMethod.invoke(t, cursor.getString(index));
                    break;
                case OBJECT:
                    Class<?> clz = ReflectUtils.getFirstParamsClass(mSetMethod);
                    if (clz != null) {
                        final Object obj = JSON.parseObject(cursor.getString(index), clz);
                        mSetMethod.invoke(t, obj);
                    }
                    break;
                default:
                    Logger.e(TAG, "Unknown type");
                    break;
            }
        } else {
            final ReflectUtils.ValueType type = ReflectUtils.getValueType(mField);
            switch (type) {
                case SHORT:
                    mField.setShort(t, cursor.getShort(index));
                    break;
                case INT:
                    mField.setInt(t, cursor.getInt(index));
                    break;
                case LONG:
                    mField.setLong(t, cursor.getLong(index));
                    break;
                case FLOAT:
                    mField.setFloat(t, cursor.getFloat(index));
                    break;
                case DOUBLE:
                    mField.setDouble(t, cursor.getDouble(index));
                    break;
                case STRING:
                    mField.set(t, cursor.getString(index));
                    break;
                case OBJECT:
                    final Object obj = JSON.parseObject(cursor.getString(index), mField.getType());
                    mField.set(t, obj);
                    break;
                default:
                    Logger.e(TAG, "Unknown type");
                    break;
            }
        }
    }
}