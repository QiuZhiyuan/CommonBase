package com.qiu.base.sample.ui.page_frame.event;

import androidx.annotation.NonNull;

import com.qiu.base.lib.widget.frame.PageFrameItem;

public class AddPageFrameItemEvent {

    @NonNull
    public final PageFrameItem item;

    public AddPageFrameItemEvent(@NonNull PageFrameItem item) {
        this.item = item;
    }
}
