package com.qiu.base.lib.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

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
    public PackageManager getPackageManager() {
        return mApplication.getPackageManager();
    }

}
