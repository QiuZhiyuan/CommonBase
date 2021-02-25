package com.qiu.base.lib.keepalive;

import android.app.Notification;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.qiu.base.lib.R;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.App;

public abstract class HeartBeatForegroundService extends AbsForegroundService {

    private static final String TAG = HeartBeatForegroundService.class.getSimpleName();

    protected static class HeartBeatTask {
        @NonNull
        public final Runnable runnable;
        public final int taskId;
        public final int delay;
        private long mBeatCount = 0;

        public HeartBeatTask(@NonNull Runnable runnable, int taskId, int delay) {
            this.runnable = runnable;
            this.taskId = taskId;
            this.delay = delay;
        }

        public void increaseCount() {
            mBeatCount++;
        }

        public long getBeatCount() {
            return mBeatCount;
        }

    }

    protected static class HeartBeatHandler extends Handler {

        private static final String TAG = HeartBeatHandler.class.getSimpleName();

        public HeartBeatHandler() {
            super();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
//            Logger.d(TAG, "handleMessage:" + msg.what);
            HeartBeatTask task = null;
            if (msg.obj instanceof HeartBeatTask) {
                task = (HeartBeatTask) msg.obj;
            }
            if (task != null) {
                task.runnable.run();
                task.increaseCount();
//                Logger.d(TAG, "Task:" + task.taskId + " beat count:" + task.getBeatCount());
                sendHeartBeatTask(task, task.delay);
            } else {
                Logger.e(TAG, "task = null");
            }
        }

        public void sendHeartBeatTask(@NonNull HeartBeatTask task, int delay) {
//            Logger.d(TAG, "sendHeartBeatTask");
            final Message msg = obtainMessage();
            msg.what = task.taskId;
            msg.obj = task;
            sendMessageDelayed(msg, delay);
        }

        public void sendHeartBeatTask(@NonNull HeartBeatTask task) {
            if (!hasMessages(task.taskId)) {
                sendHeartBeatTask(task, task.delay);
            } else {
                Logger.e(TAG, "Already run this task:" + task.taskId);
            }
        }

        public void stopHeartBeatTask(int taskId) {
            removeMessages(taskId);
        }
    }

    private final HeartBeatHandler mHeartBeatHandler = new HeartBeatHandler();

    @Override
    protected Notification buildForegroundNotification() {
        final Notification.Builder builder =
                new Notification.Builder(App.i().getApplicationContext());
        builder.setLargeIcon(BitmapFactory
                .decodeResource(App.i().getResources(), R.drawable.ic_simple_cricle));
        builder.setSmallIcon(R.drawable.ic_simple_cricle);
        builder.setContentTitle("HeartBeat");
        builder.setContentText("Hello World");
        builder.setWhen(System.currentTimeMillis());
        final Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        return notification;
    }


    public void startHeartBeatTask(@NonNull HeartBeatTask task) {
        Logger.d(TAG, "startHeartBeatTask");
        mHeartBeatHandler.sendHeartBeatTask(task);
    }

    public void stopHeartBeatTask(int taskId) {
        Logger.d(TAG, "stopHeartBeatTask");
        mHeartBeatHandler.stopHeartBeatTask(taskId);
    }

    public void stopHeartBeatTask(@NonNull HeartBeatTask task) {
        stopHeartBeatTask(task.taskId);
    }
}
