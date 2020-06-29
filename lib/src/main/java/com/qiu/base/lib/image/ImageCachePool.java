package com.qiu.base.lib.image;

import android.graphics.Bitmap;

import com.qiu.base.lib.image.pool.ImageLru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Set three level cache for images
 * lru weak reference database
 * get Thumbnail
 */
public class ImageCachePool {

    @NonNull
    private final ImageLru mImageLru;

    public ImageCachePool() {
        mImageLru = new ImageLru(1000, new ImageLru.OnRecycleListener() {
            @Override
            public void onRecycled(@NonNull String key, @NonNull Bitmap bitmap) {

            }
        });
    }

    @Nullable
    public Bitmap getImage(@NonNull String key) {
        return null;
    }

    @Nullable
    public Bitmap getThumbnail(@NonNull String key, int width, int height) {
        return null;
    }

    public void putImage(@NonNull String key, @NonNull Bitmap value) {
        mImageLru.put(key, value);
    }
}
