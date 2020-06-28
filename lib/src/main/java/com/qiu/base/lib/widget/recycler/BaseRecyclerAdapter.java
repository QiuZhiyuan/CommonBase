package com.qiu.base.lib.widget.recycler;

import android.view.ViewGroup;

import com.qiu.base.lib.data.ListEntry;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerAdapter extends RecyclerView.Adapter
        implements ListEntry.ListChangeListener {

    @NonNull
    private final BaseRecyclerSection mSection;

    public BaseRecyclerAdapter(@NonNull BaseRecyclerSection section) {
        mSection = section;
        mSection.setDataChangeListener(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mSection.getViewHolderFactory().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseRecyclerViewHolder) {
            final BaseRecyclerItem item = mSection.getItems().get(position);
            ((BaseRecyclerViewHolder) holder).onBind(item);
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof BaseRecyclerViewHolder) {
            ((BaseRecyclerViewHolder) holder).unBind();
        }
    }

    @Override
    public int getItemCount() {
        return mSection.getItems().size();
    }

    @Override
    public int getItemViewType(int position) {
        return mSection.getItems().get(position).getId();
    }

    @Override
    public void onListChanged() {
        notifyDataSetChanged();
    }
}
