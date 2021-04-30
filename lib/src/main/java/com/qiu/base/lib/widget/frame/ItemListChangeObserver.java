package com.qiu.base.lib.widget.frame;

public interface ItemListChangeObserver {

    void onItemRangeRemoved(int index, int itemCount);

    void onItemRangeInsert(int index, int itemCount);

    void onItemRangeChange(int index, int itemCount);

    void onItemListSync();
}
