package com.qiu.base.sample.binder;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.Logger;

public class SimpleBinder extends Binder {

    public static final int REMOTE_CODE = 100;

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
            throws RemoteException {
        switch (code) {
            case REMOTE_CODE:
                int a = data.readInt();
                int b = data.readInt();
                int result = (a + b) * 2;
                if (reply != null) {
                    reply.writeInt(result);
                }
                Logger.d("Simple binder a:" + a + " b:" + b + " result:" + result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }
}
