package com.nikahtech.muslimnikah.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.nikahtech.muslimnikah.R;

public class MIndicatorView extends LinearLayout {

    private int dotSize = 12;
    private int dotSpacing = 12;
    private int activeColor = Color.WHITE;
    private int inactiveColor = 0x55FFFFFF;

    private int count = 0;
    private int current = 0;

    public MIndicatorView(Context c, AttributeSet a) {
        super(c, a);
        setOrientation(HORIZONTAL);

        TypedArray t = c.obtainStyledAttributes(a, R.styleable.MIndicatorView);
        dotSize = t.getDimensionPixelSize(R.styleable.MIndicatorView_dotSize, 16);
        dotSpacing = t.getDimensionPixelSize(R.styleable.MIndicatorView_dotSpacing, 12);
        activeColor = t.getColor(R.styleable.MIndicatorView_activeColor, Color.WHITE);
        inactiveColor = t.getColor(R.styleable.MIndicatorView_inactiveColor, 0x55FFFFFF);
        t.recycle();
    }

    public void setCount(int count) {
        this.count = count;
        removeAllViews();

        for (int i = 0; i < count; i++) {
            View v = new View(getContext());
            LayoutParams lp = new LayoutParams(dotSize, dotSize);
            lp.setMargins(dotSpacing, 0, dotSpacing, 0);
            v.setLayoutParams(lp);

            GradientDrawable dot = new GradientDrawable();
            dot.setShape(GradientDrawable.OVAL);
            dot.setColor(i == 0 ? activeColor : inactiveColor);

            v.setBackground(dot);
            addView(v);
        }
    }

    public void setActive(int position) {
        View oldDot = getChildAt(current);
        View newDot = getChildAt(position);

        animateDot(oldDot, false);
        animateDot(newDot, true);

        current = position;
    }

    private void animateDot(View v, boolean active) {
        int targetWidth = active ? dotSize * 3 : dotSize;  // Capsule width
        int startWidth = v.getLayoutParams().width;

        // Animate width change
        ValueAnimator widthAnim = ValueAnimator.ofInt(startWidth, targetWidth);
        widthAnim.addUpdateListener(animation -> {
            int val = (int) animation.getAnimatedValue();
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            lp.width = val;
            v.setLayoutParams(lp);
        });

        widthAnim.setDuration(250);
        widthAnim.setInterpolator(new OvershootInterpolator());
        widthAnim.start();

        // Animate scale for smoothness
        float scaleTo = active ? 1.1f : 1f;
        v.animate()
                .scaleX(scaleTo)
                .scaleY(scaleTo)
                .setDuration(250)
                .setInterpolator(new OvershootInterpolator())
                .start();

        // Apply capsule or dot background
        GradientDrawable bg = (GradientDrawable) v.getBackground();
        bg.setColor(active ? activeColor : inactiveColor);

        if (active) {
            bg.setShape(GradientDrawable.RECTANGLE);
            bg.setCornerRadius(dotSize); // Very round → capsule
        } else {
            bg.setShape(GradientDrawable.OVAL); // circle
        }
    }
}