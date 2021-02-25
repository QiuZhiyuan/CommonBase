package com.qiu.base.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.qiu.base.lib.keepalive.HeartBeatForegroundService;
import com.qiu.base.lib.keepalive.OnePixelActivity;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KeepAliveActivity extends Activity {
//
//    protected static class HeartBeatTask {
//        @NonNull
//        public final Runnable runnable;
//        public final int taskId;
//        public final int delay;
//        private long mBeatCount = 0;
//
//        public HeartBeatTask(@NonNull Runnable runnable, int taskId, int delay) {
//            this.runnable = runnable;
//            this.taskId = taskId;
//            this.delay = delay;
//        }
//
//        public void increaseCount() {
//            mBeatCount++;
//        }
//
//        public long getBeatCount() {
//            return mBeatCount;
//        }
//
//    }
//
//    protected static class HeartBeatHandler extends Handler {
//
//        private static final String TAG = HeartBeatHandler.class.getSimpleName();
//
//        public HeartBeatHandler() {
//            super();
//        }
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
////            Logger.d(TAG, "handleMessage:" + msg.what);
//            HeartBeatTask task = null;
//            if (msg.obj instanceof HeartBeatTask) {
//                task = (HeartBeatTask) msg.obj;
//            }
//            if (task != null) {
//                task.runnable.run();
//                task.increaseCount();
////                Logger.d(TAG, "Task:" + task.taskId + " beat count:" + task.getBeatCount());
//                sendHeartBeatTask(task, task.delay);
//            } else {
//                Logger.e(TAG, "task = null");
//            }
//        }
//
//        public void sendHeartBeatTask(@NonNull HeartBeatTask task, int delay) {
////            Logger.d(TAG, "sendHeartBeatTask");
//            final Message msg = obtainMessage();
//            msg.what = task.taskId;
//            msg.obj = task;
//            sendMessageDelayed(msg, delay);
//        }
//
//        public void sendHeartBeatTask(@NonNull HeartBeatTask task) {
//            if (!hasMessages(task.taskId)) {
//                sendHeartBeatTask(task, task.delay);
//            } else {
//                Logger.e(TAG, "Already run this task:" + task.taskId);
//            }
//        }
//
//        public void stopHeartBeatTask(int taskId) {
//            removeMessages(taskId);
//        }
//    }
//
//    private final HeartBeatHandler mHeartBeatHandler = new HeartBeatHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_alive);
//        mHeartBeatHandler.sendHeartBeatTask(new HeartBeatTask(new Runnable() {
//            @Override
//            public void run() {
//                Logger.d("qiuzhiyuan", "heart beat");
//            }
//        }, 1021, 1000));
    }

    public void startForegroundService(View view) {
        startService(new Intent(this, ForegroundService.class));
    }
}
