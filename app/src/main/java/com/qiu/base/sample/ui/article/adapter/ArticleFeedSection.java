package com.qiu.base.sample.ui.article.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.recycler.BaseRecyclerSection;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.lib.widget.recycler.ViewHolderFactory;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;

public class ArticleFeedSection extends BaseRecyclerSection {

    private static final int TEXT_ITEM_ID = UniqueId.get();
    private static final int IMAGE_ITEM_ID = UniqueId.get();

    private static class VerticalViewHodlerFactory extends ViewHolderFactory {

        @NonNull
        @Override
        public BaseRecyclerViewHolder createViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TEXT_ITEM_ID) {
                final View view = getLayoutById(parent, R.layout.article_text_item);
                return new ArticleTextViewHolder(view);
            }
            if (viewType == IMAGE_ITEM_ID) {
                final View view = getLayoutById(parent, R.layout.article_image_item);
                return new ArticleImageViewHolder(view);
            } else {
                throw new RuntimeException();
            }
        }
    }

    @NonNull
    private final VerticalViewHodlerFactory mVerticalViewHodlerFactory;

    public ArticleFeedSection() {
        mVerticalViewHodlerFactory = new VerticalViewHodlerFactory();
    }

    public void addLog(@NonNull String log) {
        mListEntry.add(new ArticleTextItem(TEXT_ITEM_ID, log));
    }

    public void addImage(@NonNull Bitmap bitmap) {
        mListEntry.add(new ArticleImageItem(IMAGE_ITEM_ID, bitmap));
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
