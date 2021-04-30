package com.qiu.base.lib.widget.frame;

import androidx.annotation.NonNull;

import com.qiu.base.lib.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

    public final void addItem(@NonNull PageFrameItem item) {
        addItem(mItems.size(), item);
    }

    public final void addItem(int index, @NonNull PageFrameItem item) {
        addItemList(index, Collections.singletonList(item));
    }

    public final void addItemList(@NonNull List<PageFrameItem> itemList) {
        addItemList(mItems.size(), itemList);
    }

    public final void addItemList(int index, @NonNull List<PageFrameItem> itemList) {
        if (index < 0) {
            index = 0;
        } else if (index > mItems.size()) {
            index = mItems.size();
        }
        mItems.addAll(index, itemList);
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeInsert(index, itemList.size());
        }
    }

    public final void removeItem(@NonNull PageFrameItem item) {
        final int index = mItems.indexOf(item);
        removeItem(index);
    }

    public final void removeItem(int index) {
        removeItemList(index, 1);
    }

    public final void removeItemList(int index, int itemCount) {
        if (index < 0 || index >= mItems.size() || itemCount < 0) {
            throw new IndexOutOfBoundsException(
                    "Invalid item index " + index + " itemCount " + itemCount + " items count "
                            + mItems.size());
        }
        if (index + itemCount >= mItems.size()) {
            itemCount = mItems.size() - index;
        }
        List<PageFrameItem> subList = mItems.subList(index, index + itemCount);
        mItems.removeAll(subList);
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeRemoved(index, itemCount);
        }
    }

    public final void updateItem(int index, @NonNull PageFrameItem item) {
        updateItemList(index, CollectionUtils.singletonList(item));
    }

    public final void updateItemList(int index, @NonNull List<PageFrameItem> itemList) {
        if (index < 0 || index >= mItems.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid item index " + index + " items count "
                            + mItems.size());
        }
        int itemCount = itemList.size();
        if (index + itemCount >= mItems.size()) {
            itemCount = mItems.size() - index;
        }
        final List<PageFrameItem> subItems = mItems.subList(index, index + itemCount);
        mItems.removeAll(subItems);
        mItems.addAll(index, itemList);
        for (ItemListChangeListener listener : mItemListChangeListenerSet) {
            listener.onItemRangeChange(index, itemList.size());
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
