package com.qiu.base.sample.ui.index;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;
import com.qiu.base.sample.R;

public class IndexViewHolderFactory extends ViewHolderFactory {
    @Nullable
    @Override
    public PageFrameItemViewHolder createItemViewHolder(@NonNull ViewGroup parent, int itemType) {
        if (itemType == ButtonItem.ID || itemType == GuidePageItem.ID) {
            return new GuidePageItemViewHolder(findLayoutById(R.layout.item_index_button, parent));
        } else if (itemType == LogItem.ID) {
            return new LogItemViewHolder(findLayoutById(R.layout.item_index_log, parent));
        }
        return null;
    }
}
