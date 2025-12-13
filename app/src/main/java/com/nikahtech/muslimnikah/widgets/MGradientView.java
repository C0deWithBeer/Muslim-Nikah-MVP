package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.nikahtech.muslimnikah.R;

public class MGradientView extends View {

    private final GradientDrawable gradient = new GradientDrawable();

    private float tl, tr, br, bl;
    private boolean rounded = false;

    private int strokeWidth = 0;
    private int strokeColor = 0;

    private int startColor = 0;
    private int centerColor = -1; // optional
    private int endColor = 0;

    private int angle = 0;
    private int gradientType = 0; // 0=linear, 1=radial, 2=sweep

    public MGradientView(Context context) {
        super(context);
        init(null);
    }

    public MGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MGradientView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MGradientView);

        float radius = a.getDimension(R.styleable.MGradientView_cornerRadius, -1);

        tl = a.getDimension(R.styleable.MGradientView_topLeftRadius, -1);
        tr = a.getDimension(R.styleable.MGradientView_topRightRadius, -1);
        br = a.getDimension(R.styleable.MGradientView_bottomRightRadius, -1);
        bl = a.getDimension(R.styleable.MGradientView_bottomLeftRadius, -1);

        rounded = a.getBoolean(R.styleable.MGradientView_rounded, false);

        strokeWidth = a.getDimensionPixelSize(R.styleable.MGradientView_strokeWidth, 0);
        strokeColor = a.getColor(R.styleable.MGradientView_strokeColor, 0);

        startColor = a.getColor(R.styleable.MGradientView_gradientStartColor, 0);
        centerColor = a.getColor(R.styleable.MGradientView_gradientCenterColor, -1);
        endColor = a.getColor(R.styleable.MGradientView_gradientEndColor, 0);

        angle = a.getInt(R.styleable.MGradientView_gradientAngle, 0);
        gradientType = a.getInt(R.styleable.MGradientView_gradientType, 0);

        a.recycle();

        if (radius != -1 && !rounded) {
            tl = tr = bl = br = radius;
        }

        applyGradient();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (rounded) {
            float r = h / 2f;
            tl = tr = bl = br = r;
        }
        applyGradient();
    }

    private void applyGradient() {

        // Set corner radii
        gradient.setCornerRadii(new float[]{
                tl, tl,
                tr, tr,
                br, br,
                bl, bl
        });

        gradient.setShape(GradientDrawable.RECTANGLE);

        // Gradient type
        switch (gradientType) {
            case 1:
                gradient.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                gradient.setGradientRadius(Math.max(getWidth(), getHeight()) / 2f);
                break;

            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    gradient.setGradientType(GradientDrawable.SWEEP_GRADIENT);
                }
                break;

            default:
                gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        }

        // Gradient colors
        if (centerColor != -1) {
            gradient.setColors(new int[]{startColor, centerColor, endColor});
        } else {
            gradient.setColors(new int[]{startColor, endColor});
        }

        // Angle (0, 45, 90, 180, 315 etc.)
        gradient.setOrientation(getOrientationFromAngle(angle));

        // Stroke
        if (strokeWidth > 0) {
            gradient.setStroke(strokeWidth, strokeColor);
        }

        setBackground(gradient);
    }

    private GradientDrawable.Orientation getOrientationFromAngle(int angle) {
        angle = angle % 360;
        switch (angle) {
            case 0: return GradientDrawable.Orientation.LEFT_RIGHT;
            case 45: return GradientDrawable.Orientation.BL_TR;
            case 90: return GradientDrawable.Orientation.BOTTOM_TOP;
            case 135: return GradientDrawable.Orientation.BR_TL;
            case 180: return GradientDrawable.Orientation.RIGHT_LEFT;
            case 225: return GradientDrawable.Orientation.TR_BL;
            case 270: return GradientDrawable.Orientation.TOP_BOTTOM;
            case 315: return GradientDrawable.Orientation.TL_BR;
            default: return GradientDrawable.Orientation.LEFT_RIGHT;
        }
    }
}
