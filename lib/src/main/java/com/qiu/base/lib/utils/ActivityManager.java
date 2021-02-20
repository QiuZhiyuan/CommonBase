package com.qiu.base.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.Logger;

import java.lang.ref.WeakReference;

public class ActivityManager implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = ActivityManager.class.getSimpleName();
    @Nullable
    private WeakReference<Activity> mActiveActivity;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        mActiveActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (getActiveActivity() == activity) {
            mActiveActivity = null;
        }
    }

    @Nullable
    private Activity getActiveActivity() {
        final Activity activity;
        if (mActiveActivity != null) {
            activity = mActiveActivity.get();
        } else {
            activity = null;
        }
        if (activity == null) {

            Logger.e(TAG, "Get active activity is null");
        }
        return activity;
    }

    public void startActivity(@NonNull Intent intent) {
        final Activity activity = getActiveActivity();
        if (activity != null) {
            activity.startActivity(intent);
        }
    }
}
