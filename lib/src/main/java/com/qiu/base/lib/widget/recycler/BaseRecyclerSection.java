package com.qiu.base.lib.widget.recycler;

import com.qiu.base.lib.data.ListEntry;

import java.util.List;

import androidx.annotation.NonNull;

public abstract class BaseRecyclerSection {

    @NonNull
    protected final ListEntry<BaseRecyclerItem> mListEntry = new ListEntry<>();

    public void setDataChangeListener(@NonNull ListEntry.ListChangeListener listener) {
        mListEntry.setListener(listener);
    }

    int getItemCount() {
        return getItems().size();
    }

    int getItemType(int position) {
        return getItems().get(position).getId();
    }

    BaseRecyclerItem getItem(int position) {
        return getItems().get(position);
    }

    @NonNull
    protected List<BaseRecyclerItem> getItems() {
        return mListEntry.getList();
    }

    @NonNull
    protected abstract ViewHolderFactory getViewHolderFactory();
}
