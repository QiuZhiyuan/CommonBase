package com.qiu.base.lib.keepalive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.qiu.base.lib.toast.ToastUtils;
import com.qiu.base.lib.tools.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 还没开发完，现在没啥用
 */
public class OnePixelActivity extends Activity {

    @NonNull
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Window window = getWindow();
        window.setGravity(Gravity.START | Gravity.TOP);
        final WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 1;
        window.setAttributes(params);

        registerReceiver(mBroadcastReceiver, new IntentFilter("finish activity"));
        checkScreenOn();

        ToastUtils.show(this, "OnePixelActivity create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkScreenOn();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            Logger.d(e.toString());
        }
        super.onDestroy();
    }

    private void checkScreenOn() {
        boolean isScreenOn = false;
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                isScreenOn = pm.isInteractive();
            } else {
                isScreenOn = pm.isScreenOn();
            }
        }
        if (isScreenOn) {
            finish();
        }
    }
}
