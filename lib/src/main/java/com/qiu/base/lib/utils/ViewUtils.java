package com.qiu.base.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class ViewUtils {
    private ViewUtils() {
    }

    public static void showSoftKeyboard(@NonNull EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.requestFocus();
        imm.showSoftInput(editText, 0);
    }

    public static void hideSoftKeyboard(@NonNull Activity activity) {
        hideSoftKeyboard(activity.getWindow());
    }

    public static void hideSoftKeyboard(@NonNull Window window) {
        hideSoftKeyboard(window.getDecorView());
    }

    public static void hideSoftKeyboard(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
