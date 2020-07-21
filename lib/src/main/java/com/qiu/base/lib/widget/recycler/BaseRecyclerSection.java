package com.qiu.base.lib.widget.recycler;

import com.qiu.base.lib.data.ListEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public abstract class BaseRecyclerSection {

    public enum State {
        START,
        LOADING,
        FINISH,
        ERROR,
    }

    public interface SectionStateListener {
        void onChanged(@NonNull State state);
    }

    @NonNull
    private List<SectionStateListener> mListeners = new ArrayList<>();
    @NonNull
    private State mState;

    public BaseRecyclerSection() {
        mState = State.START;
    }

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

    protected void setState(@NonNull State state) {
        mState = state;
        for (SectionStateListener listener : mListeners) {
            listener.onChanged(state);
        }
    }

    public void addStateListener(@NonNull SectionStateListener listener) {
        mListeners.add(listener);
    }

    public void removeStateListener(@NonNull SectionStateListener listener) {
        mListeners.remove(listener);
    }

    public void onCreate() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onDestroy() {
    }
}
