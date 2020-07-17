package com.qiu.base.sample.ui.article.adapter;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;

import androidx.annotation.NonNull;

public class ArticleTextItem extends BaseRecyclerItem {

    @NonNull
    private final String mContent;
    private final int mId;

    public ArticleTextItem(int id, @NonNull String content) {
        mContent = content;
        mId = id;
    }

    @Override
    public int getId() {
        return mId;
    }

    @NonNull
    public String getContent() {
        return mContent;
    }
}
