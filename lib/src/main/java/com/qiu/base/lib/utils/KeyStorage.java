package com.qiu.base.lib.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KeyStorage {
    public static final String SP_GROUP = "sp_group";

    private KeyStorage() {
    }

    public static void saveString(@NonNull String key, @NonNull String value) {
        App.i().getSharedPreferences(SP_GROUP).edit().putString(key, value).apply();
    }

    @Nullable
    public static String loadString(@NonNull String key, @Nullable String defValue) {
        return App.i().getSharedPreferences(SP_GROUP).getString(key, defValue);
    }
}
