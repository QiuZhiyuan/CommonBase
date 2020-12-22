package com.qiu.base.lib.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.Table;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TableSQLiteOpenHelper<T extends TableBaseEntry> extends SQLiteOpenHelper {

    private enum ColumnType {
        TEXT("TEXT"),
        INTEGER("INTEGER"),
        ;
        final String name;

        ColumnType(@NonNull String name) {
            this.name = name;
        }
    }

    private static final String TAG = TableSQLiteOpenHelper.class.getSimpleName();

    @NonNull
    private final Class<T> mClz;
    @NonNull
    private final String mTableName;
    @NonNull
    private final List<ColumnEntry> mColumnEntryList;
    @NonNull
    private final String[] mColumns;

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
        onDeleteTable(db);
        onCreate(db);
    }

    public void onDeleteTable(SQLiteDatabase db) {
        db.execSQL(getDeleteTableSql());
    }

    public void insert(@NonNull TableBaseEntry t) {
        getWritableDatabase().insert(mTableName, null, getInsertContentValues(t));
    }

    public void update(@NonNull TableBaseEntry t) {
        getWritableDatabase().update(mTableName, getInsertContentValues(t), null, null);
    }

    public void delete(@NonNull TableBaseEntry entry) {
        for (ColumnEntry column : mColumnEntryList) {
            if (column.mColumn.primaryKey()) {
                final String selection = getPrimaryKeySelection(column, entry);
                if (!TextUtils.isEmpty(selection)) {
                    delete(selection);
                }
                break;
            }
        }
    }

    @Nullable
    private String getPrimaryKeySelection(@NonNull ColumnEntry columnEntry,
            @NonNull TableBaseEntry entry) {
        try {
            final Object object = columnEntry.mGetMethod.invoke(entry);
            if (object != null) {
                final ColumnType type = getColumnType(columnEntry);
                String value;
                switch (type) {
                    case INTEGER:
                        value = object.toString();
                        break;
                    case TEXT:
                        value = "'" + object.toString() + "'";
                        break;
                    default:
                        throw new RuntimeException();
                }
                return columnEntry.mColumn.name() + "=" + value;
            }
        } catch (IllegalAccessException e) {
            Logger.e(e.toString());
        } catch (InvocationTargetException e) {
            Logger.e(e.toString());
        }
        return null;
    }

    public void delete(@NonNull String where) {
        Logger.d(TAG, "delete:" + where);
        getWritableDatabase().delete(mTableName, where, null);
    }

    public List<T> query() {
        return query(null);
    }

    public List<T> query(@Nullable String where) {
        List<T> result = new ArrayList<>();
        final Cursor cursor = getReadableDatabase()
                .query(mTableName, mColumns, where, null, null, null, null);

        while (cursor.moveToNext()) {
            T t = createNewInstance();
            if (t == null) {
                break;
            }
            for (ColumnEntry entry : mColumnEntryList) {
                try {
                    entry.setValueFromCursor(t, cursor);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    Logger.e(TAG, e.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Logger.e(TAG, e.toString());
                }
            }
            result.add(t);
        }
        cursor.close();
        return result;
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
            sb.append(column.name());
            final ColumnType type = getColumnType(mColumnEntryList.get(i));
            sb.append(" ").append(type.name);
            if (column.primaryKey()) {
                sb.append(" PRIMARY KEY");
            }
            if (i < mColumnEntryList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        Logger.d(sb.toString());
        return sb.toString();
    }

    private ColumnType getColumnType(@NonNull ColumnEntry entry) {
        final ReflectUtils.ValueType type =
                ReflectUtils.getValueType(entry.mField);
        switch (type) {
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                return ColumnType.INTEGER;
            case OBJECT:
            case STRING:
            default:
                return ColumnType.TEXT;
        }
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
            ColumnEntry entry = createColumnEntry(field);
            if (entry != null) {
                columnList.add(entry);
            }
        }
        return columnList;
    }

    @Nullable
    private ColumnEntry createColumnEntry(@NonNull Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column != null) {
            return new ColumnEntry(column, field, ReflectUtils.getSetMethod(field, mClz),
                    ReflectUtils.getGetMethod(field, mClz));
        } else {
            return null;
        }
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

    @NonNull
    private ContentValues getInsertContentValues(@NonNull TableBaseEntry t) {
        ContentValues values = new ContentValues();
        for (ColumnEntry entry : mColumnEntryList) {
            final String name = entry.mColumn.name();
            try {
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
                            values.put(name, (String) entry.mGetMethod.invoke(t));
                            break;
                        case OBJECT:
                            final String objText = JSON.toJSONString(entry.mGetMethod.invoke(t));
                            values.put(name, objText);
                            break;
                        default:
                            Logger.e(TAG, "Unknown type");
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
                            values.put(name, (String) entry.mField.get(t));
                            break;
                        case OBJECT:
                            final String objText = JSON.toJSONString(entry.mField.get(t));
                            values.put(name, objText);
                        default:
                            Logger.e(TAG, "Unknown type");
                            break;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.e(TAG, name + " " + e.toString());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Logger.e(TAG, name + " " + e.toString());
            }
        }
        Logger.d(TAG, values.toString() + " " + mColumnEntryList.toString());
        return values;
    }
}
