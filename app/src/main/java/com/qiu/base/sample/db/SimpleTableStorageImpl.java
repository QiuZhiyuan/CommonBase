package com.qiu.base.sample.db;

import androidx.annotation.NonNull;

import com.qiu.base.lib.data.db.TableStorageImpl;

public class SimpleTableStorageImpl extends TableStorageImpl<SimplePersonEntry> {

    private static final String DATA_BASE_NAME = "simple.db";
    private static final int VERSION = 2;

    private static volatile SimpleTableStorageImpl sInstance;

    protected SimpleTableStorageImpl() {
        super(SimplePersonEntry.class);
    }

    public static SimpleTableStorageImpl instance() {
        if (sInstance == null) {
            synchronized (SimpleTableStorageImpl.class) {
                if (sInstance == null) {
                    sInstance = new SimpleTableStorageImpl();
                }
            }
        }
        return sInstance;
    }

    @NonNull
    @Override
    protected String getDataBaseName() {
        return DATA_BASE_NAME;
    }

    @Override
    protected int getVersion() {
        return VERSION;
    }
}
