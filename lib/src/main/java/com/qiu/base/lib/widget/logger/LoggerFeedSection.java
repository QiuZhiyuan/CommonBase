package com.qiu.base.lib.widget.logger;

import android.view.View;
import android.view.ViewGroup;

import com.qiu.base.lib.R;
import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.recycler.BaseRecyclerSection;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.lib.widget.recycler.ViewHolderFactory;

import androidx.annotation.NonNull;

public class LoggerFeedSection extends BaseRecyclerSection {

    private static final int TEXT_ITEM_ID = UniqueId.get();

    private static class VerticalViewHodlerFactory extends ViewHolderFactory {

        @NonNull
        @Override
        public BaseRecyclerViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TEXT_ITEM_ID) {
                final View view = getLayoutById(parent, R.layout.logger_item);
                return new LoggerTextViewHolder(view);
            } else {
                throw new RuntimeException();
            }
        }
    }

    @NonNull
    private final VerticalViewHodlerFactory mVerticalViewHodlerFactory;

    public LoggerFeedSection() {
        mVerticalViewHodlerFactory = new VerticalViewHodlerFactory();
    }

    public void addLog(@NonNull String log) {
        mListEntry.add(new LoggerTextItem(TEXT_ITEM_ID, log));
    }

    public void clear() {
        mListEntry.clear();
    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mVerticalViewHodlerFactory;
    }
}
