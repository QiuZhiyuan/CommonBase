package com.qiu.base.lib.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;

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

    public static String getDateString(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    public static int getPid() {
        return android.os.Process.myPid();
    }

    public static String toJson(@NonNull Object obj) {
        return JSON.toJSONString(obj);
    }

    @NonNull
    public static <T> Type getClass(@NonNull T t) {
        Type type = t.getClass().getGenericSuperclass();
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
