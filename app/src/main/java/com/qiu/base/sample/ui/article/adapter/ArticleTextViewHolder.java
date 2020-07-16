package com.qiu.base.sample.ui.article.widget;

import android.view.View;
import android.widget.TextView;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;
import com.qiu.base.sample.R;

import androidx.annotation.NonNull;

public class ArticleTextViewHolder extends BaseRecyclerViewHolder {

    public ArticleTextViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindItem(@NonNull BaseRecyclerItem item) {
        if (item instanceof ArticleTextItem) {
            final String text = ((ArticleTextItem) item).getContent();
            final TextView textView = itemView.findViewById(R.id.article_text);
            textView.setText(text);
        }
    }

    @Override
    public void unBindItem() {

    }
}
