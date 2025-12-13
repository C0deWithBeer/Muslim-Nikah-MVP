package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.nikahtech.muslimnikah.R;

public class MEditText extends AppCompatEditText {

    private GradientDrawable backgroundDrawable;

    public MEditText(Context context) {
        super(context);
        init(null);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        backgroundDrawable = new GradientDrawable();

        int bgColor = Color.TRANSPARENT;
        float strokeWidth = 0;
        int strokeColor = Color.TRANSPARENT;
        int highlightColor = Color.TRANSPARENT;

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MEditText);

            float generalRadius = a.getDimension(R.styleable.MEditText_cornerRadius, -1);

            float topLeft = a.getDimension(R.styleable.MEditText_cornerTopLeft, generalRadius >= 0 ? generalRadius : 0);
            float topRight = a.getDimension(R.styleable.MEditText_cornerTopRight, generalRadius >= 0 ? generalRadius : 0);
            float bottomRight = a.getDimension(R.styleable.MEditText_cornerBottomRight, generalRadius >= 0 ? generalRadius : 0);
            float bottomLeft = a.getDimension(R.styleable.MEditText_cornerBottomLeft, generalRadius >= 0 ? generalRadius : 0);

            backgroundDrawable.setCornerRadii(new float[]{
                    topLeft, topLeft,
                    topRight, topRight,
                    bottomRight, bottomRight,
                    bottomLeft, bottomLeft
            });

            strokeColor = a.getColor(R.styleable.MEditText_strokeColor, Color.TRANSPARENT);
            strokeWidth = a.getDimension(R.styleable.MEditText_strokeWidth, 0);
            highlightColor = a.getColor(R.styleable.MEditText_strokeHighlightColor, strokeColor);

            // ⭐ Read background color
            bgColor = a.getColor(R.styleable.MEditText_backgroundColor, Color.TRANSPARENT);

            a.recycle();
        }

        // ⭐ Apply background color
        backgroundDrawable.setColor(bgColor);

        // Apply stroke
        backgroundDrawable.setStroke((int) strokeWidth, strokeColor);

        // Highlight logic
        final float finalStrokeWidth = strokeWidth;
        final int finalStrokeColor = strokeColor;
        final int finalHighlightColor = highlightColor;

        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                backgroundDrawable.setStroke((int) (finalStrokeWidth + 2), finalHighlightColor);
            } else {
                backgroundDrawable.setStroke((int) finalStrokeWidth, finalStrokeColor);
            }
        });

        setBackground(backgroundDrawable);
    }
}
