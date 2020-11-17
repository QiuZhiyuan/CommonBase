package com.qiu.base.lib.data.db;

import androidx.annotation.NonNull;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.thread.ThreadUtils;
import com.qiu.base.lib.utils.App;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TableStorageImpl{

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
        getSQLiteHelper(t.getClass()).delete(TableBaseEntry.KEY_ID + "=" + t.getId());
    }

    public void deleteAll(@NonNull Class<? extends TableBaseEntry> clz) {
        getSQLiteHelper(clz).delete("");
    }

    public void update(@NonNull TableBaseEntry t) {
        getSQLiteHelper(t.getClass()).update(TableBaseEntry.KEY_ID + "=" + t.getId(), t);
    }

    public void queryAll(@NonNull final Callback<List<? extends TableBaseEntry>> callback,
            @NonNull final Class<? extends TableBaseEntry> clz) {
        ThreadUtils.i().postTask(new Runnable() {
            @Override
            public void run() {
                final List<? extends TableBaseEntry> result = getSQLiteHelper(clz).queryAll();
                ThreadUtils.i().postMain(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCall(result);
                    }
                });
            }
        });
    }

    @NonNull
    private TableSQLiteOpenHelper<?> getSQLiteHelper(@NonNull Class<? extends TableBaseEntry> clz) {
        TableSQLiteOpenHelper<?> helper = mSQLiteHelperMap.get(clz);
        if (helper == null) {
            helper = new TableSQLiteOpenHelper<>(App.i().getApplicationContext(), getDataBaseName(),
                    null, getVersion(), clz);
        }
        mSQLiteHelperMap.put(clz, helper);
        return helper;
    }
}
