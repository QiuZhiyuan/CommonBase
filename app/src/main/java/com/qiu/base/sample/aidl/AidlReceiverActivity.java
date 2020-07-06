package com.qiu.base.sample.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.UtilTools;
import com.qiu.base.lib.widget.BaseLoggerActivity;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AidlReceiverActivity extends BaseLoggerActivity implements View.OnClickListener {

    @NonNull
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            addLog("onServiceConnected");
            mIPerson = IPerson.Stub.asInterface(service);
            printName();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Nullable
    private IPerson mIPerson;
    private int mSetNameCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        LinearLayout mButtonContainer = findViewById(R.id.index_button_container);
        for (int i = 0; i < mButtonContainer.getChildCount(); i++) {
            View view = mButtonContainer.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
        addLog("Pid : " + UtilTools.getPid());
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    @NonNull
    @Override
    protected BaseRecyclerView getLoggerView() {
        return findViewById(R.id.aidl_logger);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_aidl_service:
                bindAidlService();
                break;
            case R.id.set_name:
                setName("Set name count : " + mSetNameCount++);
                break;
            case R.id.get_name:
                printName();
            default:
                break;
        }
    }

    private void setName(@NonNull String name) {
        if (mIPerson != null) {
            try {
                mIPerson.setName(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindAidlService() {
        addLog("bindAidlService");
        final Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void printName() {
        if (mIPerson != null) {
            try {
                addLog(mIPerson.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
                addLog(e.toString());
            }
        }
    }
}
