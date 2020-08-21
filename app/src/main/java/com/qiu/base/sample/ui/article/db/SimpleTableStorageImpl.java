package com.qiu.base.sample.ui.article.db;

import androidx.annotation.NonNull;

import com.qiu.base.lib.data.db.TableStorageImpl;

public class SimpleTableStorageImpl extends TableStorageImpl<SimpleDbEntry> {

    private static volatile SimpleTableStorageImpl sInstance;

    protected SimpleTableStorageImpl() {
        super(SimpleDbEntry.class);
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
        return "simple.db";
    }

    @Override
    protected int getVersion() {
        return 1;
    }
}
