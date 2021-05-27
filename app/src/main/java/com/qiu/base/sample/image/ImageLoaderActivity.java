package com.qiu.base.sample.image;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import com.qiu.base.lib.tools.Logger;
import com.qiu.base.sample.R;

public class ImageLoaderActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_frame);
//
//        final String[] projection = new String[]{MediaStore.Images.Media._ID,
//                                                 MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
//        final Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        final ContentResolver resolver = getContentResolver();
//        final CursorLoader cursorLoader = new CursorLoader(this, uri, projection, null, null, null);
//        final Cursor cursor = cursorLoader.loadInBackground();

        final StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            Logger.d(element.toString());
        }
    }
}
