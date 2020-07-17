package com.qiu.base.sample.ui.article.adapter;

import android.view.View;
import android.widget.ImageView;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArticleImageViewHolder extends BaseRecyclerViewHolder {

    @Nullable
    private final ImageView mImageView;

    public ArticleImageViewHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.article_image);
    }

    @Override
    public void bindItem(@NonNull BaseRecyclerItem item) {
        if (item instanceof ArticleImageItem) {
            if (mImageView != null) {
                mImageView.setImageBitmap(((ArticleImageItem) item).getBitmap());
            }
        }
    }

    @Override
    public void unBindItem() {

    }
}
