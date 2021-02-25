package com.qiu.base.sample.ui;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.qiu.base.lib.keepalive.HeartBeatForegroundService;
import com.qiu.base.lib.tools.Logger;

public class ForegroundService extends HeartBeatForegroundService {

    private static final String TAG = ForegroundService.class.getSimpleName();

    @NonNull
    private final HeartBeatTask mHeartBeatTask = new HeartBeatTask(new Runnable() {
        @Override
        public void run() {
            Logger.d(TAG, "Heart beat count:" + mHeartBeatTask.getBeatCount());
        }
    }, 1234, 1000);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startHeartBeatTask(mHeartBeatTask);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public int getId() {
        return 111;
    }
}
