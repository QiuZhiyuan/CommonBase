package com.qiu.base.sample.ui.page_frame;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.eventbus.EventDispatcher;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.lib.widget.frame.PageFrameSection;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.page_frame.event.AddItemListEvent;
import com.qiu.base.sample.ui.page_frame.event.RemoveItemRangeEvent;
import com.qiu.base.sample.ui.page_frame.event.UpdateItemListEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SimplePageFrameSection extends PageFrameSection {

    public class EventHandler {
        @Subscribe
        public void onAddPageFrameItem(AddItemListEvent event) {
            addItemList(event.position, event.itemList);
        }

        @Subscribe
        public void onRemoveItemRange(RemoveItemRangeEvent event) {
            removeItemList(event.index, event.itemCount);
        }

        @Subscribe
        public void onUpdateItemList(UpdateItemListEvent event) {
            updateItemList(event.index, event.itemList);
        }
    }

    @NonNull
    private final ViewHolderFactory mViewHolderFactory = new ViewHolderFactory() {
        @Nullable
        @Override
        public PageFrameItemViewHolder createItemViewHolder(@NonNull ViewGroup parent,
                int itemType) {
            if (itemType == SimplePageFrameItem.ID) {
                return new SimplePageFrameItemViewHolder(
                        findLayoutById(R.layout.item_page_frame, parent));
            }
            return null;
        }
    };

    @NonNull
    private final EventHandler mEventHandler = new EventHandler();

    public SimplePageFrameSection() {
        super();
        final List<PageFrameItem> items = new ArrayList<>();
        items.add(new SimplePageFrameItem("hello"));
        items.add(new SimplePageFrameItem("world"));
        items.add(new SimplePageFrameItem("remove"));
        items.add(new SimplePageFrameItem("add"));
        items.add(new SimplePageFrameItem("1215454"));
        items.add(new SimplePageFrameItem("31313"));
        items.add(new SimplePageFrameItem("1314141"));
        items.add(new SimplePageFrameItem("313141"));
        items.add(new SimplePageFrameItem("51515"));
        items.add(new SimplePageFrameItem("5315"));
        items.add(new SimplePageFrameItem("72522"));
        items.add(new SimplePageFrameItem("add141414"));
        items.add(new SimplePageFrameItem("qrfaa"));
        items.add(new SimplePageFrameItem("fwaefwafw"));
        items.add(new SimplePageFrameItem("wefaffaf"));
        items.add(new SimplePageFrameItem("saebaehae"));
        addItemList(items);
    }

    public void onAttached() {
        Logger.d("section onAttached");
        EventDispatcher.register(mEventHandler);
    }

    public void onDetached() {
        Logger.d("section onDetached");
        EventDispatcher.unregister(mEventHandler);
    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mViewHolderFactory;
    }
}
