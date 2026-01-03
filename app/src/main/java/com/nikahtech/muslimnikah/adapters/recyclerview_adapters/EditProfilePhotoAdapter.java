package com.nikahtech.muslimnikah.adapters.recyclerview_adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.models.editprofile.ProfilePhotoItem;

import java.util.List;

public class EditProfilePhotoAdapter
        extends RecyclerView.Adapter<EditProfilePhotoAdapter.PhotoVH> {

    private final Context context;
    private final List<ProfilePhotoItem> items;

    // ---------------- LISTENERS ----------------

    public interface OnAddPhotoClick {
        void onAddPhoto();
    }

    public interface OnProfilePhotoClick {
        void onProfilePhoto(String url);
    }

    public interface OnGalleryPhotoClick {
        void onGalleryPhoto(String url, int position);
    }

    private final OnAddPhotoClick addPhotoListener;
    private final OnProfilePhotoClick profilePhotoListener;
    private final OnGalleryPhotoClick galleryPhotoListener;

    // ---------------- CONSTRUCTOR ----------------

    public EditProfilePhotoAdapter(
            Context context,
            List<ProfilePhotoItem> items,
            OnAddPhotoClick addPhotoListener,
            OnProfilePhotoClick profilePhotoListener,
            OnGalleryPhotoClick galleryPhotoListener
    ) {
        this.context = context;
        this.items = items;
        this.addPhotoListener = addPhotoListener;
        this.profilePhotoListener = profilePhotoListener;
        this.galleryPhotoListener = galleryPhotoListener;
    }

    // ---------------- VIEW HOLDER ----------------

    @NonNull
    @Override
    public PhotoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RoundedImageView iv = new RoundedImageView(context);
        iv.setLayoutParams(new ViewGroup.LayoutParams(dp(80), dp(80)));
        iv.setCornerRadius(dp(12));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setBorderWidth((float) dp(2));
        iv.setPadding(dp(2), dp(2), dp(2), dp(2));

        return new PhotoVH(iv);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVH holder, int position) {

        ProfilePhotoItem item = items.get(position);
        RoundedImageView iv = holder.imageView;

        iv.setOnClickListener(null); // 🔒 important to avoid recycled clicks

        if (item.getType() == ProfilePhotoItem.TYPE_ADD) {
            bindAddPhoto(iv);
            return;
        }

        bindPhoto(iv, item, position);
    }

    private void bindAddPhoto(RoundedImageView iv) {
        iv.setImageResource(R.drawable.add_photo_placeholder);
        iv.setBorderColor(
                ContextCompat.getColor(context, R.color.colorTextMuted)
        );

        iv.setOnClickListener(v -> {
            if (addPhotoListener != null) {
                addPhotoListener.onAddPhoto();
            }
        });
    }

    private void bindPhoto(
            RoundedImageView iv,
            ProfilePhotoItem item,
            int position
    ) {
        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.user_placeholder)
                .into(iv);

        // Profile photo = index 0
        boolean isProfilePhoto = position == 0;

        iv.setBorderColor(
                ContextCompat.getColor(
                        context,
                        isProfilePhoto
                                ? R.color.colorPrimary
                                : R.color.colorTextMuted
                )
        );

        iv.setOnClickListener(v -> {
            if (isProfilePhoto) {
                if (profilePhotoListener != null) {
                    profilePhotoListener.onProfilePhoto(item.getImageUrl());
                }
            } else {
                if (galleryPhotoListener != null) {
                    galleryPhotoListener.onGalleryPhoto(
                            item.getImageUrl(),
                            position
                    );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ---------------- VIEW HOLDER ----------------

    static class PhotoVH extends RecyclerView.ViewHolder {
        RoundedImageView imageView;

        PhotoVH(@NonNull View itemView) {
            super(itemView);
            imageView = (RoundedImageView) itemView;
        }
    }

    // ---------------- UTIL ----------------

    private int dp(int dp) {
        return Math.round(
                dp * context.getResources().getDisplayMetrics().density
        );
    }


}
