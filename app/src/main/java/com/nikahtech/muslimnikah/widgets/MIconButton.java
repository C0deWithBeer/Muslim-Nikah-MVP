package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.nikahtech.muslimnikah.R;

public class MIconButton extends FrameLayout {

    private ImageView iconView;
    private GradientDrawable bgShape;

    private int bgColor = Color.TRANSPARENT;
    private int iconTint = Color.WHITE;
    private int rippleColor = 0;
    private float radius = 100f;
    private int borderColor = Color.TRANSPARENT;
    private float borderWidth = 0;
    private int iconRes = -1;

    public MIconButton(Context context) {
        super(context);
        init(null);
    }

    public MIconButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MIconButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        iconView = new ImageView(getContext());
        iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int pad = dp(8);
        iconView.setPadding(pad, pad, pad, pad);
        addView(iconView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        bgShape = new GradientDrawable();

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MIconButton);

            iconRes = a.getResourceId(R.styleable.MIconButton_iconSrc, -1);
            if (iconRes != -1) iconView.setImageResource(iconRes);

            iconTint = a.getColor(R.styleable.MIconButton_iconTint, Color.WHITE);
            iconView.setImageTintList(ColorStateList.valueOf(iconTint));

            bgColor = a.getColor(R.styleable.MIconButton_bgColor, Color.BLACK);
            rippleColor = a.getColor(R.styleable.MIconButton_rippleColor, adjustAlpha(Color.WHITE, 0.3f));

            radius = a.getDimension(R.styleable.MIconButton_cornerRadius, dp(500));

            borderColor = a.getColor(R.styleable.MIconButton_borderColor, Color.TRANSPARENT);
            borderWidth = a.getDimension(R.styleable.MIconButton_borderWidth, 0);

            int customPadding = (int) a.getDimension(R.styleable.MIconButton_iconPadding, dp(8));
            iconView.setPadding(customPadding, customPadding, customPadding, customPadding);

            a.recycle();
        }

        applyStyles();
        setClickable(true);
        setFocusable(true);
    }

    private void applyStyles() {
        bgShape.setColor(bgColor);
        bgShape.setCornerRadius(radius);

        if (borderWidth > 0) {
            bgShape.setStroke((int) borderWidth, borderColor);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GradientDrawable rippleMask = new GradientDrawable();
            rippleMask.setCornerRadius(radius);

            RippleDrawable rippleDrawable =
                    new RippleDrawable(ColorStateList.valueOf(rippleColor), bgShape, rippleMask);

            setBackground(rippleDrawable);
        } else {
            setBackground(bgShape);
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return (color & 0x00FFFFFF) | (alpha << 24);
    }

    // PUBLIC APIs ↓↓↓

    public void setIcon(int resId) {
        iconRes = resId;
        iconView.setImageResource(resId);
    }

    public void setIconTint(@ColorInt int color) {
        iconTint = color;
        iconView.setImageTintList(ColorStateList.valueOf(iconTint));
    }

    public void setBgColor(@ColorInt int color) {
        bgColor = color;
        applyStyles();
    }

    public void setRippleColor(@ColorInt int color) {
        rippleColor = color;
        applyStyles();
    }

    public void setCornerRadius(float r) {
        radius = r;
        applyStyles();
    }
}
