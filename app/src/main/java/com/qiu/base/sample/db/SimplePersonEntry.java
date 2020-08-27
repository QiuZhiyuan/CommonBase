package com.qiu.base.sample.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.data.db.TableBaseEntry;
import com.qiu.base.lib.data.db.anno.Column;
import com.qiu.base.lib.data.db.anno.Table;

@Table(name = "simple_entry")
public class SimplePersonEntry extends TableBaseEntry {

    @Nullable
    @Column(name = "name")
    private String mName;
    @Column(name = "age")
    private int mAge;
    @Nullable
    @Column(name = "company")
    private CompanyEntry mCompany;

    public SimplePersonEntry() {
        super();
    }

    public SimplePersonEntry(@NonNull String name, int age) {
        super();
        mName = name;
        mAge = age;
    }

    @NonNull
    @Override
    public String toString() {
        String companyStr = null;
        if (getCompany() != null) {
            companyStr = getCompany().toString();
        }
        return "{" + SimplePersonEntry.class.getSimpleName() + " id:" + getId() + " name:" + mName
                + " age:" + mAge + " company:" + companyStr + "}";
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

    @Nullable
    public CompanyEntry getCompany() {
        return mCompany;
    }

    public void setCompany(@Nullable CompanyEntry company) {
        mCompany = company;
    }
}
