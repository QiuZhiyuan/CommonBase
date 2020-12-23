package com.qiu.base.sample.ui.article.adapter;

import android.view.View;
import android.widget.ImageView;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArticleImageViewHolder extends BaseRecyclerViewHolder<ArticleImageItem> {

    @Nullable
    private final ImageView mImageView;

    public ArticleImageViewHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.article_image);
    }

    @Override
    public void bindItem(@NonNull ArticleImageItem item) {
        super.bindItem(item);
        if (mImageView != null) {
            mImageView.setImageBitmap(item.getBitmap());
        }
    }
}
