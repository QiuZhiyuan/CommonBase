package com.qiu.base.sample.ui.index;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.sample.R;

public class GuidePageItemViewHolder extends PageFrameItemViewHolder {

    @NonNull
    private final Button mIndexButton;

    public GuidePageItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mIndexButton = itemView.findViewById(R.id.index_button);

    }

    @Override
    protected void onBind(@NonNull PageFrameItem item) {
        if (item instanceof ButtonItem) {
            final ButtonItem buttonItem = (ButtonItem) item;
            mIndexButton.setText(buttonItem.buttonTitle);
            mIndexButton.setOnClickListener(buttonItem.onClickListener);
        }
    }

    @Override
    protected void onUnbind(@Nullable PageFrameItem item) {

    }
}
