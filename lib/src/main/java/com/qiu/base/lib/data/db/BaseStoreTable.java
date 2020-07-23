package com.qiu.base.lib.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseStoreTable {

    public enum ColumnType {
        TEXT("TEXT"),
        INTEGER("INTEGER");
        @NonNull
        public final String mName;

        ColumnType(@NonNull String name) {
            mName = name;
        }
    }

    public static class Column {
        @NonNull
        final String mColumnName;
        @NonNull
        final ColumnType mType;
        final boolean mIsPrimaryKey;

        public Column(@NonNull String columnName, @NonNull ColumnType type, boolean isPrimaryKey) {
            mColumnName = columnName;
            mType = type;
            mIsPrimaryKey = isPrimaryKey;
        }

        public Column(@NonNull String columnName, @NonNull ColumnType type) {
            this(columnName, type, false);
        }
    }

    public static class SearchClause {
        @NonNull
        private final Map<String, Object> mMap;

        public SearchClause() {
            mMap = new HashMap<>();
        }

        public void setMap(@NonNull String key, @NonNull Object value) {
            mMap.put(key, value);
        }

        @NonNull
        public String getSearchClausePart() {
            final StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : mMap.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
            }
            return sb.toString();
        }
    }

    @NonNull
    private final List<Column> mColumnList;
    @Nullable
    private final BaseStoreImpl mStoreImpl;

    public BaseStoreTable() {
        mStoreImpl = createBaseStoreImpl();
        mColumnList = createColumnList();
    }

    @NonNull
    String getCreateTableSql() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXITS ").append(getTableName());
        sb.append(" (");
        boolean hasPrimaryKey = false;
        for (Column column : createColumnList()) {
            sb.append(column.mColumnName).append(" ").append(column.mType.mName);
            if (column.mIsPrimaryKey) {
                if (hasPrimaryKey) {
                    throw new RuntimeException("Only one primary key in every table");
                }
                hasPrimaryKey = true;
                sb.append(" PRIMARY KEY");
            }
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @NonNull
    String getDeleteTableSql() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    @NonNull
    protected abstract String getTableName();

    @NonNull
    protected abstract List<Column> createColumnList();

    @NonNull
    protected abstract BaseStoreImpl createBaseStoreImpl();

    protected void insert(@NonNull ContentValues contentValues) {
        if (mStoreImpl == null) {
            return;
        }
        final SQLiteDatabase db = mStoreImpl.getWritableDatabase();
        db.insert(getTableName(), null, contentValues);
    }

    protected void update(@NonNull ContentValues contentValues,
            @NonNull SearchClause searchClause) {
        if (mStoreImpl == null) {
            return;
        }
        final SQLiteDatabase db = mStoreImpl.getWritableDatabase();
        db.update(getTableName(), contentValues, searchClause.getSearchClausePart(), null);
    }

    protected void delete(@NonNull SearchClause searchClause) {
        if (mStoreImpl == null) {
            return;
        }
        final SQLiteDatabase db = mStoreImpl.getWritableDatabase();
        db.delete(getTableName(), searchClause.getSearchClausePart(), null);
    }

    @Nullable
    protected Cursor queryAll() {
        if (mStoreImpl == null) {
            return null;
        }
        final SQLiteDatabase db = mStoreImpl.getReadableDatabase();

        return db.query(getTableName(), getColumnNames(), null, null, null, null, null);
    }

    private String[] getColumnNames() {
        final String[] columns = new String[mColumnList.size()];
        for (int i = 0; i < mColumnList.size(); i++) {
            columns[i] = mColumnList.get(i).mColumnName;
        }
        return columns;
    }
}
