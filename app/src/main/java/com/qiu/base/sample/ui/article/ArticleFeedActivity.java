package com.qiu.base.sample.ui.article;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.qiu.base.lib.widget.recycler.BaseRecyclerAdapter;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.article.db.ArticleDataBaseHelper;
import com.qiu.base.sample.ui.article.adapter.ArticleFeedSection;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ArticleFeedActivity extends Activity {

    @Nullable
    private BaseRecyclerView mRecyclerView;
    @Nullable
    private ArticleFeedSection mSection;
    @Nullable
    private ArticleDataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_feed);

        mSection = new ArticleFeedSection();
        mDataBaseHelper = new ArticleDataBaseHelper(this);
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {
        if (mSection == null) {
            return;
        }
        findViewById(R.id.btn_add_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSection.addLog("this is a log");
            }
        });
        findViewById(R.id.btn_add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSection.addImage(BitmapFactory
                        .decodeResource(getResources(), R.drawable.demo_article_image));
            }
        });
        findViewById(R.id.btn_clear_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSection.clear();
            }
        });

        mRecyclerView = findViewById(R.id.article_feed_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BaseRecyclerAdapter(mSection));
    }

    private void createDataBase() {
    }
}
