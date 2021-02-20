package com.qiu.base.lib.utils;

import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.qiu.base.lib.tools.Logger;

public class App {

    private static volatile App sInstance;

    public static App i() {
        if (sInstance == null) {
            synchronized (App.class) {
                if (sInstance == null) {
                    sInstance = new App();
                }
            }
        }
        return sInstance;
    }

    private App() {
    }

    private Application mApplication;
    @NonNull
    private final ActivityManager mActivityManager = new ActivityManager();

    public void init(@NonNull Application application) {
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(mActivityManager);
    }

    @NonNull
    public Context getApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @NonNull
    public String getPackageName() {
        return mApplication.getApplicationContext().getPackageName();
    }

    @NonNull
    public Resources getResources() {
        return mApplication.getResources();
    }

    @NonNull
    public String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    @NonNull
    public String getString(@StringRes int id, @Nullable Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }

    @NonNull
    public PackageManager getPackageManager() {
        return mApplication.getPackageManager();
    }

    public SharedPreferences getSharedPreferences(@NonNull String name) {
        return mApplication.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Nullable
    public Object getSystemService(@NonNull String name) {
        return mApplication.getSystemService(name);
    }

    public boolean checkPermission(@NonNull String permission) {
        final int result = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Nullable
    public Context getContextByPackageName(@NonNull String packageName) {
        Context context = null;
        try {
            context = mApplication.createPackageContext(packageName,
                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("getContextByPackageName");
        }
        return context;
    }

    public void startActivity(@NonNull String path) {
        final Intent intent = new Intent(path);
        startActivity(intent);
    }

    public void startActivity(@NonNull Intent intent) {
        mActivityManager.startActivity(intent);
    }
}
