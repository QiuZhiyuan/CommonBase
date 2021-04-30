package com.qiu.base.sample.ui.page_frame.event;

import androidx.annotation.NonNull;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.sample.ui.page_frame.SimplePageFrameItem;

import java.util.ArrayList;
import java.util.List;

public class AddItemListEvent {

    @NonNull
    public final List<PageFrameItem> itemList;
    public final int position;

    public AddItemListEvent(@NonNull List<SimplePageFrameItem> itemList, int position) {
        this.itemList = new ArrayList<>();
        this.itemList.addAll(itemList);
        this.position = position;
    }
}
