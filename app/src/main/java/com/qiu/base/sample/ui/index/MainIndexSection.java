package com.qiu.base.sample.ui.index;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameSection;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;

import java.util.List;

public class MainIndexSection extends PageFrameSection {

    @NonNull
    private final ViewHolderFactory mViewHolderFactory = new IndexViewHolderFactory();

    public MainIndexSection(@Nullable List<PageFrameItem> itemList) {
        super();
        if (itemList != null) {
            addItemList(itemList);
        }
    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mViewHolderFactory;
    }

    public void addLog(@NonNull String logContent) {
        addLog(new LogItem(logContent));
    }

    public void addLog(@NonNull LogItem item) {
        int index = 0;
        while (!(getItemAllowNull(index) instanceof LogItem) && getItemAllowNull(index) != null) {
            index++;
        }
        addItem(index, item);
    }

    public void clearLog() {
        removeByFilter(pageFrameItem -> pageFrameItem instanceof LogItem);
    }
}
