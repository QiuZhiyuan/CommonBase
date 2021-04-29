package com.qiu.base.lib.widget.frame;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SimplePageFrameItemViewHolder extends PageFrameItemViewHolder {
    public SimplePageFrameItemViewHolder(@NonNull Context context) {
        super(new View(context));
    }

    @Override
    protected void onBind(@NonNull PageFrameItem item) {

    }

    @Override
    protected void onUnbind(@Nullable PageFrameItem item) {

    }
}
