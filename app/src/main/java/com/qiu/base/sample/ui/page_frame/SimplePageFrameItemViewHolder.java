package com.qiu.base.sample.ui.page_frame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.eventbus.EventDispatcher;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.CollectionUtils;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.page_frame.event.AddItemListEvent;
import com.qiu.base.sample.ui.page_frame.event.RemoveItemRangeEvent;
import com.qiu.base.sample.ui.page_frame.event.UpdateItemListEvent;

import java.util.List;

public class SimplePageFrameItemViewHolder extends PageFrameItemViewHolder {

    @NonNull
    private final TextView mTextView;

    public SimplePageFrameItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem instanceof SimplePageFrameItem) {
                    Logger.d("click:" + ((SimplePageFrameItem) mItem).text);
                    final String text1 = ((SimplePageFrameItem) mItem).text + " copy1";
                    final String text2 = ((SimplePageFrameItem) mItem).text + " copy2";
                    final List<SimplePageFrameItem> itemList = CollectionUtils
                            .createList(new SimplePageFrameItem(text1),
                                    new SimplePageFrameItem(text2));
//                    EventDispatcher.post(new AddItemListEvent(itemList, getAdapterPosition() + 1));
                    EventDispatcher.post(new UpdateItemListEvent(itemList, getAdapterPosition()));
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItem instanceof SimplePageFrameItem) {
                    Logger.d("long click:" + ((SimplePageFrameItem) mItem).text);
                    EventDispatcher.post(new RemoveItemRangeEvent(getAdapterPosition(), 1));
                }
                return true;
            }
        });
    }

    @Override
    protected void onBind(@NonNull PageFrameItem item) {
        if (item instanceof SimplePageFrameItem) {
            final SimplePageFrameItem simpleItem = (SimplePageFrameItem) item;
            mTextView.setText(simpleItem.text);
        }
    }

    @Override
    protected void onUnbind(@Nullable PageFrameItem item) {
    }
}
