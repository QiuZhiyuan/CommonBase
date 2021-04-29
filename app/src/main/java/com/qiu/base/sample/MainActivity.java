package com.qiu.base.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.sample.aidl.AidlReceiverActivity;
import com.qiu.base.sample.ui.DataBaseActivity;
import com.qiu.base.sample.ui.GalleryActivity;
import com.qiu.base.sample.ui.KeepAliveActivity;
import com.qiu.base.sample.ui.PageFrameActivity;
import com.qiu.base.sample.ui.SettingActivity;
import com.qiu.base.sample.ui.SystemStateActivity;
import com.qiu.base.sample.ui.article.ArticleFeedActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mButtonContainer = findViewById(R.id.index_button_container);
        for (int i = 0; i < mButtonContainer.getChildCount(); i++) {
            View view = mButtonContainer.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_test_page:
                openActivity(PageFrameActivity.class);
                break;
            case R.id.show_gallery:
                openActivity(GalleryActivity.class);
                break;
            case R.id.show_article_feed:
                openActivity(ArticleFeedActivity.class);
                break;
            case R.id.show_keep_alive:
                openActivity(KeepAliveActivity.class);
                break;
            case R.id.show_system_state_listener:
                openActivity(SystemStateActivity.class);
                break;
            case R.id.show_aidl_receiver:
                openActivity(AidlReceiverActivity.class);
                break;
            case R.id.show_database:
                openActivity(DataBaseActivity.class);
                break;
            case R.id.show_net:
                break;
            case R.id.show_setting:
                openActivity(SettingActivity.class);
                break;
        }
    }

    private void openActivity(@NonNull Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
