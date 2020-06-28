package com.qiu.base.lib.toast;

import android.content.Context;
import android.widget.Toast;

import com.qiu.base.lib.thread.ThreadUtils;

import androidx.annotation.NonNull;

public class ToastUtils {
    private ToastUtils() {

    }

    public static void show(@NonNull final Context context, @NonNull final String msg) {
        ThreadUtils.i().postTask(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }, 0, false);
    }
}
