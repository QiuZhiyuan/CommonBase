package com.qiu.base.sample.ui.article.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Show article feed, mix show text and image (jpg/png/gif)
 * Edit all elements in article
 */
public class ArticleFeedView extends RecyclerView {
    public ArticleFeedView(@NonNull Context context) {
        super(context);
    }

    public ArticleFeedView(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticleFeedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
