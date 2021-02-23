package com.qiu.base.lib.widget;

import android.view.View;

import androidx.annotation.NonNull;

public abstract class InitViewHelper {

    @NonNull
    protected final View mRoot;

    public InitViewHelper(@NonNull View root) {
        mRoot = root;
    }
}
