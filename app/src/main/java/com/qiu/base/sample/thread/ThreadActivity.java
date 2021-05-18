package com.qiu.base.sample.thread;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.thread.ThreadUtils;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.ViewUtils;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.index.ButtonItem;
import com.qiu.base.sample.ui.index.LogItem;
import com.qiu.base.sample.ui.index.MainIndexSection;

import java.util.ArrayList;
import java.util.List;

public class ThreadActivity extends Activity {

    @NonNull
    private final MainIndexSection mSection = new MainIndexSection(null);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_and_log);
        final RecyclerView recyclerView = findViewById(R.id.button_container);

        final List<PageFrameItem> itemList = new ArrayList<>();
        itemList.add(new ButtonItem("Clear Log", v -> mSection.clearLog()));
        itemList.add(new ButtonItem("Create Thread", v -> createThread()));
        itemList.add(new ButtonItem("Add new thread to pool", v -> addNewThreadToPool()));

        mSection.addItemList(itemList);
        ViewUtils.initLinearRecyclerView(recyclerView, mSection);
    }

    private void createThread() {
        showLog("create thread");
        final ThreadLocal<String> strThreadLocal = new ThreadLocal<>();
        strThreadLocal.set("Hello World");
        showLog(strThreadLocal.get());
        new Thread(() -> showLog(strThreadLocal.get())).start();
    }

    private void addNewThreadToPool() {
        final LogItem item = new LogItem("Count");
        mSection.addLog(item);
        new Thread(() -> {
            int count = 0;
            while (mSection.contain(item)) {
                item.logContent = "Count:" + count++ +" "+item.isBind();
                item.callDataUpdate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    showLog("Interrupted");
                }
            }
        }).start();

    }

    private void showLog(@NonNull String log) {
        ThreadUtils.i().postMain(() -> {
            mSection.addLog(log);
        });
    }
}
