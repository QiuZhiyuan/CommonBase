package com.qiu.base.sample.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.UtilTools;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    @NonNull
    private static final String TAG = AIDLService.class.getSimpleName();
//
//    private static class MyBinder extends Binder {
//        @Override
//        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply,
//                int flags) throws RemoteException {
//            Logger.d("onTransact code:" + code + " data:" + data.toString());
//            return super.onTransact(code, data, reply, flags);
//        }
//    }

    private String name = "qiuzhiyuan";

    @NonNull
    private final Binder binder = new IPerson.Stub() {

        @NonNull
        private final List<String> mNameList = new ArrayList<>();

        @Override
        public void setName(String s) {
            name = s;
            mNameList.add(s);
        }

        @Override
        public String getName() {
            return name + " Pid : " + UtilTools.getPid();
        }

        @NonNull
        @Override
        public List<String> getNameList() {
            return mNameList;
        }

        @NonNull
        @Override
        public String getThreadPid() {
            return String.valueOf(UtilTools.getPid());
        }

        @Override
        public void setIBinder(IBinder iBinder) throws RemoteException {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
