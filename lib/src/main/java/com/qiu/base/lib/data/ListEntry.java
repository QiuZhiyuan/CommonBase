package com.qiu.base.lib.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.impl.Callback;

public class ListEntry<T> {

    public interface ListChangeListener {
        void onListChanged();
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

    @NonNull
    public List<T> getList() {
        return mList;
    }

    public void callListChanged() {
        if (mListener != null) {
            mListener.onListChanged();
        }
    }

    @NonNull
    public Iterator<T> getIterator() {
        return mList.iterator();
    }

    public void iteration(@NonNull Callback<T> onCall) {
        Iterator<T> iterator = getIterator();
        while (iterator.hasNext()) {
            onCall.onCall(iterator.next());
        }
    }

    public void add(@NonNull T item) {
        add(item, true);
    }

    public void add(@NonNull T item, boolean update) {
        mList.add(item);
        if (update) {
            callListChanged();
        }
    }

    public void add(int index, @NonNull T element) {
        add(index, element, true);
    }

    public void add(int index, @NonNull T element, boolean update) {
        mList.add(index, element);
        if (update) {
            callListChanged();
        }
    }

    public void addAll(@NonNull Collection<T> elements) {
        addAll(elements, true);
    }

    public void addAll(@NonNull Collection<T> elements, boolean update) {
        mList.addAll(elements);
        if (update) {
            callListChanged();
        }
    }

    public void addAll(int index, @NonNull Collection<T> elements) {
        addAll(index, elements, true);
    }

    public void addAll(int index, @NonNull Collection<T> elements, boolean update) {
        mList.addAll(index, elements);
        if (update) {
            callListChanged();
        }
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

    public void update(int index, @NonNull T t) {
        if (contain(index)) {
            mList.set(index, t);
        }
    }

    @Nullable
    public T get(int index) {
        if (contain(index)) {
            return mList.get(index);
        } else {
            return null;
        }
    }

    public int size() {
        return mList.size();
    }

    public boolean contains(@NonNull T t) {
        return mList.contains(t);
    }

    public boolean contain(int index) {
        return index >= 0 && index < mList.size();
    }

    public void clear() {
        mList.clear();
        callListChanged();
    }
}
