package com.qiu.base.lib.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <E> List<E> singletonList(E item) {
        final List<E> eList = new ArrayList<>();
        eList.add(item);
        return eList;
    }

    public static <E> List<E> createList(E... items) {
        return Arrays.asList(items);
    }
}
