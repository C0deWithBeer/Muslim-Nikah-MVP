package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.nikahtech.muslimnikah.R;

public class MImageButton extends FrameLayout {

    private final ImageView imageView;

    public MImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 1️⃣ Create ImageView
        imageView = new ImageView(context);
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(imageView);

        // 2️⃣ Load custom attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MImageButton);

        int bgColor = a.getColor(R.styleable.MImageButton_backgroundColor, 0xFFFFFFFF);
        int strokeColor = a.getColor(R.styleable.MImageButton_strokeColor, 0);
        int strokeWidth = a.getDimensionPixelSize(R.styleable.MImageButton_strokeWidth, 0);
        boolean rounded = a.getBoolean(R.styleable.MImageButton_rounded, false);

        int radius = a.getDimensionPixelSize(R.styleable.MImageButton_cornerRadius, 0);
        int tl = a.getDimensionPixelSize(R.styleable.MImageButton_topLeftRadius, radius);
        int tr = a.getDimensionPixelSize(R.styleable.MImageButton_topRightRadius, radius);
        int bl = a.getDimensionPixelSize(R.styleable.MImageButton_bottomLeftRadius, radius);
        int br = a.getDimensionPixelSize(R.styleable.MImageButton_bottomRightRadius, radius);

        int rippleColor = a.getColor(R.styleable.MImageButton_rippleColor, 0x22000000);
        int padding = a.getDimensionPixelSize(R.styleable.MImageButton_innerPadding, 24);

        int src = a.getResourceId(R.styleable.MImageButton_srcCompat, -1);
        int tintColor = a.getColor(R.styleable.MImageButton_iconTint, -1);

        a.recycle();

        // 3️⃣ Create background shape
        GradientDrawable shape = new GradientDrawable();
        if (rounded) shape.setCornerRadius(10000f);
        else shape.setCornerRadii(new float[]{tl, tl, tr, tr, br, br, bl, bl});
        shape.setColor(bgColor);
        if (strokeWidth > 0) shape.setStroke(strokeWidth, strokeColor);

        // 4️⃣ Apply ripple
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable rippleDrawable = new RippleDrawable(
                    ColorStateList.valueOf(rippleColor),
                    shape,
                    null
            );
            setBackground(rippleDrawable);
        } else {
            setBackground(shape);
        }

        // 5️⃣ Inner padding
        imageView.setPadding(padding, padding, padding, padding);

        // 6️⃣ Load drawable & apply tint correctly
        if (src != -1) {
            Drawable drawable = AppCompatResources.getDrawable(context, src);
            if (drawable != null) {
                // MUST mutate & wrap to apply tint
                drawable = DrawableCompat.wrap(drawable.mutate());

                // ✅ Tint works ONLY for fillColor
                if (tintColor != -1) DrawableCompat.setTint(drawable, tintColor);

                imageView.setImageDrawable(drawable);
            }
        }
    }

    // Optional getter
    public ImageView getImageView() {
        return imageView;
    }
}
