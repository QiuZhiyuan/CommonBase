package com.qiu.base.lib.tools;

import android.util.Log;

import androidx.annotation.NonNull;

public class Logger {

    private static final String LOG_TAG = "QiuLogger";

    private Logger() {
    }

    public static void d(@NonNull String msg) {
        d(LOG_TAG, msg);
    }

    public static void d(@NonNull String tag, @NonNull String msg) {
        Log.d(tag, msg);
    }
}
