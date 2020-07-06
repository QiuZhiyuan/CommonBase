package com.qiu.base.sample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;


import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.UtilTools;

import androidx.annotation.Nullable;

public class AIDLService extends Service {

    private String name = "qiuzhiyuan";

    private Binder binder = new IPerson.Stub() {
        @Override
        public void setName(String s) {
            name = s;
        }

        @Override
        public String getName() {
            return name+" Pid : "+ UtilTools.getPid();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
