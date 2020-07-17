package com.qiu.base.lib.widget.logger;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.qiu.base.lib.R;
import com.qiu.base.lib.widget.recycler.BaseRecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class CommonLoggerActivity extends BaseLoggerActivity {

    protected static class BtnEntry {

        @NonNull
        public final String text;
        @NonNull
        public final View.OnClickListener onClickListener;

        public BtnEntry(@NonNull String text, @NonNull View.OnClickListener onClickListener) {
            this.text = text;
            this.onClickListener = onClickListener;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_logger);
        prepareSwitchContainer();
    }

    private void prepareSwitchContainer() {
        final LinearLayout switchContainer = findViewById(R.id.switch_container);
        List<BtnEntry> entryList = createBtnEntryList();
        if (entryList == null) {
            return;
        }
        for (BtnEntry entry : entryList) {
            final Button button = createButton(entry);
            switchContainer.addView(button);
        }
    }

    @NonNull
    protected Button createButton(@NonNull BtnEntry entry) {
        final Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(entry.text);
        button.setOnClickListener(entry.onClickListener);
        return button;
    }

    @Nullable
    protected abstract List<BtnEntry> createBtnEntryList();

    @NonNull
    @Override

    protected BaseRecyclerView getLoggerView() {
        return findViewById(R.id.common_logger_view);
    }
}
