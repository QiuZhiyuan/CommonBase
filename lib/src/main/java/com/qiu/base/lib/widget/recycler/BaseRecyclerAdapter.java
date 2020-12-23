package com.qiu.base.lib.widget.recycler;

import android.view.ViewGroup;

import com.qiu.base.lib.data.ListEntry;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder>
        implements ListEntry.ListChangeListener {

    @NonNull
    private final BaseRecyclerSection mSection;

    public BaseRecyclerAdapter(@NonNull BaseRecyclerSection section) {
        mSection = section;
        mSection.setDataChangeListener(this);
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder viewHolder =
                mSection.getViewHolderFactory().createViewHolder(parent, viewType);
        if (viewHolder != null) {
            return viewHolder;
        } else {
            return new SimpleViewHolder(parent.getContext());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        final BaseRecyclerItem item = mSection.getItem(position);
        holder.onBind(item);
    }

    @Override
    public void onViewRecycled(@NonNull BaseRecyclerViewHolder holder) {
        holder.unBind();
    }

    @Override
    public int getItemCount() {
        return mSection.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mSection.getItemType(position);
    }

    @Override
    public void onListChanged() {
        notifyDataSetChanged();
    }
}
