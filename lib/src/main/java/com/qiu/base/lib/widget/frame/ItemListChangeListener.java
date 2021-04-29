package com.qiu.base.lib.widget.frame;

public interface ItemListChangeListener {

    void onItemRangeRemoved(int index,int itemCount);

    void onItemRangeInsert(int index, int itemCount);

    void onItemListSync();
}
