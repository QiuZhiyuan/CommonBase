package com.qiu.base.lib.tools;

import android.util.Log;

import androidx.annotation.NonNull;

public class Logger {

    private static final String TAG = "QiuLogger";

    private Logger() {
    }

    public static void d(@NonNull String msg) {
        d(TAG, msg);
    }

    public static void d(@NonNull String tag, @NonNull String msg) {
        Log.d(TAG + " " + tag, msg);
    }

    public static void e(@NonNull String msg) {
        e(TAG, msg);
    }

    public static void e(@NonNull String tag, @NonNull String msg) {
        Log.e(TAG + " " + tag, msg);
    }
}
