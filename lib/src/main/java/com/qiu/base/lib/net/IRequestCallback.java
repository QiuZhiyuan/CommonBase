package com.qiu.base.lib.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface IRequestCallback {
    void onRequestStart();

    void onRequestResponse(@Nullable String response);

    void onRequestError(@NonNull RequestException exception);

    void onRequestFinish();
}
