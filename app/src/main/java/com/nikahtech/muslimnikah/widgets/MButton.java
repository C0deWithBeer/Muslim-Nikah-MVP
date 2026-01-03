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
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.nikahtech.muslimnikah.R;

/**
 * World-class customizable button with:
 * - Individual corner radii
 * - Fully-rounded pill mode (rounded = true)
 * - Stroke
 * - Ripple (masked)
 * - Background color control
 * - Auto-adjusting radii
 */
public class MButton extends AppCompatButton {

    private final GradientDrawable shape = new GradientDrawable();

    // Corner values
    private float tl = 0f, tr = 0f, bl = 0f, br = 0f;

    private int strokeWidth = 0;
    private int strokeColor = Color.TRANSPARENT;
    private int bgColor = Color.TRANSPARENT;
    private int rippleColor = 0;

    private boolean rounded = false;   // NEW FEATURE

    public MButton(Context context) {
        super(context);
        init(null);
    }

    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MButton);

            float radius = a.getDimension(R.styleable.MButton_cornerRadius, -1);

            tl = a.getDimension(R.styleable.MButton_topLeftRadius, -1);
            tr = a.getDimension(R.styleable.MButton_topRightRadius, -1);
            br = a.getDimension(R.styleable.MButton_bottomRightRadius, -1);
            bl = a.getDimension(R.styleable.MButton_bottomLeftRadius, -1);

            strokeWidth = a.getDimensionPixelSize(R.styleable.MButton_strokeWidth, 0);
            strokeColor = a.getColor(R.styleable.MButton_strokeColor, Color.TRANSPARENT);
            bgColor = a.getColor(R.styleable.MButton_backgroundColor, Color.TRANSPARENT);
            rippleColor = a.getColor(R.styleable.MButton_rippleColor, 0);

            rounded = a.getBoolean(R.styleable.MButton_rounded, false);

            a.recycle();
        }

        // --- proper padding logic ---
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        boolean userSetPadding = (left != 0 || top != 0 || right != 0 || bottom != 0);

        if (!userSetPadding) {
            setPadding(0, 0, 0, 0);
        }

        setMinimumHeight(0);
        setMinHeight(0);

        applyProperties();
    }

    // This is executed when layout size changes -> required for pill buttons
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (rounded) {
            float radius = h / 2f;
            tl = tr = bl = br = radius;
            applyProperties();
        }
    }

    private void applyProperties() {
        shape.setColor(bgColor);
        shape.setStroke(strokeWidth, strokeColor);

        shape.setCornerRadii(new float[]{
                tl, tl,
                tr, tr,
                br, br,
                bl, bl
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int ripple = rippleColor == 0 ? adjustAlpha(bgColor, 0.25f) : rippleColor;

            GradientDrawable mask = new GradientDrawable();
            mask.setCornerRadii(shape.getCornerRadii());

            RippleDrawable rippleDrawable =
                    new RippleDrawable(ColorStateList.valueOf(ripple), shape, mask);

            setBackground(rippleDrawable);
        } else {
            setBackground(shape);
        }
    }

    private int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        return (color & 0x00FFFFFF) | (alpha << 24);
    }

    // ---------- Public APIs ----------

    public void setRounded(boolean value) {
        this.rounded = value;
        requestLayout(); // re-trigger size handling
    }

    public void setBackgroundColorInt(@ColorInt int color) {
        this.bgColor = color;
        applyProperties();
    }

    @Override
    public void setBackgroundColor(int color) {
        setBackgroundColorInt(color);
    }

    public void setBackgroundColorRes(@ColorRes int res) {
        setBackgroundColorInt(ContextCompat.getColor(getContext(), res));
    }

    public void setCornerRadius(float radius) {
        if (!rounded) {
            tl = tr = bl = br = radius;
            applyProperties();
        }
    }

    public void setTopLeftRadius(float radius) { if (!rounded) { tl = radius; applyProperties(); } }
    public void setTopRightRadius(float radius) { if (!rounded) { tr = radius; applyProperties(); } }
    public void setBottomLeftRadius(float radius) { if (!rounded) { bl = radius; applyProperties(); } }
    public void setBottomRightRadius(float radius) { if (!rounded) { br = radius; applyProperties(); } }

    public void setStrokeWidth(int width) { strokeWidth = width; applyProperties(); }
    public void setStrokeColor(@ColorInt int color) { strokeColor = color; applyProperties(); }
    public void setStrokeColorRes(@ColorRes int res) {
        strokeColor = ContextCompat.getColor(getContext(), res);
        applyProperties();
    }

    public void setRippleColor(@ColorInt int color) { rippleColor = color; applyProperties(); }

    public void setRippleColorRes(@ColorRes int res) {
        rippleColor = ContextCompat.getColor(getContext(), res);
        applyProperties();
    }
}
