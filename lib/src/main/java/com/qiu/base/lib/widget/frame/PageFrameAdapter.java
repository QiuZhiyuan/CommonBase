package com.qiu.base.lib.widget.frame;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PageFrameAdapter extends RecyclerView.Adapter<PageFrameItemViewHolder>
        implements ItemListChangeObserver {

    @NonNull
    private final PageFrameSection mSection;

    public PageFrameAdapter(@NonNull PageFrameSection section) {
        mSection = section;
    }

    @NonNull
    @Override
    public PageFrameItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final PageFrameItemViewHolder viewHolder =
                mSection.getViewHolderFactory().createItemViewHolder(parent, viewType);
        return viewHolder != null ? viewHolder :
                new SimplePageFrameItemViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull PageFrameItemViewHolder holder, int position) {
        final PageFrameItem item = mSection.getItem(position);
        holder.bindItem(item);
    }

    @Override
    public void onViewRecycled(@NonNull PageFrameItemViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unbindItem();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull PageFrameItemViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.attachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PageFrameItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.detachedFromWindow();
    }

    @Override
    public int getItemViewType(int position) {
        final PageFrameItem item = mSection.getItem(position);
        return item.getId();
    }

    @Override
    public int getItemCount() {
        return mSection.getItemListSize();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSection.onAttached();
        mSection.addItemListChangeObserver(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mSection.onDetached();
        mSection.removeItemListChangeObserver(this);
    }

    @Override
    public void onItemRangeRemoved(int index, int itemCount) {
        notifyItemRangeRemoved(index, itemCount);
    }

    @Override
    public void onItemRangeInsert(int index, int itemCount) {
        notifyItemRangeInserted(index, itemCount);
    }

    @Override
    public void onItemRangeChange(int index, int itemCount) {
        notifyItemRangeChanged(index, itemCount);
    }

    @Override
    public void onItemListSync() {
        notifyDataSetChanged();
    }
}
