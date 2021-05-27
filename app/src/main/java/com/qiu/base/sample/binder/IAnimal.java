package com.qiu.base.sample.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IAnimal extends IInterface {

    public static class Proxy implements IAnimal {

        private static final int METHOD_get_name = 100;
        private static final int METHOD_set_name = 101;
        @NonNull
        private final IBinder mRemote;

        public Proxy(@NonNull IBinder remote) {
            super();
            mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        @NonNull
        @Override
        public String getName() throws RemoteException {
            final Parcel data = Parcel.obtain();
            final Parcel reply = Parcel.obtain();

            mRemote.transact(METHOD_get_name, data, reply, 0);
            final String name = reply.readString();

            data.recycle();
            reply.recycle();
            return name;
        }

        @Override
        public void setName(@NonNull String name) throws RemoteException {
            final Parcel data = Parcel.obtain();
            final Parcel reply = Parcel.obtain();

            data.writeString(name);
            mRemote.transact(METHOD_set_name, data, reply, 0);

            data.recycle();
            reply.recycle();

        }
    }

    public static abstract class Stub implements IAnimal, IBinder {

    }

    @NonNull
    public static IAnimal asInterface(IBinder binder) {
        return new Proxy(binder);
    }

    @Override
    IBinder asBinder();

    @NonNull
    String getName() throws RemoteException;

    void setName(@NonNull String name) throws RemoteException;
}
