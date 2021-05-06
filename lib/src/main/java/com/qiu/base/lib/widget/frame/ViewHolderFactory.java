package com.qiu.base.lib.widget.frame;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.utils.App;

public abstract class ViewHolderFactory {

    @NonNull
    public static View findLayoutById(@LayoutRes int layoutId, @NonNull ViewGroup parent) {
        return App.i().getLayoutInflater().inflate(layoutId, parent, false);
    }

    @Nullable
    public abstract PageFrameItemViewHolder createItemViewHolder(@NonNull ViewGroup parent,
            int itemType);
}
