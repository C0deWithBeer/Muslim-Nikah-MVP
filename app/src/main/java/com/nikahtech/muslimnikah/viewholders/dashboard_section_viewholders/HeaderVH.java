package com.nikahtech.muslimnikah.viewholders.dashboard_section_viewholders;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nikahtech.muslimnikah.activities.EditProfileActivity;
import com.nikahtech.muslimnikah.databinding.ItemDashHeaderBinding;
import com.nikahtech.muslimnikah.models.dashboard_sections.Header;

public class HeaderVH extends RecyclerView.ViewHolder{

    ItemDashHeaderBinding binding;
    private boolean isExpanded = false;

    public HeaderVH(ItemDashHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Header model, Context context) {

        Log.d("TAG", "Header: profileImg: "+model.getProfilePic());
        Glide.with(context)
                .load(model.getProfilePic())
                .into(binding.profilePic);
        binding.nameTxt.setText(model.getName());

        binding.dropdownImg.setOnClickListener(v -> toggleDropDown());

        binding.progressBar.setMax(100);
        binding.progressBar.setProgress(48, true);

        binding.completeBtn.setOnClickListener(view -> openEditProfile(context));
    }

    private void openEditProfile(Context context) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        context.startActivity(intent);
    }

    private void toggleDropDown() {
        if (isExpanded) {
            collapseView(binding.completeProfileSection);
            rotateIcon(binding.dropdownImg, 180f, 0f);
            isExpanded = false;
        } else {
            expandView(binding.completeProfileSection);
            rotateIcon(binding.dropdownImg, 0f, 180f);
            isExpanded = true;
        }
    }

    private void rotateIcon(View view, float from, float to) {
        view.animate()
                .rotation(to)
                .setDuration(300)
                .setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator())
                .start();
    }

    private void expandView(View view) {
        view.setVisibility(View.VISIBLE);

        // 🔥 IMPORTANT: Use parent's width to measure correctly on first expand
        View parent = (View) view.getParent();
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(widthSpec, heightSpec);
        int targetHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.requestLayout();

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });

        animator.setDuration(300);
        animator.setInterpolator(new android.view.animation.DecelerateInterpolator());
        animator.start();
    }


    private void collapseView(View view) {
        int initialHeight = view.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });

        animator.setDuration(300);
        animator.setInterpolator(new android.view.animation.AccelerateInterpolator());
        animator.start();

        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
    }




}
