package com.qiu.base.sample.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.data.db.TableBaseEntry;
import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.Table;

@Table(name = "simple_entry")
public class SimpleDbEntry extends TableBaseEntry {

    @Nullable
    @Column(name = "name")
    private String mName;
    @Column(name = "age")
    private int mAge;

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
        return SimpleDbEntry.class.getSimpleName() + " id:" + getId() + " name:" + mName + " age:"
                + mAge;
    }

    @Nullable
    public String getName() {
        return mName;
    }

    public void setName(@Nullable String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }
}
