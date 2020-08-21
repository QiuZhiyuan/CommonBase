package com.qiu.base.lib.utils;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

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
}
