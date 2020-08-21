package com.qiu.base.lib.data.db;

import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.ColumnType;

public abstract class TableBaseEntry {

    public static final String KEY_ID = "id";

    @Column(name = KEY_ID, type = ColumnType.INTEGER, primaryKey = true)
    public long mId;

    public TableBaseEntry() {
    }
}
