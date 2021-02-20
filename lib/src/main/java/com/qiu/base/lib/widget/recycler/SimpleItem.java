package com.qiu.base.lib.widget.recycler;

import com.qiu.base.lib.tools.UniqueId;

public class SimpleItem extends BaseRecyclerItem {

    public static final int ID = UniqueId.get();

    @Override
    public int getId() {
        return ID;
    }
}
