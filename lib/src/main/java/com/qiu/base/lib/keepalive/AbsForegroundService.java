package com.qiu.base.lib.keepalive;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.Logger;

public abstract class AbsForegroundService extends Service {

    private static final String TAG = AbsForegroundService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate()");
        Logger.d(TAG, "onStartCommand");
        final Notification notification = buildForegroundNotification();
        startForeground(getId(), notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(TAG, "onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    protected abstract Notification buildForegroundNotification();

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public abstract int getId();
}
