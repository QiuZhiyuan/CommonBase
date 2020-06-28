package com.qiu.base.lib.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public abstract class ViewHolderFactory {

    @NonNull
    public abstract BaseRecyclerViewHolder createViewHolder(@NonNull ViewGroup parent,
            int viewType);

    @NonNull
    protected View findViewById(@NonNull ViewGroup parent, @LayoutRes int id) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(id, parent, false);
    }

}
