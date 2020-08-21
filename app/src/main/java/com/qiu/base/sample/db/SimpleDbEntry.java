package com.qiu.base.sample.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.data.db.TableBaseEntry;
import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.ColumnType;
import com.qiu.base.lib.data.db.anno.Table;

@Table(name = "simple_entry")
public class SimpleDbEntry extends TableBaseEntry {

    @Nullable
    @Column(name = "name", type = ColumnType.TEXT)
    public String mName;
    @Column(name = "age", type = ColumnType.INTEGER)
    public int mAge;

    public SimpleDbEntry() {
        super();
    }

    public SimpleDbEntry(@NonNull String name, int age) {
        super();
        mName = name;
        mAge = age;
    }

    @NonNull
    @Override
    public String toString() {
        return SimpleDbEntry.class.getSimpleName() + " id:" + mId + " name:" + mName + " age:"
                + mAge;
    }
}
