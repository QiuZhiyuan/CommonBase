package com.qiu.base.lib.widget.frame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class PageFrameItem {

    public interface DataUpdateListener {
        public void onDataUpdate(@NonNull PageFrameItem item);
    }

    @Nullable
    private DataUpdateListener mDataUpdateListener;

    final void setDataUpdateListener(@Nullable DataUpdateListener listener) {
        mDataUpdateListener = listener;
    }

    public final void callDataUpdate() {
        if (mDataUpdateListener != null) {
            mDataUpdateListener.onDataUpdate(this);
        }
    }

    public void onBind() {
    }

    public void onUnbind() {
    }
    
    public abstract int getId();
}
