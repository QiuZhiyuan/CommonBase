package com.qiu.base.sample.ui.page_frame.event;

import androidx.annotation.NonNull;

import com.qiu.base.lib.widget.frame.PageFrameItem;

public class AddPageFrameItemEvent {

    @NonNull
    public final PageFrameItem item;
    public final int position;

    public AddPageFrameItemEvent(@NonNull PageFrameItem item, int position) {
        this.item = item;
        this.position = position;
    }
}
