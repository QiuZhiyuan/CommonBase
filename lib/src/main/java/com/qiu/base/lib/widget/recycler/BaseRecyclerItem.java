package com.qiu.base.lib.widget.recycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseRecyclerItem {

    public interface UpdateViewListener {
        void onDataUpdate();
    }

    @Nullable
    private UpdateViewListener mUpdateViewListener;

    void setUpdateViewListener(@NonNull UpdateViewListener listener) {
        mUpdateViewListener = listener;
    }

    public void onDataUpdate() {
        if (mUpdateViewListener != null) {
            mUpdateViewListener.onDataUpdate();
        }
    }

    public abstract int getId();
}
