package com.qiu.base.sample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.qiu.base.lib.settting.AbsConfigProvider;
import com.qiu.base.lib.utils.App;
import com.qiu.base.lib.widget.BaseActivity;
import com.qiu.base.sample.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static class SimpleConfigProvider extends AbsConfigProvider {


        public static class StringConfig extends AbsConfig<String> {

            public StringConfig(@NonNull String key, @Nullable String defValue) {
                super(key, defValue);
            }

            public StringConfig(@NonNull String key, @StringRes int defId) {
                super(key, App.i().getString(defId));
            }
        }

        public static class BooleanConfig extends AbsConfig<Boolean> {

            public BooleanConfig(@NonNull String key, @Nullable Boolean defValue) {
                super(key, defValue);
            }
        }

        public static class IntConfig extends AbsConfig<Integer> {

            public IntConfig(@NonNull String key, @Nullable Integer defValue) {
                super(key, defValue);
            }
        }

        public static class LongConfig extends AbsConfig<Long> {

            public LongConfig(@NonNull String key, @Nullable Long defValue) {
                super(key, defValue);
            }
        }

        public static final StringConfig STRING_CONFIG =
                new StringConfig("string_config", "hello_world");

        public static final IntConfig INT_CONFIG = new IntConfig("int_config", 1);

        public static final BooleanConfig BOOLEAN_CONFIG =
                new BooleanConfig("boolean_config", false);

        public static final LongConfig LONG_CONFIG = new LongConfig("long_config", 0L);

        private volatile static SimpleConfigProvider sInstance;

        private SimpleConfigProvider() {
            addSavableConfig(STRING_CONFIG);
            addSavableConfig(INT_CONFIG);
            addSavableConfig(BOOLEAN_CONFIG);
            addSavableConfig(LONG_CONFIG);
        }

        @NonNull
        public static SimpleConfigProvider i() {
            if (sInstance == null) {
                synchronized (SimpleConfigProvider.class) {
                    if (sInstance == null) {
                        sInstance = new SimpleConfigProvider();
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
        SimpleConfigProvider.i().loadConfigSet();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SimpleConfigProvider.i().saveConfigSet();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.change_configs) {
            SimpleConfigProvider.STRING_CONFIG.setValue("hello android");
            SimpleConfigProvider.INT_CONFIG.setValue(100);
            SimpleConfigProvider.BOOLEAN_CONFIG.setValue(true);
            SimpleConfigProvider.LONG_CONFIG.setValue(System.currentTimeMillis());
        } else if (id == R.id.show_configs) {
            final TextView textView = findViewById(R.id.config_board);
            textView.setText("string:" + SimpleConfigProvider.STRING_CONFIG.getValue() +
                    "\nint:" + SimpleConfigProvider.INT_CONFIG.getValue() +
                    "\nboolean:" + SimpleConfigProvider.BOOLEAN_CONFIG.getValue() +
                    "\nlong:" + SimpleConfigProvider.LONG_CONFIG.getValue());
        }
    }
}
