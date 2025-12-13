package com.nikahtech.muslimnikah.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.nikahtech.muslimnikah.R;

public class MCheckBox extends View {

    // Colors
    private int boxBorderColor = Color.GRAY;
    private int checkedBackgroundColor = Color.BLUE;
    private int tickColor = Color.WHITE;

    private float strokeWidth = 4f;
    private float cornerRadius = 0f;

    // Checkbox state
    private boolean isChecked = false;

    // Label
    private boolean labelDisplay = false;
    private String labelText = "";
    private int labelColor = Color.BLACK;
    private float labelSize = 40f;
    private String labelFontFamily = null;

    // Paints
    private Paint boxPaint;
    private Paint tickPaint;
    private Paint textPaint;

    private Path tickPath;
    private float tickProgress = 0f;
    private ValueAnimator tickAnimator;

    private float boxSize;

    public MCheckBox(Context context) {
        super(context);
        init(null);
    }

    public MCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MCheckBox);

            boxBorderColor = a.getColor(R.styleable.MCheckBox_boxColor, boxBorderColor);
            tickColor = a.getColor(R.styleable.MCheckBox_tickColor, tickColor);
            checkedBackgroundColor = a.getColor(R.styleable.MCheckBox_checkedBackgroundColor, checkedBackgroundColor);
            strokeWidth = a.getDimension(R.styleable.MCheckBox_strokeWidth, strokeWidth);
            cornerRadius = a.getDimension(R.styleable.MCheckBox_cornerRadius, cornerRadius);
            isChecked = a.getBoolean(R.styleable.MCheckBox_checked, false);

            labelDisplay = a.getBoolean(R.styleable.MCheckBox_labelDisplay, false);
            labelText = a.getString(R.styleable.MCheckBox_labelText);
            labelColor = a.getColor(R.styleable.MCheckBox_labelColor, Color.BLACK);
            labelSize = a.getDimension(R.styleable.MCheckBox_labelSize, 40f);
            labelFontFamily = a.getString(R.styleable.MCheckBox_labelFontFamily);

            a.recycle();
        }

        // Box Paint
        boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(strokeWidth);
        boxPaint.setColor(boxBorderColor);

        // Tick Paint
        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setStyle(Paint.Style.STROKE);
        tickPaint.setStrokeWidth(strokeWidth);
        tickPaint.setColor(tickColor);
        tickPaint.setStrokeCap(Paint.Cap.ROUND);
        tickPaint.setStrokeJoin(Paint.Join.ROUND);

        // Text Paint
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(labelColor);
        textPaint.setTextSize(labelSize);
        if (labelFontFamily != null) {
            textPaint.setTypeface(Typeface.create(labelFontFamily, Typeface.NORMAL));
        }

        tickPath = new Path();
        setupAnimator();
    }

    private void setupAnimator() {
        tickAnimator = ValueAnimator.ofFloat(0f, 1f);
        tickAnimator.setDuration(250);
        tickAnimator.addUpdateListener(animation -> {
            tickProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boxSize = 60 + strokeWidth * 2; // Default box size, can be customized

        int desiredWidth = (int) boxSize;
        int desiredHeight = (int) boxSize;

        if (labelDisplay && labelText != null) {
            float textWidth = textPaint.measureText(labelText) + 20f; // margin between box and label
            desiredWidth += textWidth;
            desiredHeight = (int) Math.max(desiredHeight, labelSize + 20f);
        }

        int width = resolveSize(desiredWidth, widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = strokeWidth / 2;
        float top = (getHeight() - boxSize) / 2;
        float right = left + boxSize;
        float bottom = top + boxSize;

        RectF rect = new RectF(left, top, right, bottom);

        // Draw box
        if (isChecked) {
            boxPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            boxPaint.setColor(checkedBackgroundColor);
        } else {
            boxPaint.setStyle(Paint.Style.STROKE);
            boxPaint.setColor(boxBorderColor);
        }

        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, boxPaint);

        // Draw tick if checked
        if (isChecked) {
            tickPath.reset();
            tickPath.moveTo(left + boxSize * 0.2f, top + boxSize * 0.5f);
            tickPath.lineTo(left + boxSize * 0.45f, top + boxSize * 0.75f);
            tickPath.lineTo(left + boxSize * 0.8f, top + boxSize * 0.25f);

            Path path = new Path();
            PathMeasure pm = new PathMeasure(tickPath, false);
            float length = pm.getLength();
            pm.getSegment(0, length * tickProgress, path, true);
            canvas.drawPath(path, tickPaint);
        }

        // Draw label if enabled
        if (labelDisplay && labelText != null && !labelText.isEmpty()) {
            float textX = right + 20f;
            float textY = getHeight() / 2f + (labelSize / 3f);
            canvas.drawText(labelText, textX, textY, textPaint);
        }
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void toggle() {
        setChecked(!isChecked);
    }

    public void setChecked(boolean checked) {
        if (isChecked == checked) return;
        isChecked = checked;
        if (isChecked) tickAnimator.start();
        else tickProgress = 0f;
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }

    // Label setters
    public void setLabel(String label) {
        this.labelText = label;
        requestLayout();
        invalidate();
    }

    public void setLabelDisplay(boolean show) {
        this.labelDisplay = show;
        requestLayout();
        invalidate();
    }

    public void setLabelColor(int color) {
        this.labelColor = color;
        textPaint.setColor(color);
        invalidate();
    }

    public void setLabelSize(float size) {
        this.labelSize = size;
        textPaint.setTextSize(size);
        requestLayout();
        invalidate();
    }

    public void setLabelFontFamily(String fontFamily) {
        this.labelFontFamily = fontFamily;
        textPaint.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
        requestLayout();
        invalidate();
    }

    // Checkbox styling setters
    public void setBoxBorderColor(int color) {
        this.boxBorderColor = color;
        invalidate();
    }

    public void setCheckedBackgroundColor(int color) {
        this.checkedBackgroundColor = color;
        invalidate();
    }

    public void setTickColor(int color) {
        this.tickColor = color;
        tickPaint.setColor(color);
        invalidate();
    }

    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        invalidate();
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        boxPaint.setStrokeWidth(width);
        tickPaint.setStrokeWidth(width);
        invalidate();
    }
}
