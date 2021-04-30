package com.qiu.base.sample.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.widget.frame.ViewUtils;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.page_frame.SimplePageFrameSection;

public class PageFrameActivity extends Activity {

    @NonNull
    private final SimplePageFrameSection mSection = new SimplePageFrameSection();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_frame);

        mSection.onAttached();
        final RecyclerView recyclerView = findViewById(R.id.content);
        ViewUtils.initLinearRecyclerView(recyclerView, mSection, RecyclerView.VERTICAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSection.onDetached();
    }
}
