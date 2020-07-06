package com.qiu.base.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qiu.base.sample.ui.GalleryActivity;
import com.qiu.base.sample.ui.KeepAliveActivity;
import com.qiu.base.sample.ui.SystemStateActivity;
import com.qiu.base.sample.ui.article.ArticleFeedActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mButtonContainer = findViewById(R.id.index_button_container);
        for (int i = 0; i < mButtonContainer.getChildCount(); i++) {
            mButtonContainer.getChildAt(i).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }

    private void openActivity(@NonNull Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
