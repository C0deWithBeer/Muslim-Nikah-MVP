package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.nikahtech.muslimnikah.R;

public class MOtpView extends View {

    private int otpLength = 4;
    private StringBuilder otpBuilder = new StringBuilder();

    private float circleRadius = 40f;
    private float strokeWidth = 6f;

    private int activeStrokeColor = Color.parseColor("#00897B");
    private int inactiveStrokeColor = Color.parseColor("#BDBDBD");
    private int textColor = Color.BLACK;
    private float textSize = 40f;

    private Paint circlePaint;
    private Paint textPaint;

    private OtpCompleteListener otpListener;

    public MOtpView(Context context) {
        super(context);
        init(null);
    }

    public MOtpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MOtpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MOtpView);
            otpLength = a.getInt(R.styleable.MOtpView_otpLength, otpLength);
            circleRadius = a.getDimension(R.styleable.MOtpView_circleRadius, circleRadius);
            strokeWidth = a.getDimension(R.styleable.MOtpView_strokeWidth, strokeWidth);
            activeStrokeColor = a.getColor(R.styleable.MOtpView_activeStrokeColor, activeStrokeColor);
            inactiveStrokeColor = a.getColor(R.styleable.MOtpView_inactiveStrokeColor, inactiveStrokeColor);
            textColor = a.getColor(R.styleable.MOtpView_textColor, textColor);
            textSize = a.getDimension(R.styleable.MOtpView_textSize, textSize);
            a.recycle();
        }

        setFocusableInTouchMode(true);
        setClickable(true);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        this.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (otpBuilder.length() > 0) {
                        otpBuilder.deleteCharAt(otpBuilder.length() - 1);
                        invalidate();
                    }
                    return true;
                }
            }
            return false;
        });

        this.setOnClickListener(v -> openKeyboard());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float totalWidth = (circleRadius * 2 * otpLength) + (circleRadius * (otpLength - 1));
        float startX = (getWidth() - totalWidth) / 2 + circleRadius;
        float centerY = getHeight() / 2f;

        for (int i = 0; i < otpLength; i++) {
            float cx = startX + i * (circleRadius * 3);

            // Highlight active circle
            if (i == otpBuilder.length()) {
                circlePaint.setColor(activeStrokeColor);
            } else {
                circlePaint.setColor(inactiveStrokeColor);
            }

            canvas.drawCircle(cx, centerY, circleRadius, circlePaint);

            // Draw character if exists
            if (i < otpBuilder.length()) {
                float textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2);
                canvas.drawText(String.valueOf(otpBuilder.charAt(i)), cx, textY, textPaint);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (otpBuilder.length() < otpLength) {
            if (event.getUnicodeChar() != 0 && Character.isDigit(event.getUnicodeChar())) {
                otpBuilder.append((char) event.getUnicodeChar());
                invalidate();

                if (otpBuilder.length() == otpLength && otpListener != null) {
                    otpListener.onOtpCompleted(otpBuilder.toString());
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void openKeyboard() {
        requestFocus();
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        openKeyboard();
        return true;
    }

    public void clearOtp() {
        otpBuilder.setLength(0);
        invalidate();
    }

    public String getOtp() {
        return otpBuilder.toString();
    }

    public void setOtpCompleteListener(OtpCompleteListener listener) {
        this.otpListener = listener;
    }

    public interface OtpCompleteListener {
        void onOtpCompleted(String otp);
    }
}
