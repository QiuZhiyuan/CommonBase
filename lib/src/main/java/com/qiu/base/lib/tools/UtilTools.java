package com.qiu.base.lib.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UtilTools {
    private UtilTools() {

    }

    public static Bitmap transDrawableToBitmap(@NonNull Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, height);
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean equals(@Nullable String str1, @Nullable String str2) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(str1, str2);
        } else {
            return (str1 == str2) || (str1 != null && str1.equals(str2));
        }
    }
}
