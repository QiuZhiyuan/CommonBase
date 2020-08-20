package com.qiu.base.lib.widget.recycler;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder implements
        BaseRecyclerItem.UpdateViewListener {

    @Nullable
    protected BaseRecyclerItem mItem;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public final void onBind(@NonNull BaseRecyclerItem item) {
        mItem = item;
        bindItem(item);
    }

    public final void unBind() {
        unBindItem();
    }

    @NonNull
    protected Resources getResources() {
        return itemView.getResources();
    }

    public void bindItem(@NonNull BaseRecyclerItem item) {
        item.setUpdateViewListener(this);
    }

    public void unBindItem() {
    }

    @Override
    public void onDataUpdate() {
    }
}
