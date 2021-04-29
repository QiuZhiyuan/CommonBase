package com.qiu.base.sample.ui.page_frame;

import androidx.annotation.NonNull;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.frame.PageFrameItem;

public class SimplePageFrameItem extends PageFrameItem {
    public static final int ID = UniqueId.get();

    @NonNull
    public final String text;

    public SimplePageFrameItem(@NonNull String text) {
        this.text = text;
    }

    @Override
    public int getId() {
        return ID;
    }
}
