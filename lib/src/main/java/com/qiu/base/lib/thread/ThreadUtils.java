package com.qiu.base.lib.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.qiu.base.lib.tools.UtilTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @NonNull
    private final MainHandler mMainHandler = new MainHandler();

    private ThreadUtils() {
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
}
