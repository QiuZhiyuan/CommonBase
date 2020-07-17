package com.qiu.base.sample.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.UtilTools;
import com.qiu.base.lib.widget.logger.CommonLoggerActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AidlReceiverActivity extends CommonLoggerActivity {

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
        addLog("Pid : " + UtilTools.getPid());
    }

    @Nullable
    @Override
    protected List<BtnEntry> createBtnEntryList() {
        final List<BtnEntry> entryList = new ArrayList<>();
        entryList.add(new BtnEntry("Start AIDL Service", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindAidlService();
            }
        }));
        entryList.add(new BtnEntry("Set Name", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setName("Set name count : " + mSetNameCount++);
            }
        }));
        entryList.add(new BtnEntry("Get Name", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printName();
            }
        }));

        return entryList;
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
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
