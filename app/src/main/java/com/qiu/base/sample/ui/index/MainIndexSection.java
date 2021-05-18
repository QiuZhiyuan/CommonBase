package com.qiu.base.sample.ui.index;

import androidx.annotation.NonNull;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameSection;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;

import java.util.List;

public class MainIndexSection extends PageFrameSection {

    @NonNull
    private final ViewHolderFactory mViewHolderFactory = new IndexViewHolderFactory();

    public MainIndexSection(List<PageFrameItem> itemList) {
        super();
        addItemList(itemList);
    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mViewHolderFactory;
    }
}
