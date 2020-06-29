package com.qiu.base.lib.image.pool;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import androidx.annotation.NonNull;

public class ImageWeakMap {
    @NonNull
    private final WeakHashMap<String, Bitmap> mWeakHashMap = new WeakHashMap<>();

    public ImageWeakMap(){
    }
}
