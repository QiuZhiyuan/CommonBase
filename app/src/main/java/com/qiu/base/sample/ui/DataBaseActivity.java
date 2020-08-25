package com.qiu.base.sample.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.data.db.TableBaseEntry;
import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.widget.recycler.BaseRecyclerAdapter;
import com.qiu.base.sample.R;
import com.qiu.base.sample.db.SimpleDbEntry;
import com.qiu.base.sample.db.SimpleTableStorageImpl;
import com.qiu.base.sample.ui.article.adapter.ArticleFeedSection;

import java.util.ArrayList;
import java.util.List;

public class DataBaseActivity extends Activity implements View.OnClickListener {

    @NonNull
    private ArticleFeedSection mSection = new ArticleFeedSection();
    @NonNull
    private List<SimpleDbEntry> mEntryList = new ArrayList<>();
    @NonNull
    private String[] mEntryNameList = new String[]{"qiu", "wang", "liu", "li", "hong"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        LinearLayout mButtonContainer = findViewById(R.id.index_button_container);
        for (int i = 0; i < mButtonContainer.getChildCount(); i++) {
            View view = mButtonContainer.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
        prepareLogFeed();
    }

    private void prepareLogFeed() {
        final RecyclerView recyclerView = findViewById(R.id.log_feed_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseRecyclerAdapter(mSection));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_entry:
                addEntry();
                break;
            case R.id.btn_delete_entry:
                deleteEntry();
                break;
            case R.id.btn_update_entry:
                updateEntry();
                break;
            case R.id.btn_query_entry:
                queryEntry();
                break;
        }
    }

    private void addEntry() {
        final SimpleDbEntry entry = createNewEntry();
        mEntryList.add(entry);
        SimpleTableStorageImpl.instance().insert(entry);
        mSection.addLog("add:");
        mSection.addLog(entry.toString());

    }

    private void deleteEntry() {
        final SimpleDbEntry entry = randomGetEntryFromList();
        if (entry != null) {
            SimpleTableStorageImpl.instance().delete(entry);
            mSection.addLog("delete:");
            mSection.addLog(entry.toString());
        } else {
            mSection.addLog("delete: entry is null");
        }


    }

    private void updateEntry() {
        final SimpleDbEntry entry = randomGetEntryFromList();
        if (entry != null) {
            mSection.addLog("update:" + entry.getName());
            entry.setName(entry.getName() + " x");
            SimpleTableStorageImpl.instance().update(entry);
        }
    }

    private void queryEntry() {
        SimpleTableStorageImpl.instance().queryAll(new Callback<List<SimpleDbEntry>>() {
            @Override
            public void onCall(List<SimpleDbEntry> simpleDbEntries) {
                mEntryList.clear();
                mEntryList.addAll(simpleDbEntries);
                mSection.addLog("query:");
                for (TableBaseEntry entry : mEntryList) {
                    if (entry instanceof SimpleDbEntry) {
                        mSection.addLog(entry.toString());
                    }
                }
            }
        });
    }

    @NonNull
    private SimpleDbEntry createNewEntry() {
        SimpleDbEntry entry = new SimpleDbEntry();
        entry.setAge((int) (Math.random() * 100));
        entry.setName(mEntryNameList[(int) (Math.random() * (mEntryNameList.length - 1))]);
        return entry;
    }

    @Nullable
    private SimpleDbEntry randomGetEntryFromList() {
        if (mEntryList.isEmpty()) {
            return null;
        }
        final int index = (int) (Math.random() * (mEntryList.size() - 1));
        return mEntryList.get(index);
    }

}