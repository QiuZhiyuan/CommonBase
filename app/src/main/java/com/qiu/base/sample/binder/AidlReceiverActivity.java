package com.qiu.base.sample.binder;

import static com.qiu.base.sample.binder.SimpleBinder.REMOTE_CODE;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.UtilTools;
import com.qiu.base.lib.widget.logger.CommonLoggerActivity;

import java.util.ArrayList;
import java.util.List;

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

    @NonNull
    private final ServiceConnection mSimpleBinderConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service == null) {
                return;
            }
            final Parcel data = Parcel.obtain();
            final Parcel reply = Parcel.obtain();
            data.writeInt(10);
            data.writeInt(23);

            try {
                service.transact(REMOTE_CODE, data, reply, 0);
                final int result = reply.readInt();
                addLog("Get remote result:" + result);
            } catch (RemoteException e) {
                e.printStackTrace();
                Logger.e(e.toString());
            }
            data.recycle();
            reply.recycle();

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
        entryList.add(new BtnEntry("Start AIDL Service", v -> bindAidlService()));
        entryList.add(new BtnEntry("Set Name", v -> setName("NameId: " + mSetNameCount++)));
        entryList.add(new BtnEntry("Get Name", v -> printName()));
        entryList.add(new BtnEntry("Get All Name", v -> printNameList()));
        entryList.add(new BtnEntry("Send Simple Binder", v -> bindSimpleBinder()));

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
                addLog("Set name:" + name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindAidlService() {
        addLog("bindAidlService");
//        final Intent intent = new Intent("qiu.aidl.service");
//        intent.setPackage("com.qiu.base.sample");
        bindService(createBinderIntent(AIDLService.AIDL_BINDER), mServiceConnection,
                BIND_AUTO_CREATE);
    }

    private void printName() {
        if (mIPerson != null) {
            try {
                addLog("Get name:" + mIPerson.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
                addLog(e.toString());
            }
        }
    }

    private void printNameList() {
        if (mIPerson != null) {
            try {
                addLog("Get all name:" + mIPerson.getNameList() + " " + mIPerson.getThreadPid());
            } catch (RemoteException e) {
                e.printStackTrace();
                addLog(e.toString());
            }
        }
    }

    @NonNull
    private Intent createBinderIntent(int binderType) {
        final Intent intent = new Intent(this, AIDLService.class);
        intent.putExtra(AIDLService.KEY_BINDER_GET, binderType);
        return intent;
    }

    private void bindSimpleBinder() {
        bindService(createBinderIntent(AIDLService.SIMPLE_BINDER), mSimpleBinderConnection,
                BIND_AUTO_CREATE);
    }
}
