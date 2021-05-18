package com.qiu.base.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.lib.widget.frame.PageFrameSection;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;
import com.qiu.base.sample.R;
import com.qiu.base.sample.aidl.AidlReceiverActivity;
import com.qiu.base.sample.ui.DataBaseActivity;
import com.qiu.base.sample.ui.GalleryActivity;
import com.qiu.base.sample.ui.KeepAliveActivity;
import com.qiu.base.sample.ui.PageFrameActivity;
import com.qiu.base.sample.ui.SettingActivity;
import com.qiu.base.sample.ui.SystemStateActivity;
import com.qiu.base.sample.ui.article.ArticleFeedActivity;

import java.util.ArrayList;
import java.util.List;

public class MainIndexSection extends PageFrameSection {

    private static class IndexItem extends PageFrameItem {

        private static final int ID = UniqueId.get();

        @NonNull
        public final String buttonTitle;
        @NonNull
        public final Class<? extends Activity> targetActivity;

        private IndexItem(@NonNull String buttonTitle,
                @NonNull Class<? extends Activity> targetActivity) {
            this.buttonTitle = buttonTitle;
            this.targetActivity = targetActivity;
        }

        @Override
        public int getId() {
            return ID;
        }
    }

    private static class IndexItemViewHolder extends PageFrameItemViewHolder {

        @NonNull
        private final Button mIndexButton;

        public IndexItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mIndexButton = itemView.findViewById(R.id.index_button);
            mIndexButton.setOnClickListener(v -> {
                if (mItem instanceof IndexItem) {
                    final IndexItem indexItem = (IndexItem) mItem;
                    final Context context = itemView.getContext();
                    context.startActivity(new Intent(context, indexItem.targetActivity));
                }
            });
        }

        @Override
        protected void onBind(@NonNull PageFrameItem item) {
            if (item instanceof IndexItem) {
                final IndexItem indexItem = (IndexItem) item;
                mIndexButton.setText(indexItem.buttonTitle);
            }
        }

        @Override
        protected void onUnbind(@Nullable PageFrameItem item) {

        }
    }

    @NonNull
    private final ViewHolderFactory mViewHolderFactory = new ViewHolderFactory() {
        @Nullable
        @Override
        public PageFrameItemViewHolder createItemViewHolder(@NonNull ViewGroup parent,
                int itemType) {
            if (itemType == IndexItem.ID) {
                return new IndexItemViewHolder(findLayoutById(R.layout.item_index_button, parent));
            }
            return null;
        }
    };

    public MainIndexSection() {
        super();
        final List<PageFrameItem> itemList = new ArrayList<>();
        itemList.add(new IndexItem("test", PageFrameActivity.class));
        itemList.add(new IndexItem("Show Gallery", GalleryActivity.class));
        itemList.add(new IndexItem("Show Article Feed", ArticleFeedActivity.class));
        itemList.add(new IndexItem("Show Keep Alive", KeepAliveActivity.class));
        itemList.add(new IndexItem("Show System State Listener", SystemStateActivity.class));
        itemList.add(new IndexItem("Show AIDL Receiver", AidlReceiverActivity.class));
        itemList.add(new IndexItem("Show Database", DataBaseActivity.class));
        itemList.add(new IndexItem("Show Setting", SettingActivity.class));
        addItemList(itemList);
    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mViewHolderFactory;
    }
}
