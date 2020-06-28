package com.qiu.base.lib.tools;

import java.util.HashSet;
import java.util.Set;

public class UniqueId {

    private static Set<Integer> sUniqueIdPool = new HashSet<>();
    private static int count = 0;

    private UniqueId() {
    }

    public static int get() {
        return createUniqueId();
    }

    public static boolean hasId(int id) {
        return sUniqueIdPool.contains(id);
    }

    private static int createUniqueId() {
        count++;
        sUniqueIdPool.add(count);
        return count;
    }
}
