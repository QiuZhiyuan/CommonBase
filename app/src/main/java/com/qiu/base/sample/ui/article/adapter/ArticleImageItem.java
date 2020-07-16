package com.qiu.base.sample.ui.article.widget;

import android.graphics.Bitmap;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;

import androidx.annotation.NonNull;

public class ArticleImageItem extends BaseRecyclerItem {

    private final int mId;
    private final Bitmap mBitmap;

    public ArticleImageItem(int id, @NonNull Bitmap bitmap) {
        mId = id;
        mBitmap = bitmap;
    }

    @Override
    public int getId() {
        return mId;
    }

    @NonNull
    public Bitmap getBitmap() {
        return mBitmap;
    }
}
