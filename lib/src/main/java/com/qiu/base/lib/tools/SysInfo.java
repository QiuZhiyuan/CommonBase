package com.qiu.base.lib.tools;

import android.os.Build;

public class SysInfo {
    private SysInfo() {
    }

    public static final String MODEL = Build.MODEL;

    public static final String SDK_VERSION = Build.VERSION.SDK;

    public static final String OS_VERSION = Build.VERSION.RELEASE;

    public static final String createInfoString() {
        return "Device Model : " + MODEL + "\n" + "Device sdk version : " + SDK_VERSION + "\n"
                + "Device os version : " + OS_VERSION;
    }
}
