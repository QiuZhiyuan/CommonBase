package com.qiu.base.lib.widget.frame;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewUtils {

    public static void initLinearRecyclerView(@NonNull RecyclerView recyclerView,
            @NonNull PageFrameSection section) {
        initLinearRecyclerView(recyclerView, section, RecyclerView.VERTICAL);
    }

    public static void initLinearRecyclerView(@NonNull RecyclerView recyclerView,
            @NonNull PageFrameSection section, @RecyclerView.Orientation int orientation) {
        final PageFrameAdapter adapter = new PageFrameAdapter(section);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(recyclerView.getContext(), orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static void smoothScrollToItemFully(@NonNull final RecyclerView parent,
            @NonNull final View item, final boolean orientation) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                if (orientation) {
                    final int itemTop = item.getTop();
                    final int itemBottom = item.getBottom();
                    final int parentHeight = parent.getHeight();
                    if (itemTop < 0) {
                        parent.smoothScrollBy(0, itemTop);
                    } else if (itemBottom > parentHeight) {
                        parent.smoothScrollBy(0, itemBottom - parentHeight);
                    }
                } else {
                    final int itemLeft = item.getLeft();
                    final int itemRight = item.getRight();
                    final int parentWidth = parent.getWidth();
                    if (itemLeft < 0) {
                        parent.smoothScrollBy(itemLeft, 0);
                    } else if (itemRight > parentWidth) {
                        parent.smoothScrollBy(itemRight - parentWidth, 0);
                    }
                }
            }
        });
    }
}
