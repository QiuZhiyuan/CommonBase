package com.qiu.base.lib.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KeyStorage {
    private static final String SP_GROUP = "sp_group";

    private KeyStorage() {
    }

    public static void saveString(@NonNull String key, @NonNull String value) {
        App.i().getSharedPreferences(SP_GROUP).edit().putString(key, value).apply();
    }

    public static void saveInt(@NonNull String key, int value) {
        App.i().getSharedPreferences(SP_GROUP).edit().putInt(key, value).apply();
    }

    @Nullable
    public static String loadString(@NonNull String key, @Nullable String defValue) {
        return App.i().getSharedPreferences(SP_GROUP).getString(key, defValue);
    }

    public static int loadInt(@NonNull String key, int def) {
        return App.i().getSharedPreferences(SP_GROUP).getInt(key, def);
    }
}
