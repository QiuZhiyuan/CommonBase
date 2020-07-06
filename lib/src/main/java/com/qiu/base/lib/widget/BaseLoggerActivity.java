package com.qiu.base.lib.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.qiu.base.lib.widget.logger.LoggerFeedSection;
import com.qiu.base.lib.widget.recycler.BaseRecyclerAdapter;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public abstract class BaseLoggerActivity extends BaseActivity {

    @NonNull
    private final LoggerFeedSection mSection = new LoggerFeedSection();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void prepareLoggerView() {
        final BaseRecyclerView loggerView = getLoggerView();
        loggerView.setLayoutManager(new LinearLayoutManager(this));
        loggerView.setAdapter(new BaseRecyclerAdapter(mSection));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        prepareLoggerView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        prepareLoggerView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        prepareLoggerView();
    }

    @NonNull
    protected abstract BaseRecyclerView getLoggerView();

    protected final void addLog(@NonNull String log) {
        mSection.addLog(log);
    }

    protected final void clearLog() {
        mSection.clear();
    }
}
