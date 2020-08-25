package com.qiu.base.lib.data.db;

import android.content.ContentValues;

import androidx.annotation.NonNull;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.App;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class TableStorageImpl<T extends TableBaseEntry> {

    private static final String TAG = TableStorageImpl.class.getSimpleName();

    @NonNull
    protected final TableSQLiteOpenHelper<T> mTableSQLiteOpenHelper;

    protected TableStorageImpl(@NonNull Class<T> clz) {
        mTableSQLiteOpenHelper =
                new TableSQLiteOpenHelper<>(App.i().getApplicationContext(), getDataBaseName(),
                        null, getVersion(), clz);
    }

    @NonNull
    protected abstract String getDataBaseName();

    protected abstract int getVersion();

    public void insert(T t) {
        try {
            mTableSQLiteOpenHelper.insert(t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            Logger.d(TAG, "insert error:" + e.toString());
        }
    }

    public void delete(T t) {
        delete(t.getId());
    }

    public void delete(long id) {
        mTableSQLiteOpenHelper.delete(T.KEY_ID + "=" + id);
    }

    public void update(T t) {
        try {
            mTableSQLiteOpenHelper.update(T.KEY_ID + "=" + t.getId(), t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            Logger.d(TAG, "update error:" + e.toString());
        }
    }

    public void query(Callback<T> callback, @NonNull ContentValues values) {
        mTableSQLiteOpenHelper.query(callback);
    }

    public void queryAll(Callback<List<T>> callback) {
        mTableSQLiteOpenHelper.queryAll(callback);
    }

}
