package com.qiu.base.lib.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IndexMap<K, V> {

    public static class Entry<K, V> {
        @NonNull
        public final K k;
        @NonNull
        public final V v;
        @Nullable
        public Entry<K, V> next;

        public Entry(@NonNull K k, @NonNull V v) {
            this.k = k;
            this.v = v;
        }
    }

    public void put(@NonNull K key, @NonNull V value) {

    }

}
