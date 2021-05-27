package com.qiu.base.sample.image;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qiu.base.lib.tools.UniqueId;
import com.qiu.base.lib.widget.frame.PageFrameItem;
import com.qiu.base.lib.widget.frame.PageFrameItemViewHolder;
import com.qiu.base.lib.widget.frame.PageFrameSection;
import com.qiu.base.lib.widget.frame.ViewHolderFactory;
import com.qiu.base.sample.R;

public class ImageSection extends PageFrameSection {

    public static class ImageItem extends PageFrameItem {
        public static final int ID = UniqueId.get();

        @Nullable
        private String mImageUrl;

        @Nullable
        public String getImageUrl() {
            return mImageUrl;
        }

        public void setImageUrl(@Nullable String imageUrl) {
            mImageUrl = imageUrl;
        }

        @Override
        public int getId() {
            return ID;
        }
    }

    public static class ImageItemViewHolder extends PageFrameItemViewHolder {

        @NonNull
        private final ImageView mImageView;

        public ImageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_content);
        }

        @Override
        protected void onBind(@NonNull PageFrameItem item) {

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
            if (itemType == ImageItem.ID) {
                return new ImageItemViewHolder(findLayoutById(R.layout.item_image, parent));
            }
            return null;
        }
    };

    public ImageSection() {
        super();

    }

    @NonNull
    @Override
    protected ViewHolderFactory getViewHolderFactory() {
        return mViewHolderFactory;
    }
}
