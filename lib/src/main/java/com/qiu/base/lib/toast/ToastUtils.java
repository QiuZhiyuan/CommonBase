package com.qiu.base.lib.toast;

import android.content.Context;
import android.widget.Toast;

import com.qiu.base.lib.thread.ThreadUtils;
import com.qiu.base.lib.utils.App;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;

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

    public static void show(@NonNull final String msg) {
        show(App.i().getApplicationContext(), msg);
    }

    public static void show(@StringRes int id) {
        show(App.i().getResources().getString(id));
    }
}
