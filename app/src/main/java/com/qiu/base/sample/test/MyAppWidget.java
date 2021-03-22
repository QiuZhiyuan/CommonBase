package com.qiu.base.sample.test;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.impl.Callback;
import com.qiu.base.lib.tools.Logger;
import com.qiu.base.lib.utils.App;
import com.qiu.base.sample.MainActivity;
import com.qiu.base.sample.R;

public class MyAppWidget extends AppWidgetProvider implements Callback<Long> {

    private static final String TAG = MyAppWidget.class.getSimpleName();

    private static class UpdateHandler extends Handler {
        private static final int DURATION = 1000;

        @Nullable
        private com.qiu.base.lib.impl.Callback<Long> mCallback;

        private void setCallback(@Nullable com.qiu.base.lib.impl.Callback<Long> callback) {
            mCallback = callback;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Logger.d(TAG, "handleMessage");
            if (mCallback != null) {
                mCallback.onCall(System.currentTimeMillis());
                sendEmptyMessageDelayed(0, DURATION);
            }
        }

        public boolean isSchedule() {
            return hasMessages(0);
        }
    }

    @NonNull
    private final AppWidgetManager mWidgetManager =
            AppWidgetManager.getInstance(App.i().getApplicationContext());
    @NonNull
    private final RemoteViews mRemoteViews =
            new RemoteViews(App.i().getPackageName(), R.layout.app_widget_my_update);

    @NonNull
    private final UpdateHandler mHandler = new UpdateHandler();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Logger.d(TAG, "onReceive");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Logger.d(TAG, "onUpdate");
        if (!mHandler.isSchedule()) {
            mHandler.setCallback(this);
            mHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Logger.d(TAG, "onDeleted");
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Logger.d(TAG, "onEnable");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Logger.d(TAG, "onDisabled");
        mHandler.setCallback(null);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Logger.d(TAG, "onAppWidgetOptionsChanged");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Logger.d(TAG, "onRestored");
    }

    @Override
    public void onCall(Long aLong) {
        Logger.d(TAG, "onCall:" + aLong);
        mRemoteViews.setTextViewText(R.id.current_time, aLong.toString());
        //这里获得当前的包名，并且用AppWidgetManager来向NewAppWidget.class发送广播。
        ComponentName cn = new ComponentName(App.i().getApplicationContext(), MyAppWidget.class);
        mWidgetManager.updateAppWidget(cn, mRemoteViews);

    }
}
