package com.qiu.base.sample.db;

import androidx.annotation.NonNull;

public class CompanyEntry {

    private String mName;

    @NonNull
    @Override
    public String toString() {
        return "{" + getClass().getSimpleName() + " name:" + getName() + "}";
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
