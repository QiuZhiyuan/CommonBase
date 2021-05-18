package com.qiu.base.sample;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.ViewUtils;
import com.qiu.base.sample.aidl.AidlReceiverActivity;
import com.qiu.base.sample.thread.ThreadActivity;
import com.qiu.base.sample.ui.DataBaseActivity;
import com.qiu.base.sample.ui.GalleryActivity;
import com.qiu.base.sample.ui.KeepAliveActivity;
import com.qiu.base.sample.ui.PageFrameActivity;
import com.qiu.base.sample.ui.SettingActivity;
import com.qiu.base.sample.ui.SystemStateActivity;
import com.qiu.base.sample.ui.article.ArticleFeedActivity;
import com.qiu.base.sample.ui.index.GuidePageItem;
import com.qiu.base.sample.ui.index.MainIndexSection;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView buttonContainer = findViewById(R.id.index_button_container);

        final List<PageFrameItem> itemList = new ArrayList<>();
        itemList.add(new GuidePageItem("test", PageFrameActivity.class));
        itemList.add(new GuidePageItem("Thread Page", ThreadActivity.class));
        itemList.add(new GuidePageItem("Show Gallery", GalleryActivity.class));
        itemList.add(new GuidePageItem("Show Article Feed", ArticleFeedActivity.class));
        itemList.add(new GuidePageItem("Show Keep Alive", KeepAliveActivity.class));
        itemList.add(new GuidePageItem("Show System State Listener", SystemStateActivity.class));
        itemList.add(new GuidePageItem("Show AIDL Receiver", AidlReceiverActivity.class));
        itemList.add(new GuidePageItem("Show Database", DataBaseActivity.class));
        itemList.add(new GuidePageItem("Show Setting", SettingActivity.class));

        final MainIndexSection section = new MainIndexSection(itemList);
        ViewUtils.initLinearRecyclerView(buttonContainer, section, RecyclerView.VERTICAL);
    }
}
