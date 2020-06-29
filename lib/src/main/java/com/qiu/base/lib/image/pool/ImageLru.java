package com.qiu.base.lib.image.pool;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

public class ImageLru extends LruCache<String, Bitmap> {

    public interface OnRecycleListener {
        void onRecycled(@NonNull String key, @NonNull Bitmap bitmap);
    }

    @NonNull
    private final OnRecycleListener mListener;

    /**
     * @param maxSize  for caches that do not override {@link #sizeOf}, this is
     *                 the maximum number of entries in the cache. For all other caches,
     *                 this is the maximum sum of the sizes of the entries in this cache.
     * @param listener
     */
    public ImageLru(int maxSize, @NonNull OnRecycleListener listener) {
        super(maxSize);
        mListener = listener;
    }

    @Override
    public void trimToSize(int maxSize) {
        super.trimToSize(maxSize);
    }

    @Override
    public void resize(int maxSize) {
        super.resize(maxSize);
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Bitmap oldValue,
            @Nullable Bitmap newValue) {
        mListener.onRecycled(key, oldValue);
    }

    @Nullable
    @Override
    protected Bitmap create(@NonNull String key) {
        return super.create(key);
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
        return super.sizeOf(key, value);
    }
}
