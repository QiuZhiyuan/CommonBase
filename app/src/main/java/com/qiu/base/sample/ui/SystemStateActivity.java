package com.qiu.base.sample.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.tools.sys.SystemStateManager;
import com.qiu.base.lib.widget.logger.CommonLoggerActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SystemStateActivity extends CommonLoggerActivity implements
        Callback<SystemStateManager.NetState> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startListenNetworkChange();
        showScreenParams();
    }

    @Nullable
    @Override
    protected List<BtnEntry> createBtnEntryList() {
        return null;
    }

    @Override
    protected void onDestroy() {
        SystemStateManager.i().stopListenNetState(this);
        SystemStateManager.i().unregisterNetStateListener(this);
        super.onDestroy();
    }

    private void startListenNetworkChange() {
        SystemStateManager.i().startListenNetState(this);
        SystemStateManager.i().registerNetStateListener(this);
    }

    private void showScreenParams() {
        addLog("show screen params:");
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        addLog("xdpi: " + metrics.xdpi);
        addLog("ydpi: " + metrics.ydpi);
        addLog("densityDpi: " + metrics.densityDpi);
        addLog("widthPixels: " + metrics.widthPixels);
        addLog("heightPixels: " + metrics.heightPixels);
        addLog("density:" + metrics.density);
    }

    @Override
    public void onCall(SystemStateManager.NetState netState) {
        addLog("NetWork state change Name : " + netState.typeName + " type : "
                + netState.type);
    }
}
