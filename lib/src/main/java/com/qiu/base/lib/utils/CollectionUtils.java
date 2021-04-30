package com.qiu.base.lib.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <E> List<E> createSingleItemList(E item) {
        final List<E> eList = new ArrayList<>();
        eList.add(item);
        return eList;
    }
}
