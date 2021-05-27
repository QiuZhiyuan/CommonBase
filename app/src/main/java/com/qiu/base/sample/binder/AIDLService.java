package com.qiu.base.sample.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.IPerson;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.tools.UtilTools;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {

    public static final String KEY_BINDER_GET = "key_binder_set";
    public static final int AIDL_BINDER = 100;
    public static final int SIMPLE_BINDER = 101;

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
    private final Binder iPersonBinder = new IPerson.Stub() {

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
        Logger.d(getClass().getSimpleName() + " onBinder");
        final Bundle bundle = intent.getExtras();
        final int binderType = bundle != null ? bundle.getInt(KEY_BINDER_GET, -2) : -1;
        if (binderType == AIDL_BINDER) {
            Logger.d("return aidl binder");
            return iPersonBinder;
        } else if (binderType == SIMPLE_BINDER) {
            Logger.d("return simple binder");
            return new SimpleBinder();
        }
        Logger.e("No binder type:" + binderType);
        return null;
    }
}
