package com.qiu.base.sample.ui;

import android.app.Activity;
import android.os.Bundle;

import com.qiu.base.lib.inter.CallBack;
import com.qiu.base.lib.tools.sys.SystemStateManager;
import com.qiu.base.lib.widget.recycler.BaseRecyclerAdapter;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;
import com.qiu.base.sample.R;
import com.qiu.base.sample.ui.article.widget.ArticleFeedSection;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SystemStateActivity extends Activity implements
        CallBack<SystemStateManager.NetState> {

    @Nullable
    private ArticleFeedSection mSection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_state);
        prepareLogger();

        SystemStateManager.i().startListenNetState(this);
        SystemStateManager.i().registerNetStateListener(this);
    }

    private void prepareLogger() {
        mSection = new ArticleFeedSection();
        final BaseRecyclerView recyclerView = findViewById(R.id.system_state_logger);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseRecyclerAdapter(mSection));
    }

    @Override
    protected void onDestroy() {
        SystemStateManager.i().stopListenNetState(this);
        SystemStateManager.i().unregisterNetStateListener(this);
        super.onDestroy();
    }

    @Override
    public void onCall(SystemStateManager.NetState netState) {
        if (mSection != null) {
            mSection.addLog("NetWork state change Name : " + netState.typeName + " type : "
                    + netState.type);
        }
    }
}
