package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.nikahtech.muslimnikah.R;

public class MCardView extends CardView {

    private final GradientDrawable shape = new GradientDrawable();

    private float tl = 0f, tr = 0f, br = 0f, bl = 0f;

    private int bgColor = Color.WHITE;
    private int rippleColor = 0;

    // --- Stroke ---
    private int strokeWidthPx = 0;
    private int strokeColor = Color.TRANSPARENT;

    public MCardView(Context context) {
        super(context);
        init(null);
    }

    public MCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MCardView);

            float radius = a.getDimension(R.styleable.MCardView_cornerRadius, -1);

            tl = a.getDimension(R.styleable.MCardView_topLeftRadius, -1);
            tr = a.getDimension(R.styleable.MCardView_topRightRadius, -1);
            br = a.getDimension(R.styleable.MCardView_bottomRightRadius, -1);
            bl = a.getDimension(R.styleable.MCardView_bottomLeftRadius, -1);

            if (radius != -1) tl = tr = br = bl = radius;

            bgColor = a.getColor(R.styleable.MCardView_backgroundColor, Color.WHITE);
            rippleColor = a.getColor(R.styleable.MCardView_rippleColor, 0);

            float elevation = a.getDimension(R.styleable.MCardView_cardElevation, -1);
            if (elevation != -1) setCardElevation(elevation);

            // STROKE
            strokeWidthPx = a.getDimensionPixelSize(R.styleable.MCardView_strokeWidth, 0);
            strokeColor = a.getColor(R.styleable.MCardView_strokeColor, Color.TRANSPARENT);

            a.recycle();
        }

        // Fix CardView interfering with corners
        setRadius(0f);
        setUseCompatPadding(false);
        setPreventCornerOverlap(false);

        applyProperties();
    }

    private void applyProperties() {

        shape.setColor(bgColor);

        shape.setCornerRadii(new float[]{
                tl, tl,
                tr, tr,
                br, br,
                bl, bl
        });

        // --- STROKE ---
        if (strokeWidthPx > 0) {
            shape.setStroke(strokeWidthPx, strokeColor);
        } else {
            shape.setStroke(0, Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int actualRipple =
                    rippleColor == 0 ? adjustAlpha(Color.BLACK, 0.1f) : rippleColor;

            GradientDrawable mask = new GradientDrawable();
            mask.setCornerRadii(shape.getCornerRadii());
            // Do NOT set stroke on mask

            RippleDrawable rippleDrawable =
                    new RippleDrawable(ColorStateList.valueOf(actualRipple), shape, mask);

            setBackground(rippleDrawable);

        } else {
            setBackground(shape);
        }
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return (color & 0x00FFFFFF) | (alpha << 24);
    }

    // ---------------- PUBLIC APIs ----------------

    public void setCardBackgroundColorInt(@ColorInt int color) {
        bgColor = color;
        applyProperties();
    }

    @Override
    public void setCardBackgroundColor(@ColorInt int color) {
        setCardBackgroundColorInt(color);
    }

    public void setCardBackgroundColorRes(@ColorRes int res) {
        bgColor = ContextCompat.getColor(getContext(), res);
        applyProperties();
    }

    public void setCornerRadius(float radius) {
        tl = tr = br = bl = radius;
        applyProperties();
    }

    public void setTopLeftRadius(float radius) { tl = radius; applyProperties(); }
    public void setTopRightRadius(float radius) { tr = radius; applyProperties(); }
    public void setBottomLeftRadius(float radius) { bl = radius; applyProperties(); }
    public void setBottomRightRadius(float radius) { br = radius; applyProperties(); }

    // --- Stroke setters ---
    public void setStrokeWidth(int widthPx) {
        strokeWidthPx = widthPx;
        applyProperties();
    }

    public void setStrokeColor(@ColorInt int color) {
        strokeColor = color;
        applyProperties();
    }

    public void setStrokeColorRes(@ColorRes int res) {
        strokeColor = ContextCompat.getColor(getContext(), res);
        applyProperties();
    }

    public void setRippleColor(@ColorInt int color) {
        rippleColor = color;
        applyProperties();
    }

    public void setRippleColorRes(@ColorRes int res) {
        rippleColor = ContextCompat.getColor(getContext(), res);
        applyProperties();
    }
}
