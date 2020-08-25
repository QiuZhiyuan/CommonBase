package com.qiu.base.lib.data.db;

import androidx.annotation.NonNull;

import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.tools.UniqueId;

/**
 * Id is unique for every entry subclass which extends TableBaseEntry should create getter and
 * setter for column-field or make column-field public
 */
public abstract class TableBaseEntry {

    public static final String KEY_ID = "id";

    @NonNull
    @Column(name = KEY_ID, primaryKey = true)
    private long mId;

    public TableBaseEntry() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
