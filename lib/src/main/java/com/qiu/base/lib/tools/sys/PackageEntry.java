package com.qiu.base.lib.tools.sys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.qiu.base.lib.utils.App;

public class PackageEntry {

    @NonNull
    private final PackageInfo mPackageInfo;

    public PackageEntry(@NonNull PackageInfo packageInfo) {
        mPackageInfo = packageInfo;
    }

    @NonNull
    private PackageManager getPackageManager() {
        return App.i().getPackageManager();
    }

    @NonNull
    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    @NonNull
    public String getAppName() {
        return mPackageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
    }

    @NonNull
    public String getPackageName() {
        return mPackageInfo.packageName;
    }

    @NonNull
    public Drawable getIcon() {
        return mPackageInfo.applicationInfo.loadIcon(getPackageManager());
    }
}
