package com.qiu.base.lib.widget.frame;

import android.view.View;

import androidx.annotation.AnyThread;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.thread.ThreadUtils;

public abstract class PageFrameItemViewHolder extends RecyclerView.ViewHolder
        implements PageFrameItem.DataUpdateListener {

    @Nullable
    protected PageFrameItem mItem;

    public PageFrameItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    final void bindItem(@NonNull PageFrameItem item) {
        mItem = item;
        mItem.setDataUpdateListener(this);
        mItem.onBind();
        onBind(mItem);
    }

    final void unbindItem() {
        if (mItem != null) {
            mItem.setDataUpdateListener(null);
            mItem.onUnbind();
        }
        onUnbind(mItem);
        mItem = null;
    }

    final void attachedToWindow() {
    }

    final void detachedFromWindow() {
    }

    @AnyThread
    @Override
    public final void onDataUpdate(@NonNull PageFrameItem item) {
        ThreadUtils.i().postMain(() -> refreshView(item));
    }

    @MainThread
    protected void refreshView(@NonNull PageFrameItem item) {

    }

    protected abstract void onBind(@NonNull PageFrameItem item);

    protected abstract void onUnbind(@Nullable PageFrameItem item);
}
