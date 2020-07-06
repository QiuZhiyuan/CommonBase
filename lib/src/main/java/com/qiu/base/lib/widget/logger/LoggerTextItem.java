package com.qiu.base.lib.widget.logger;

import com.qiu.base.lib.widget.recycler.BaseRecyclerItem;

import androidx.annotation.NonNull;

public class LoggerTextItem extends BaseRecyclerItem {

    @NonNull
    private final String mContent;
    private final int mId;

    public LoggerTextItem(int id, @NonNull String content) {
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
