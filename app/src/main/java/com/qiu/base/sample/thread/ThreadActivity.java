package com.qiu.base.sample.thread;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.ViewUtils;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.index.ButtonItem;
import com.qiu.base.sample.ui.index.MainIndexSection;

import java.util.ArrayList;
import java.util.List;

public class ThreadActivity extends Activity {

    @Nullable
    private TextView mLogContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_and_log);
        mLogContent = findViewById(R.id.log_content);
        final RecyclerView recyclerView = findViewById(R.id.button_container);
        final List<PageFrameItem> itemList = new ArrayList<>();
        itemList.add(new ButtonItem("Create Thread", v -> createThread()));
        ViewUtils.initLinearRecyclerView(recyclerView, new MainIndexSection(itemList));
    }

    private void createThread() {
        showLog("create thread");
    }

    private void showLog(@NonNull String log) {
        if (mLogContent != null) {
            mLogContent.setText(log);
        }
    }
}
