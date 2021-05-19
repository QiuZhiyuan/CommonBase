package com.qiu.base.lib.widget.frame;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class PageFrameItem {

    public interface DataUpdateListener {
        @AnyThread
        void onDataUpdate(@NonNull PageFrameItem item);
    }

    @Nullable
    private DataUpdateListener mDataUpdateListener;
    private int mBindCount;

    final void setDataUpdateListener(@Nullable DataUpdateListener listener) {
        mDataUpdateListener = listener;
    }

    public final void callDataUpdate() {
        if (mDataUpdateListener != null) {
            mDataUpdateListener.onDataUpdate(this);
        }
    }

    public void onBind() {
        mBindCount++;
    }

    public void onUnbind() {
        mBindCount--;
    }

    /**
     * Bind state may update after parent view layout
     *
     * @return whether item bind by view holder
     */
    public boolean isBind() {
        return mBindCount > 0;
    }

    public abstract int getId();
}
