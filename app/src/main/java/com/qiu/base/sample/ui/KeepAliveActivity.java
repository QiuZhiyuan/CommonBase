package com.qiu.base.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qiu.base.lib.keepalive.OnePixelActivity;
import com.qiu.base.sample.R;

import androidx.annotation.Nullable;

public class KeepAliveActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_alive);
    }

    public void startOnePixelActivity(View view) {
        startActivity(new Intent(this, OnePixelActivity.class));
        finish();
    }
}
