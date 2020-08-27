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
import java.util.ArrayList;
import java.util.List;

public class TableSQLiteOpenHelper<T extends TableBaseEntry> extends SQLiteOpenHelper {

    private static final String TAG = TableSQLiteOpenHelper.class.getSimpleName();

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

    public void insert(@NonNull T t) {
        long id = getWritableDatabase().insert(mTableName, null, getInsertContentValues(t));
        if (id != -1) {
            t.setId(id);
        }
    }

    public void update(@NonNull String where, @NonNull T t) {
        getWritableDatabase().update(mTableName, getInsertContentValues(t), where, null);
    }

    public void delete(@NonNull String where) {
        getWritableDatabase().delete(mTableName, where, null);
    }

    // TODO Query elements by some case
    public void query(Callback<T> callback) {
    }

    public void queryAll(@NonNull Callback<List<T>> callback) {
        List<T> result = new ArrayList<>();
        final Cursor cursor = getReadableDatabase()
                .query(mTableName, mColumns, null, null, null, null, null);

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
            sb.append(column.name());
            final ReflectUtils.ValueType type =
                    ReflectUtils.getValueType(mColumnEntryList.get(i).mField);
            if (type == null) {
                continue;
            }
            switch (type) {
                case SHORT:
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                    sb.append(" INTEGER");
                    break;
                case STRING:
                    sb.append(" TEXT");
                    break;
                default:
                    throw new RuntimeException(TAG + " Unknown field type");
            }
            if (column.primaryKey()) {
                sb.append(" PRIMARY KEY AUTOINCREMENT");
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
            ColumnEntry entry = createColumnEntry(field);
            if (entry != null) {
                columnList.add(entry);
            }
        }
        return columnList;
    }

    @Nullable
    private ColumnEntry createColumnEntry(@NonNull Field field) {
        Column column = field.getAnnotation(Column.class);
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

    private ContentValues getInsertContentValues(T t) {
        ContentValues values = new ContentValues();
        for (ColumnEntry entry : mColumnEntryList) {
            if (entry.mColumn.primaryKey()) {
                continue;
            }
            final String name = entry.mColumn.name();
            try {
                if (entry.mGetMethod != null) {
                    final ReflectUtils.ValueType type = ReflectUtils.getValueType(entry.mGetMethod);
                    if (type == null) {
                        continue;
                    }
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
                    if (type == null) {
                        continue;
                    }
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
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.e(TAG, name + " " + e.toString());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Logger.e(TAG, name + " " + e.toString());
            }

        }
        return values;
    }
}
