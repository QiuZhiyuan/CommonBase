package com.qiu.base.lib.widget.logger;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qiu.base.lib.data.ListEntry;
import com.qiu.base.lib.widget.BaseActivity;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;

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
        loggerView.setAdapter(mSection.createDefaultAdapter());
        if (isAutoScroll()) {
            mSection.setDataChangeListener(new ListEntry.ListChangeListener() {
                @Override
                public void onListChanged() {
                    loggerView.scrollToPosition(mSection.getItemCount() - 1);
                }
            });
        }
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

    protected boolean isAutoScroll() {
        return true;
    }

    protected final void addLog(@NonNull String log) {
        mSection.addLog(log);
    }

    protected final void clearLog() {
        mSection.clear();
    }
}
