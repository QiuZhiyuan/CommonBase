package com.qiu.base.lib.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListEntry<T> {

    public interface ListChangeListener {
        public void onListChanged();
    }

    @NonNull
    private final List<T> mList;
    @Nullable
    private ListChangeListener mListener;

    public ListEntry() {
        mList = createList();
    }

    public void setListener(@NonNull ListChangeListener listener) {
        mListener = listener;
    }

    @NonNull
    protected List<T> createList() {
        return new ArrayList<T>();
    }

    public List<T> getList() {
        return mList;
    }

    protected void callListChanged() {
        if (mListener != null) {
            mListener.onListChanged();
        }
    }

    public void add(@NonNull T item) {
        mList.add(item);
        callListChanged();
    }

    public void add(int index, @NonNull T element) {
        mList.add(index, element);
        callListChanged();
    }

    public void addAll(@NonNull Collection<T> elements) {
        mList.addAll(elements);
        callListChanged();
    }

    public void addAll(int index, @NonNull Collection<T> elements) {
        mList.addAll(index, elements);
        callListChanged();
    }

    public boolean remove(@NonNull T element) {
        boolean result = mList.remove(element);
        callListChanged();
        return result;
    }

    public T remove(int index) {
        T result = mList.remove(index);
        callListChanged();
        return result;
    }

    public void clear() {
        mList.clear();
        callListChanged();
    }
}
