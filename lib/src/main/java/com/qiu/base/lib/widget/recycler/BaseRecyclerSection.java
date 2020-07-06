package com.qiu.base.lib.widget.recycler;

import com.qiu.base.lib.data.ListEntry;

import java.util.List;

import androidx.annotation.NonNull;

public abstract class BaseRecyclerSection {

    @NonNull
    protected final ListEntry<BaseRecyclerItem> mListEntry = new ListEntry<>();

    void setDataChangeListener(@NonNull ListEntry.ListChangeListener listener) {
        mListEntry.setListener(listener);
    }

    @NonNull
    List<BaseRecyclerItem> getItems() {
        return mListEntry.getList();
    }

    @NonNull
    protected abstract ViewHolderFactory getViewHolderFactory();
}
