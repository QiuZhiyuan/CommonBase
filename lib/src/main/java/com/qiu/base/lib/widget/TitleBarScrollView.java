package com.qiu.base.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TitleBarScrollView extends ScrollView {

    private float mStartX;
    private float mStartY;
    @Nullable
    private View mTitleBarView;

    public TitleBarScrollView(Context context) {
        super(context);
    }

    public TitleBarScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBarScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addTitleBarView(@NonNull View titleBarView) {
        mTitleBarView = titleBarView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                return false;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
