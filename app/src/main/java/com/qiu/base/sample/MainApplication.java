package com.qiu.base.sample;

import android.app.Application;

import com.qiu.base.lib.utils.App;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        App.i().init(this);
    }
}
