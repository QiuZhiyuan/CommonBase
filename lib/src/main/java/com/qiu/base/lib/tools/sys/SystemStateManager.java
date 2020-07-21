package com.qiu.base.lib.tools.sys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.tools.UtilTools;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class SystemStateManager {

    private volatile static SystemStateManager sInstance;

    public static SystemStateManager i() {
        if (sInstance == null) {
            synchronized (SystemStateManager.class) {
                if (sInstance == null) {
                    sInstance = new SystemStateManager();
                }
            }
        }
        return sInstance;
    }

    public static class NetState {
        public final int type;
        @NonNull
        public final String typeName;

        public NetState(int type, @NonNull String typeName) {
            this.type = type;
            this.typeName = typeName;
        }
    }

    @NonNull
    private final BroadcastReceiver mNetStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (UtilTools.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {
                final ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm != null) {
                    final NetworkInfo ni = cm.getActiveNetworkInfo();
                    if (ni != null && ni.isAvailable()) {
                        final int type = ni.getType();
                        final String typeName = ni.getTypeName();
                        onNetStateChanged(type, typeName);
                    }
                }
            }
        }
    };

    @NonNull
    private final List<Callback<NetState>> mNetStateCallback = new ArrayList<>();

    private SystemStateManager() {

    }

    public void startListenNetState(@NonNull Context context) {

        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(mNetStateReceiver, timeFilter);
    }

    public void stopListenNetState(@NonNull Context context) {
        context.unregisterReceiver(mNetStateReceiver);
    }

    public void registerNetStateListener(@NonNull Callback<NetState> callback) {
        mNetStateCallback.add(callback);
    }

    public void unregisterNetStateListener(@NonNull Callback<NetState> callback) {
        mNetStateCallback.remove(callback);
    }

    private void onNetStateChanged(int type, @NonNull String typeName) {
        for (Callback<NetState> callBack : mNetStateCallback) {
            callBack.onCall(new NetState(type, typeName));
        }
    }
}
