package com.qiu.base.sample.ui.index;

import androidx.annotation.NonNull;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.frame.PageFrameItem;

public class LogItem extends PageFrameItem {

    public static final int ID = UniqueId.get();

    @NonNull
    public final String logContent;

    public LogItem(@NonNull String logContent) {
        this.logContent = logContent;
    }

    @Override
    public int getId() {
        return ID;
    }
}
