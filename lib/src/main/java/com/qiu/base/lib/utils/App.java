package com.qiu.base.lib.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.Logger;

import java.security.Permission;

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

    public void init(@NonNull Application application) {
        mApplication = application;
    }

    @NonNull
    public Context getApplicationContext() {
        return mApplication.getApplicationContext();
    }

    @NonNull
    public Resources getResources() {
        return mApplication.getResources();
    }

    @NonNull
    public PackageManager getPackageManager() {
        return mApplication.getPackageManager();
    }

    public SharedPreferences getSharedPreferences(@NonNull String name) {
        return mApplication.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Nullable
    public Object getSystemService(@NonNull String name){
        return mApplication.getSystemService(name);
    }

    @Nullable
    public Context getContextByPackageName(@NonNull String packageName) {
        Context context = null;
        try {
            context = mApplication.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("getContextByPackageName");
        }
        return context;
    }

}
