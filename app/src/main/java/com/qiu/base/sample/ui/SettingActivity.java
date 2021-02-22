package com.qiu.base.sample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.settting.ConfigProvider;
import com.qiu.base.lib.widget.BaseActivity;
import com.qiu.base.sample.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static class SimpleConfigHelper extends ConfigProvider {

        public static final StringConfig STRING_CONFIG =
                new StringConfig("string_config", "hello_world");

        public static final IntConfig INT_CONFIG = new IntConfig("int_config", 1);

        public static final BooleanConfig BOOLEAN_CONFIG =
                new BooleanConfig("boolean_config", false);

        public static final LongConfig LONG_CONFIG = new LongConfig("long_config", 0L);

        private volatile static SimpleConfigHelper sInstance;

        private SimpleConfigHelper() {
            addSavableConfig(STRING_CONFIG);
            addSavableConfig(INT_CONFIG);
            addSavableConfig(BOOLEAN_CONFIG);
            addSavableConfig(LONG_CONFIG);
        }

        @NonNull
        public static SimpleConfigHelper i() {
            if (sInstance == null) {
                synchronized (SimpleConfigHelper.class) {
                    if (sInstance == null) {
                        sInstance = new SimpleConfigHelper();
                    }
                }
            }
            return sInstance;
        }

        @NonNull
        @Override
        protected String getStorageKey() {
            return "simple_config_helper";
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.change_configs).setOnClickListener(this);
        findViewById(R.id.show_configs).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleConfigHelper.i().loadConfigSet();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SimpleConfigHelper.i().saveConfigSet();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.change_configs) {
            SimpleConfigHelper.STRING_CONFIG.setValue("hello android");
            SimpleConfigHelper.INT_CONFIG.setValue(100);
            SimpleConfigHelper.BOOLEAN_CONFIG.setValue(true);
            SimpleConfigHelper.LONG_CONFIG.setValue(System.currentTimeMillis());
        } else if (id == R.id.show_configs) {
            final TextView textView = findViewById(R.id.config_board);
            textView.setText("string:" + SimpleConfigHelper.STRING_CONFIG.getValue() +
                    "\nint:" + SimpleConfigHelper.INT_CONFIG.getValue() +
                    "\nboolean:" + SimpleConfigHelper.BOOLEAN_CONFIG.getValue() +
                    "\nlong:" + SimpleConfigHelper.LONG_CONFIG.getValue());
        }
    }
}
