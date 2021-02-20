package com.qiu.base.lib.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class ViewHolderFactory {

    @Nullable
    public abstract BaseRecyclerViewHolder<? extends BaseRecyclerItem> createViewHolder(
            @NonNull ViewGroup parent, int viewType);

    @NonNull
    protected View getLayoutById(@NonNull ViewGroup parent, @LayoutRes int id) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(id, parent, false);
    }
}
