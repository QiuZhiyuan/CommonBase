package com.qiu.base.lib.widget.frame;

import androidx.annotation.NonNull;

import com.qiu.base.lib.tools.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class PageFrameSection {

    @NonNull
    private final List<PageFrameItem> mItems = new ArrayList<>();
    @NonNull
    private final Set<ItemListChangeListener> mItemListChangeListenerSet = new HashSet<>();

    public PageFrameSection() {
        super();
    }

    public void onAttached() {
    }

    public void onDetached() {
    }

    @NonNull
    public PageFrameItem getItem(int index) {
        if (index < 0 || index >= mItems.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid item index " + index + " items count " + mItems.size());
        }
        return mItems.get(index);
    }

    public final void addItemListChangeListener(
            @NonNull ItemListChangeListener listChangeListener) {
        mItemListChangeListenerSet.add(listChangeListener);
        listChangeListener.onItemListSync();
    }

    public final void removeItemListChangeListener(
            @NonNull ItemListChangeListener listChangeListener) {
        mItemListChangeListenerSet.remove(listChangeListener);
    }

    public void addItem(@NonNull PageFrameItem item) {
        addItem(mItems.size(), item);
    }

    public void addItem(int index, @NonNull PageFrameItem item) {
        mItems.add(index, item);
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeInsert(index, 1);
        }
    }

    public void addItemList(@NonNull List<PageFrameItem> itemList) {
        addItemList(mItems.size(), itemList);
    }

    public void addItemList(int index, @NonNull List<PageFrameItem> itemList) {
        mItems.addAll(index, itemList);
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeInsert(index, itemList.size());
        }
    }

    public void removeItem(@NonNull PageFrameItem item) {
        final int index = mItems.indexOf(item);
        Logger.d("remove item index:" + index);
        if (index >= 0) {
            removeItem(index);
        }
    }

    public void removeItem(int index) {
        removeItem(index, 1);
    }

    public void removeItem(int index, int itemCount) {
        if (index < 0 || index >= mItems.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid item index " + index + " itemCount " + itemCount + " items count "
                            + mItems.size());
        }
        Iterator<PageFrameItem> itemIterator = mItems.iterator();
        Logger.d("index:" + index + " itemCount:" + itemCount + " size:" + mItems.size());
        int i = 0;
        while (itemIterator.hasNext() && i < index + itemCount) {
            if (i >= index) {
                itemIterator.remove();
            } else {
                itemIterator.next();
            }
            i++;
        }
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeRemoved(index, itemCount);
        }
    }

    public void callItemListChanged() {
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemListSync();
        }
    }

    public int getItemListSize() {
        return mItems.size();
    }

    @NonNull
    protected abstract ViewHolderFactory getViewHolderFactory();

}
