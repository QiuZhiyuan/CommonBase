package com.qiu.base.lib.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseStoreImpl {

    private class StoreSQLiteOpenHelper extends SQLiteOpenHelper {


        public StoreSQLiteOpenHelper(@Nullable Context context,
                @Nullable String name,
                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (BaseStoreTable table : mTableList) {
                db.execSQL(table.getCreateTableSql());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (BaseStoreTable table : mTableList) {
                db.execSQL(table.getDeleteTableSql());
            }
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    @NonNull
    private StoreSQLiteOpenHelper mStoreSQLiteOpenHelper;
    @NonNull
    private final List<BaseStoreTable> mTableList = new ArrayList<>();

    public BaseStoreImpl() {
    }

    @NonNull
    public SQLiteDatabase getReadableDatabase() {
        return mStoreSQLiteOpenHelper.getReadableDatabase();
    }

    @NonNull
    public SQLiteDatabase getWritableDatabase() {
        return mStoreSQLiteOpenHelper.getWritableDatabase();
    }

    public void createDatabase(@NonNull Context context, @NonNull List<BaseStoreTable> tableList) {
        mTableList.clear();
        mTableList.addAll(tableList);
        mStoreSQLiteOpenHelper =
                new StoreSQLiteOpenHelper(context, getDataBaseName(), null, getVersion());
    }


    @NonNull
    protected abstract String getDataBaseName();

    protected abstract int getVersion();
}
