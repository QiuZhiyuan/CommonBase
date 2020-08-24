package com.qiu.base.lib.data.db;

import androidx.annotation.NonNull;

import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.ColumnType;
import com.qiu.base.lib.tools.UniqueId;

/**
 * id is unique for every entry
 * Subclass which extends TableBaseEntry should create getter and setter for column-field
 * or make column-field public
 */
public abstract class TableBaseEntry {

    public static final String KEY_ID = "id";

    @NonNull
    @Column(name = KEY_ID, type = ColumnType.INTEGER, primaryKey = true)
    private long mId;

    public TableBaseEntry() {
        mId = UniqueId.get();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
