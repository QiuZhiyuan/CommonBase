package com.qiu.base.sample.ui.page_frame.event;

public class RemoveItemRangeEvent {
    public final int index;
    public final int itemCount;

    public RemoveItemRangeEvent(int index, int itemCount) {
        this.index = index;
        this.itemCount = itemCount;
    }
}
