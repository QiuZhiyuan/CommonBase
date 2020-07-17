package com.qiu.base.lib.image;

import android.graphics.Bitmap;

import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

public class ImageProvider {

    public static final String IMAGE_RES_KEY_START = "<ImageRes>";
    public static final String IMAGE_RES_KEY_END = "</ImageRes>";
    private static final String RES_KEY_START = "<IdRes>";
    private static final String RES_KEY_END = "</IdRes>";
    private static final String URL_KEY_START = "<ResUrl>";
    private static final String URL_KEY_END = "</ResUrl>";
    private static volatile ImageProvider sInstance;

    public static ImageProvider i() {
        if (sInstance == null) {
            synchronized (ImageProvider.class) {
                if (sInstance == null) {
                    sInstance = new ImageProvider();
                }
            }
        }
        return sInstance;
    }

    public static boolean isImageKey(@NonNull String imageKey) {
        final int keyStart = imageKey.indexOf(IMAGE_RES_KEY_START);
        final int keyEnd = imageKey.indexOf(IMAGE_RES_KEY_END);
        return keyStart < keyEnd && keyStart != -1;
    }

    public interface ImageLoadCallback {
        @MainThread
        public void onPreload();

        @MainThread
        public void onFinish(@NonNull Bitmap bitmap);

        @MainThread
        public void onError(@NonNull String errorLog);
    }


    private ImageProvider() {
    }

    public void loadImage(@NonNull String imageKey, @NonNull ImageLoadCallback callback) {
        callback.onPreload();

    }

    private String assembleImageKey(@NonNull String str) {
        return IMAGE_RES_KEY_START + str + IMAGE_RES_KEY_END;
    }

    @NonNull
    public String getImageKey(@IdRes int id) {
        return assembleImageKey(RES_KEY_START + id + RES_KEY_END);
    }

    @NonNull
    public String getImageKey(@NonNull String url) {
        return assembleImageKey(URL_KEY_START + url + URL_KEY_END);
    }
}
