package com.qiu.base.lib.widget.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    @Nullable
    protected BaseRecyclerItem mItem;

    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public final void onBind(@NonNull BaseRecyclerItem item) {
        mItem = item;
        bindItem(item);
        onViewBinded(item);
    }

    public final void unBind() {
        unBindItem();
        onViewRecycled();
    }

    private void onViewBinded(@NonNull BaseRecyclerItem item) {

    }

    private void onViewRecycled() {

    }

    public abstract void bindItem(@NonNull BaseRecyclerItem item);

    public abstract void unBindItem();
}
