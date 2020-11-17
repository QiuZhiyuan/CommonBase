package com.qiu.base.sample.ui.article;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.qiu.base.lib.data.db.TableBaseEntry;
import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.widget.recycler.BaseRecyclerAdapter;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.article.adapter.ArticleFeedSection;
import com.qiu.base.sample.db.SimplePersonEntry;
import com.qiu.base.sample.db.SimpleTableStorageImpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

public class ArticleFeedActivity extends Activity {

    private static final String TAG = ArticleFeedActivity.class.getSimpleName();

    @Nullable
    private BaseRecyclerView mRecyclerView;
    @NonNull
    private ArticleFeedSection mSection = new ArticleFeedSection();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_feed);
        prepareRecyclerView();
    }

    private void prepareRecyclerView() {
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
        findViewById(R.id.btn_create_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDataBase();
            }
        });

        mRecyclerView = findViewById(R.id.article_feed_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BaseRecyclerAdapter(mSection));
    }

    private void createDataBase() {
        SimplePersonEntry entry = new SimplePersonEntry("qiu", 28);
        SimpleTableStorageImpl.instance().insert(entry);
        SimpleTableStorageImpl.instance().queryAll(new Callback<List<? extends TableBaseEntry>>() {
            @Override
            public void onCall(List<? extends TableBaseEntry> simpleDbEntries) {
                mSection.addLog("simpleDbEntries size:" + simpleDbEntries.size());
                for (TableBaseEntry entryQuery : simpleDbEntries) {
                    mSection.addLog("entryQuery:" + entryQuery.toString());
                }
            }
        }, SimplePersonEntry.class);
    }
}
