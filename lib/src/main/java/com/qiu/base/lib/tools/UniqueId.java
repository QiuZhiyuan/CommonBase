package com.qiu.base.lib.tools;

import java.util.HashSet;
import java.util.Set;

public class UniqueId {

    private static final Set<Integer> sUniqueIdPool = new HashSet<>();
    private static int count = Integer.MIN_VALUE;

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
