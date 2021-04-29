package com.qiu.base.lib.widget.frame;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onDataUpdate(@NonNull PageFrameItem item) {
    }

    protected abstract void onBind(@NonNull PageFrameItem item);

    protected abstract void onUnbind(@Nullable PageFrameItem item);
}
