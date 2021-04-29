package com.qiu.base.sample.ui.page_frame.event;

import androidx.annotation.NonNull;

import com.qiu.base.lib.widget.frame.PageFrameItem;

public class RemovePageFrameItemEvent {
    @NonNull
    public final PageFrameItem item;

    public RemovePageFrameItemEvent(@NonNull PageFrameItem item) {
        this.item = item;
    }
}
