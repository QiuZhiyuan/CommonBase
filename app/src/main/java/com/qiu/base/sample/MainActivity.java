package com.qiu.base.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qiu.base.lib.toast.ToastUtils;
import com.qiu.base.sample.ui.GalleryActivity;
import com.qiu.base.sample.ui.article.ArticleFeedActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_gallery).setOnClickListener(this);
        findViewById(R.id.show_article_feed).setOnClickListener(this);
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
        }
    }

    private void openActivity(@NonNull Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
