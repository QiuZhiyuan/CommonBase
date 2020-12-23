package com.qiu.base.lib.widget.recycler;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewHolder<T extends BaseRecyclerItem>
        extends RecyclerView.ViewHolder implements
        BaseRecyclerItem.UpdateViewListener {

    @Nullable
    protected T mItem;

    public BaseRecyclerViewHolder(@NonNull final View itemView) {
        super(itemView);
    }

    public final void onBind(@NonNull final T item) {
        mItem = item;
        bindItem(item);
    }

    public final void unBind() {
        unBindItem(mItem);
    }

    @NonNull
    protected Resources getResources() {
        return itemView.getResources();
    }

    @CallSuper
    public void bindItem(@NonNull final T item) {
        item.setUpdateViewListener(this);
    }

    @CallSuper
    public void unBindItem(@Nullable final T item) {
        if (item != null) {
            item.setUpdateViewListener(null);
        }
    }

    @Override
    public void onDataUpdate() {
    }
}
