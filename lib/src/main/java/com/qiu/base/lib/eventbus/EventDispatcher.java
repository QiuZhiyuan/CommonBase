package com.qiu.base.lib.eventbus;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;

public class EventDispatcher {
    private EventDispatcher() {

    }

    public static void post(@NonNull Object event) {
        EventBus.getDefault().post(event);
    }

    public static void register(@NonNull Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(@NonNull Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
}
