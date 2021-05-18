package com.qiu.base.sample.ui.index;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.sample.R;

public class LogItemViewHolder extends PageFrameItemViewHolder {
    @NonNull
    private final TextView mLogContent;

    public LogItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mLogContent = itemView.findViewById(R.id.log_content_line);
    }

    @Override
    protected void onBind(@NonNull PageFrameItem item) {
        onDataUpdate(item);

    }

    @Override
    protected void onUnbind(@Nullable PageFrameItem item) {

    }

    @Override
    protected void refreshView(@NonNull PageFrameItem item) {
        if (item instanceof LogItem) {
            mLogContent.setText(((LogItem) item).logContent);
        }
    }
}
