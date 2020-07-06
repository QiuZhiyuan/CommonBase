package com.qiu.base.sample.ui;

import android.os.Bundle;

import com.qiu.base.lib.inter.CallBack;
import com.qiu.base.lib.tools.sys.SystemStateManager;
import com.qiu.base.lib.widget.BaseLoggerActivity;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SystemStateActivity extends BaseLoggerActivity implements
        CallBack<SystemStateManager.NetState> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_state);

        SystemStateManager.i().startListenNetState(this);
        SystemStateManager.i().registerNetStateListener(this);
    }

    @NonNull
    @Override
    protected BaseRecyclerView getLoggerView() {
        return findViewById(R.id.system_state_logger);
    }

    @Override
    protected void onDestroy() {
        SystemStateManager.i().stopListenNetState(this);
        SystemStateManager.i().unregisterNetStateListener(this);
        super.onDestroy();
    }

    @Override
    public void onCall(SystemStateManager.NetState netState) {
        addLog("NetWork state change Name : " + netState.typeName + " type : "
                + netState.type);
    }
}
