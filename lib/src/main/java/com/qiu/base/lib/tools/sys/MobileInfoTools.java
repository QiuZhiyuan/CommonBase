package com.qiu.base.lib.tools.sys;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Catch mobile base info, such as dip/px, system version, and so on.
 */
public class MobileInfoTools {
    private MobileInfoTools() {
    }

    @NonNull
    public static float[] getScreenDpi(@NonNull Context context) {
        float[] result = new float[2];
        result[0] = context.getResources().getDisplayMetrics().xdpi;
        result[1] = context.getResources().getDisplayMetrics().ydpi;
        return result;
    }
}
