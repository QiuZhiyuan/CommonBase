package com.qiu.base.sample.ui.index;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.utils.App;

public class GuidePageItem extends ButtonItem {
    public static final int ID = UniqueId.get();

    public GuidePageItem(@NonNull String buttonTitle,
            @NonNull Class<? extends Activity> targetActivity) {
        super(buttonTitle, v -> App.i().getApplicationContext()
                .startActivity(new Intent(App.i().getApplicationContext(), targetActivity)));
    }

    @Override
    public int getId() {
        return ID;
    }
}
