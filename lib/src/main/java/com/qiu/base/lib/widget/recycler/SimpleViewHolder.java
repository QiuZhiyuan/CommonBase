package com.qiu.base.lib.widget.recycler;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class SimpleViewHolder extends BaseRecyclerViewHolder<SimpleItem> {
    public SimpleViewHolder(@NonNull Context context) {
        super(new View(context));
    }
}
