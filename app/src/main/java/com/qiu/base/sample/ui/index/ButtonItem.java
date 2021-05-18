package com.qiu.base.sample.ui.index;

import android.view.View;

import androidx.annotation.NonNull;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.frame.PageFrameItem;

public class ButtonItem extends PageFrameItem {
    public static final int ID = UniqueId.get();

    @NonNull
    public final String buttonTitle;

    @NonNull
    public final View.OnClickListener onClickListener;

    public ButtonItem(@NonNull String buttonTitle, @NonNull View.OnClickListener onClickListener) {
        this.buttonTitle = buttonTitle;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getId() {
        return ID;
    }
}
