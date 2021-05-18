package com.qiu.base.sample;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qiu.base.lib.widget.frame.ViewUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView buttonContainer = findViewById(R.id.index_button_container);
        final MainIndexSection section = new MainIndexSection();
        ViewUtils.initLinearRecyclerView(buttonContainer, section, RecyclerView.VERTICAL);
    }
}
