package com.qiu.base.lib.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.impl.Callback;

public class ThreadUtils {

    private static final int MSG_POST_SINGLE_THREAD = 100;
    private static final int MSG_POST_ASYNC_THREAD = 101;

    @Nullable
    private volatile static ThreadUtils sInstance;

    public static ThreadUtils i() {
        if (sInstance == null) {
            synchronized (ThreadUtils.class) {
                if (sInstance == null) {
                    sInstance = new ThreadUtils();
                }
            }
        }
        return sInstance;
    }

    public static void release() {
        sInstance = null;
    }

    private static class MainHandler extends Handler {

        MainHandler() {
            super(Looper.getMainLooper());
        }

        @NonNull
        private final ExecutorService mCachedThreadExecutor = Executors.newCachedThreadPool();
        @NonNull
        private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_POST_ASYNC_THREAD:
                    final Runnable taskCache = (Runnable) msg.obj;
                    mCachedThreadExecutor.submit(taskCache);
                    break;
                case MSG_POST_SINGLE_THREAD:
                    final Runnable taskSingle = (Runnable) msg.obj;
                    mSingleThreadExecutor.submit(taskSingle);
                    break;
                default:
                    break;
            }
        }

        void postAsync(@NonNull Runnable runnable, long delay) {
            Message msg = obtainMessage();
            msg.what = MSG_POST_ASYNC_THREAD;
            msg.obj = runnable;
            sendMessageDelayed(msg, delay);
        }

        void postSingleAsync(@NonNull Runnable runnable, long delay) {
            Message msg = obtainMessage();
            msg.what = MSG_POST_SINGLE_THREAD;
            msg.obj = runnable;
            sendMessageDelayed(msg, delay);
        }
    }

    private static class ScheduleHandler extends Handler {

        private static class ScheduleEntry {
            final int id;
            @NonNull
            final Runnable task;
            final long duration;

            private ScheduleEntry(int id, @NonNull Runnable task, long duration) {
                this.id = id;
                this.task = task;
                this.duration = duration;
            }
        }

        private Set<ScheduleEntry> mScheduleEntrySet = new HashSet<>();

        ScheduleHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            removeMessages(msg.what);
            if (msg.obj instanceof ScheduleEntry) {
                final ScheduleEntry entry = (ScheduleEntry) msg.obj;
                if (mScheduleEntrySet.contains(entry)) {
                    entry.task.run();
                    sendSchedule(entry);
                }
            } else {
                throw new RuntimeException("Only send scheduleEntry");
            }
        }

        private void sendSchedule(@NonNull ScheduleEntry entry) {
            final Message msg = obtainMessage();
            msg.what = entry.id;
            msg.obj = entry;
            sendMessageDelayed(msg, entry.duration);
        }

        public void postNewSchedule(int scheduleId, @NonNull Runnable runnable, long duration) {
            cancelSchedule(scheduleId);
            final ScheduleEntry entry = new ScheduleEntry(scheduleId, runnable, duration);
            mScheduleEntrySet.add(entry);
            sendSchedule(entry);

        }

        public void cancelSchedule(int scheduleId) {
            removeMessages(scheduleId);
            for (ScheduleEntry entry : mScheduleEntrySet) {
                if (entry.id == scheduleId) {
                    mScheduleEntrySet.remove(entry);
                    break;
                }
            }
        }
    }

    @NonNull
    private final MainHandler mMainHandler = new MainHandler();
    @NonNull
    private final ScheduleHandler mScheduleHandler = new ScheduleHandler();

    private ThreadUtils() {
    }

    public void postMain(@NonNull Runnable runnable) {
        postTask(runnable, 0, false);
    }

    public void postTask(@NonNull Runnable runnable) {
        postTask(runnable, 0, true);
    }

    public void postTask(@NonNull Runnable runnable, long delay, boolean isAsync) {
        postTask(runnable, delay, isAsync, false);
    }

    public void postTask(@NonNull Runnable runnable, long delay, boolean isAsync,
            boolean isSingle) {
        if (isAsync) {
            if (isSingle) {
                mMainHandler.postSingleAsync(runnable, delay);
            } else {
                mMainHandler.postAsync(runnable, delay);
            }
        } else {
            mMainHandler.postDelayed(runnable, delay);
        }
    }

    public void postSchedule(int id, @NonNull Runnable runnable, long duration) {
        mScheduleHandler.postNewSchedule(id, runnable, duration);
    }

    public void cancelSchedule(int id) {
        mScheduleHandler.cancelSchedule(id);
    }
}
