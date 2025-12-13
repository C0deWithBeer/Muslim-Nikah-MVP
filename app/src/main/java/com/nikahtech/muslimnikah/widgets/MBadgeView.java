package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.nikahtech.muslimnikah.R;

public class MBadgeView extends FrameLayout {

    private TextView textView;
    private GradientDrawable bgShape;

    private int bgColor = Color.RED;
    private int textColor = Color.WHITE;
    private int rippleColor = 0;
    private float radius = 100f;

    private int borderColor = Color.TRANSPARENT;
    private float borderWidth = 0;

    private float textSize = 12f;
    private int padding = 0;
    private String value = "";

    public MBadgeView(Context context) {
        super(context);
        init(null);
    }

    public MBadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MBadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setIncludeFontPadding(false); // no extra top/bottom gaps
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        addView(textView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        bgShape = new GradientDrawable();

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MBadgeView);

            value = a.getString(R.styleable.MBadgeView_badgeValue);
            if (value == null) value = "";

            bgColor = a.getColor(R.styleable.MBadgeView_badgeBgColor, Color.RED);
            textColor = a.getColor(R.styleable.MBadgeView_badgeTextColor, Color.WHITE);
            rippleColor = a.getColor(R.styleable.MBadgeView_badgeRippleColor, adjustAlpha(Color.WHITE, 0.3f));

            radius = a.getDimension(R.styleable.MBadgeView_badgeCornerRadius, dp(500));

            borderColor = a.getColor(R.styleable.MBadgeView_badgeBorderColor, Color.TRANSPARENT);
            borderWidth = a.getDimension(R.styleable.MBadgeView_badgeBorderWidth, 0);

            textSize = a.getDimension(R.styleable.MBadgeView_badgeTextSize, sp(12));

            padding = (int) a.getDimension(R.styleable.MBadgeView_badgePadding, dp(6));

            a.recycle();
        }

        textView.setTextColor(textColor);
        textView.setTextSize(pxToSp(textSize));

        // set text to bold
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        textView.setText(value);
        textView.setPadding(padding, padding, padding, padding);

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
            GradientDrawable mask = new GradientDrawable();
            mask.setCornerRadius(radius);

            RippleDrawable rippleDrawable =
                    new RippleDrawable(ColorStateList.valueOf(rippleColor), bgShape, mask);

            setBackground(rippleDrawable);
        } else {
            setBackground(bgShape);
        }
    }

    // Public APIs =====

    public void setValue(int v) {
        value = String.valueOf(v);
        textView.setText(value);
        requestLayout();
    }

    public void setValue(String v) {
        value = v;
        textView.setText(v);
        requestLayout();
    }

    public void setTextColor(@ColorInt int col) {
        textColor = col;
        textView.setTextColor(col);
    }

    public void setBgColor(@ColorInt int col) {
        bgColor = col;
        applyStyles();
    }

    public void setRippleColor(@ColorInt int col) {
        rippleColor = col;
        applyStyles();
    }

    public void setCornerRadius(float r) {
        radius = r;
        applyStyles();
    }

    public void setBorder(int widthDp, @ColorInt int col) {
        borderWidth = dp(widthDp);
        borderColor = col;
        applyStyles();
    }

    public void setTextSizePx(float textPx) {
        this.textSize = textPx;
        textView.setTextSize(pxToSp(textPx));
    }

    public void setPaddingDp(int paddingDp) {
        padding = dp(paddingDp);
        textView.setPadding(padding, padding, padding, padding);
        requestLayout();
    }

    // Helpers =====

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private float sp(int value) {
        return value * getResources().getDisplayMetrics().scaledDensity;
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return (color & 0x00FFFFFF) | (alpha << 24);
    }
}
