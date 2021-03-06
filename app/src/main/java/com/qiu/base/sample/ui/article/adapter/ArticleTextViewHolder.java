package com.qiu.base.sample.ui.article.adapter;

import android.view.View;
import android.widget.TextView;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;

public class ArticleTextViewHolder extends BaseRecyclerViewHolder<ArticleTextItem> {

    public ArticleTextViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindItem(@NonNull ArticleTextItem item) {
        super.bindItem(item);
        final String text = item.getContent();
        final TextView textView = itemView.findViewById(R.id.article_text);
        textView.setText(text);
    }
}
