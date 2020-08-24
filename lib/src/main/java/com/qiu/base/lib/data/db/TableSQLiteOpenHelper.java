package com.qiu.base.lib.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.Table;
import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TableSQLiteOpenHelper<T extends TableBaseEntry> extends SQLiteOpenHelper {

    private static final String TAG = TableSQLiteOpenHelper.class.getSimpleName();

    private static class ColumnEntry {
        @NonNull
        public final Field mField;
        @Nullable
        public final Method mSetMethod;
        @Nullable
        public final Method mGetMethod;
        @NonNull
        public final Column mColumn;

        private ColumnEntry(@NonNull Column column, @NonNull Field field,
                @Nullable Method setMethod, @Nullable Method getMethod) {
            mField = field;
            mColumn = column;
            mSetMethod = setMethod;
            mGetMethod = getMethod;
            if (mSetMethod != null) {
                Logger.d(TAG, mSetMethod.getName());
            }
            if (mGetMethod != null) {
                Logger.d(TAG, mGetMethod.getName());
            }
        }
    }

    @NonNull
    private final Class<T> mClz;
    @NonNull
    private String mTableName;
    @NonNull
    private List<ColumnEntry> mColumnEntryList;
    @NonNull
    private String[] mColumns;

    public TableSQLiteOpenHelper(@Nullable Context context, @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory, int version,
            @NonNull Class<T> clz) {
        super(context, name, factory, version);
        Logger.d(TAG, clz.toString());
        mClz = clz;
        mTableName = getTableName();
        mColumnEntryList = getColumnList();
        mColumns = getColumns();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDeleteTableSql());
        onCreate(db);
    }

    public void insert(T t) throws IllegalAccessException, InvocationTargetException {
        getWritableDatabase().insert(mTableName, null, getInsertContentValues(t));
    }

    public void update(@NonNull String where, @NonNull T t)
            throws IllegalAccessException, InvocationTargetException {
        getWritableDatabase().update(mTableName, getInsertContentValues(t), where, null);
    }

    public void delete(String where) {
        getWritableDatabase().delete(mTableName, where, null);
    }

    // TODO Query elements by some case
    @Nullable
    public void query(Callback<T> callback) {
    }

    @NonNull
    public void queryAll(@NonNull Callback<List<T>> callback)
            throws IllegalAccessException, InvocationTargetException {
        List<T> result = new ArrayList<>();
        final Cursor cursor = getReadableDatabase()
                .query(mTableName, mColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            T t = createNewInstance();
            if (t == null) {
                break;
            }
            for (ColumnEntry entry : mColumnEntryList) {
                final int index = cursor.getColumnIndex(entry.mColumn.name());
                final ReflectUtils.ValueType type = ReflectUtils.getValueType(entry.mField);
                if (entry.mSetMethod != null) {
                    switch (type) {
                        case SHORT:
                            entry.mSetMethod.invoke(t, cursor.getShort(index));
                            break;
                        case INT:
                            entry.mSetMethod.invoke(t, cursor.getInt(index));
                            break;
                        case LONG:
                            entry.mSetMethod.invoke(t, cursor.getLong(index));
                            break;
                        case FLOAT:
                            entry.mSetMethod.invoke(t, cursor.getFloat(index));
                            break;
                        case DOUBLE:
                            entry.mSetMethod.invoke(t, cursor.getDouble(index));
                            break;
                        case STRING:
                        default:
                            entry.mSetMethod.invoke(t, cursor.getString(index));
                            break;
                    }
                } else {
                    switch (type) {
                        case SHORT:
                            entry.mField.setShort(t, cursor.getShort(index));
                            break;
                        case INT:
                            entry.mField.setInt(t, cursor.getInt(index));
                            break;
                        case LONG:
                            entry.mField.setLong(t, cursor.getLong(index));
                            break;
                        case FLOAT:
                            entry.mField.setFloat(t, cursor.getFloat(index));
                            break;
                        case DOUBLE:
                            entry.mField.setDouble(t, cursor.getDouble(index));
                            break;
                        case STRING:
                        default:
                            entry.mField.set(t, cursor.getString(index));
                            break;
                    }
                }
            }
            result.add(t);
        }
        cursor.close();
        callback.onCall(result);
    }

    @Nullable
    private T createNewInstance() {
        try {
            return mClz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, e.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
            Logger.e(TAG, e.toString());
        }
        return null;
    }

    protected String getCreateTableSql() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(mTableName).append(" (");
        for (int i = 0; i < mColumnEntryList.size(); i++) {
            final Column column = mColumnEntryList.get(i).mColumn;
            final ReflectUtils.ValueType type =
                    ReflectUtils.getValueType(mColumnEntryList.get(i).mField);
            sb.append(column.name());
            switch (type) {
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                    sb.append("INTEGER");
                    break;
                case STRING:
                    sb.append("TEXT");
                    break;
                default:
                    throw new RuntimeException(TAG + " Unknown field type");
            }
            if (column.primaryKey()) {
                sb.append(" PRIMARY KEY");
            }
            if (i < mColumnEntryList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @NonNull
    protected String getDeleteTableSql() {
        return "DROP TABLE IF EXISTS " + mTableName;
    }

    @NonNull
    protected List<ColumnEntry> getColumnList() {
        List<ColumnEntry> columnList = new ArrayList<>();
        List<Field> fields = ReflectUtils.getAllField(mClz);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnList
                        .add(new ColumnEntry(column, field, ReflectUtils.getSetMethod(field, mClz),
                                ReflectUtils.getGetMethod(field, mClz)));
            }
        }
        return columnList;
    }

    @NonNull
    protected String getTableName() {
        Table table = mClz.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            throw new RuntimeException("Table class should have annotation table");
        }

    }

    @NonNull
    private String[] getColumns() {
        final String[] columns = new String[mColumnEntryList.size()];
        for (int i = 0; i < mColumnEntryList.size(); i++) {
            columns[i] = mColumnEntryList.get(i).mColumn.name();
        }
        return columns;
    }

    private ContentValues getInsertContentValues(T t)
            throws IllegalAccessException, InvocationTargetException {
        ContentValues values = new ContentValues();
        for (ColumnEntry entry : mColumnEntryList) {
            final String name = entry.mColumn.name();
            if (entry.mGetMethod != null) {
                final ReflectUtils.ValueType type = ReflectUtils.getValueType(entry.mGetMethod);
                switch (type) {
                    case SHORT:
                        values.put(name, (Short) entry.mGetMethod.invoke(t));
                        break;
                    case INT:
                        values.put(name, (Integer) entry.mGetMethod.invoke(t));
                        break;
                    case LONG:
                        values.put(name, (Long) entry.mGetMethod.invoke(t));
                        break;
                    case FLOAT:
                        values.put(name, (Float) entry.mGetMethod.invoke(t));
                        break;
                    case DOUBLE:
                        values.put(name, (Double) entry.mGetMethod.invoke(t));
                        break;
                    case STRING:
                    default:
                        final Object v = entry.mGetMethod.invoke(t);
                        if (v != null) {
                            values.put(name, v.toString());
                        }
                        break;
                }
            } else {
                final ReflectUtils.ValueType type = ReflectUtils.getValueType(entry.mField);
                switch (type) {
                    case SHORT:
                        values.put(name, entry.mField.getShort(t));
                        break;
                    case INT:
                        values.put(name, entry.mField.getInt(t));
                        break;
                    case LONG:
                        values.put(name, entry.mField.getLong(t));
                        break;
                    case FLOAT:
                        values.put(name, entry.mField.getFloat(t));
                        break;
                    case DOUBLE:
                        values.put(name, entry.mField.getDouble(t));
                        break;
                    case STRING:
                    default:
                        final Object v = entry.mField.get(t);
                        if (v != null) {
                            values.put(name, v.toString());
                        }
                        break;
                }
            }
        }
        return values;
    }
}
