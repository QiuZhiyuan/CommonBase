package com.qiu.base.sample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.UtilTools;

public class AIDLService extends Service {

    private String name = "qiuzhiyuan";

    @NonNull
    private final Binder binder = new IPerson.Stub() {
        @Override
        public void setName(String s) {
            name = s;
        }

        @Override
        public String getName() {
            return name + " Pid : " + UtilTools.getPid();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
