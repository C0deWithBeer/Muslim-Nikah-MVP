package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.nikahtech.muslimnikah.R;

public class MReadMoreTextView extends AppCompatTextView {

    private static final int DEFAULT_MAX_LINES = 3;

    private String originalText = "";
    private String readMoreText = " Read More";
    private String readLessText = " Read Less";

    private int readMoreColor;
    private int readLessColor;

    private boolean isExpanded = false;
    private int collapsedMaxLines = DEFAULT_MAX_LINES;

    public MReadMoreTextView(Context context) {
        super(context);
        init(null);
    }

    public MReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MReadMoreTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        setMovementMethod(LinkMovementMethod.getInstance());

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MReadMoreTextView);

            readMoreText = a.getString(R.styleable.MReadMoreTextView_rmtv_readMoreText);
            readLessText = a.getString(R.styleable.MReadMoreTextView_rmtv_readLessText);
            collapsedMaxLines = a.getInt(R.styleable.MReadMoreTextView_rmtv_collapsedLines, DEFAULT_MAX_LINES);

            readMoreColor = a.getColor(R.styleable.MReadMoreTextView_rmtv_readMoreColor, getCurrentTextColor());
            readLessColor = a.getColor(R.styleable.MReadMoreTextView_rmtv_readLessColor, getCurrentTextColor());

            a.recycle();
        }

        post(() -> {
            originalText = getText().toString();
            applyCollapsedState();
        });
    }

    private void applyCollapsedState() {
        if (originalText == null) return;

        setMaxLines(collapsedMaxLines);

        post(() -> {
            Layout layout = getLayout();
            if (layout == null || layout.getLineCount() <= collapsedMaxLines) {
                setText(originalText);
                return;
            }

            int end = layout.getLineEnd(collapsedMaxLines - 1);
            String trimmed = originalText.substring(0, Math.max(0, end)).trim() + "...";

            SpannableStringBuilder ssb = new SpannableStringBuilder(trimmed + readMoreText);

            // Color the "Read More"
            ssb.setSpan(
                    new ForegroundColorSpan(readMoreColor),
                    ssb.length() - readMoreText.length(),
                    ssb.length(),
                    0
            );

            // Make it bold
            ssb.setSpan(
                    new StyleSpan(android.graphics.Typeface.BOLD),
                    ssb.length() - readMoreText.length(),
                    ssb.length(),
                    0
            );

            setText(ssb);
            setOnClickListener(v -> toggle());
        });
    }

    private void applyExpandedState() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(originalText + readLessText);

        ssb.setSpan(
                new ForegroundColorSpan(readLessColor),
                ssb.length() - readLessText.length(),
                ssb.length(),
                0
        );

        ssb.setSpan(
                new StyleSpan(android.graphics.Typeface.BOLD),
                ssb.length() - readLessText.length(),
                ssb.length(),
                0
        );

        setMaxLines(Integer.MAX_VALUE);
        setText(ssb);

        setOnClickListener(v -> toggle());
    }

    private void toggle() {
        isExpanded = !isExpanded;
        if (isExpanded) {
            applyExpandedState();
        } else {
            applyCollapsedState();
        }
    }

    // Public API
    public void setCollapsedLines(int lines) {
        this.collapsedMaxLines = lines;
        applyCollapsedState();
    }

    public void setReadMoreText(String text) {
        this.readMoreText = text;
        applyCollapsedState();
    }

    public void setReadLessText(String text) {
        this.readLessText = text;
        applyExpandedState();
    }

    public void setReadMoreColor(int color) {
        this.readMoreColor = color;
        applyCollapsedState();
    }

    public void setReadLessColor(int color) {
        this.readLessColor = color;
        applyExpandedState();
    }
}
