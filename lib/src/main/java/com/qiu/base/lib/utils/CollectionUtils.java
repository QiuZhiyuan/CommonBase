package com.qiu.base.lib.utils;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.function.Predicate;

public class CollectionUtils {
    private CollectionUtils(){
    }

    public static <T> boolean removeIf(@NonNull Iterable<T> c, @NonNull Predicate<T> filter) {
        boolean removed = false;
        final Iterator<T> each = c.iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

}
