package com.qiu.base.lib.data.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.thread.ThreadUtils;
import com.qiu.base.lib.tools.UtilTools;
import com.qiu.base.lib.utils.App;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TableStorageImpl {

    private static final String TAG = TableStorageImpl.class.getSimpleName();

    @NonNull
    protected final Map<Class<?>, TableSQLiteOpenHelper<? extends TableBaseEntry>> mSQLiteHelperMap;

    protected TableStorageImpl() {
        mSQLiteHelperMap = new HashMap<>();
    }

    @NonNull
    protected abstract String getDataBaseName();

    protected abstract int getVersion();

    public void insert(@NonNull TableBaseEntry t) {
        getSQLiteHelper(t.getClass()).insert(t);
    }

    public void delete(@NonNull TableBaseEntry t) {
        getSQLiteHelper(t.getClass()).delete(t);
    }

    public void deleteAll(@NonNull Class<? extends TableBaseEntry> clz) {
        getSQLiteHelper(clz).delete("");
    }

    public void update(@NonNull TableBaseEntry t) {
        getSQLiteHelper(t.getClass()).update(t);
    }

    public void query(@NonNull final Callback<List<? extends TableBaseEntry>> callback,
            @NonNull final Class<? extends TableBaseEntry> clz, @Nullable final String where) {
        ThreadUtils.i().postTask(new Runnable() {
            @Override
            public void run() {
                final List<? extends TableBaseEntry> result = getSQLiteHelper(clz).query(where);
                ThreadUtils.i().postMain(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCall(result);
                    }
                });
            }
        });
    }

    public void queryAll(@NonNull final Callback<List<? extends TableBaseEntry>> callback,
            @NonNull final Class<? extends TableBaseEntry> clz) {
        query(callback, clz, null);
    }

    protected void exeSql(@NonNull String sql, @NonNull final Class<? extends TableBaseEntry> clz) {
        getSQLiteHelper(clz).getWritableDatabase().execSQL(sql);
    }

    @NonNull
    protected TableSQLiteOpenHelper<?> getSQLiteHelper(
            @NonNull Class<? extends TableBaseEntry> clz) {
        TableSQLiteOpenHelper<?> helper = mSQLiteHelperMap.get(clz);
        if (helper == null) {
            helper = new TableSQLiteOpenHelper<>(App.i().getApplicationContext(), getDataBaseName(),
                    null, getVersion(), clz);
        }
        mSQLiteHelperMap.put(clz, helper);
        return helper;
    }
}
