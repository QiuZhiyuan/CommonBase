package com.qiu.base.sample.ui.page_frame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.eventbus.EventDispatcher;
import com.qiu.base.lib.toast.ToastUtils;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.page_frame.event.AddPageFrameItemEvent;
import com.qiu.base.sample.ui.page_frame.event.RemovePageFrameItemEvent;

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
                    ToastUtils.show("click:" + ((SimplePageFrameItem) mItem).text);
                    EventDispatcher.post(new AddPageFrameItemEvent(
                            new SimplePageFrameItem(((SimplePageFrameItem) mItem).text + " copy")));
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItem instanceof SimplePageFrameItem) {
                    ToastUtils.show("long click:" + ((SimplePageFrameItem) mItem).text);
                    EventDispatcher.post(new RemovePageFrameItemEvent(mItem));
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
