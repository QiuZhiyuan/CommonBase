package com.qiu.base.lib.data.db.anno;

import androidx.annotation.NonNull;

public enum ColumnType {
    INTEGER("INTEGER"),
    TEXT("TEXT"),
    ;
    @NonNull
    public final String value;

    ColumnType(@NonNull String value) {
        this.value = value;
    }
}
