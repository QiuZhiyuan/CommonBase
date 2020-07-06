package com.qiu.base.lib.widget.logger;

import android.view.View;
import android.widget.TextView;

import com.qiu.base.lib.R;
import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;
import com.qiu.base.lib.widget.recycler.BaseRecyclerViewHolder;

import androidx.annotation.NonNull;

public class LoggerTextViewHolder extends BaseRecyclerViewHolder {

    public LoggerTextViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindItem(@NonNull BaseRecyclerItem item) {
        if (item instanceof LoggerTextItem) {
            final String text = ((LoggerTextItem) item).getContent();
            final TextView textView = itemView.findViewById(R.id.log_text);
            textView.setText(text);
        }
    }

    @Override
    public void unBindItem() {

    }
}
