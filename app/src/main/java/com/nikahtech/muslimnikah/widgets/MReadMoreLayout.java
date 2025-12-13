package com.nikahtech.muslimnikah.widgets;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikahtech.muslimnikah.R;

public class MReadMoreLayout extends LinearLayout {

    private TextView tvContent, tvToggle;

    private String fullText = "";
    private int collapsedLines = 4;
    private boolean isExpanded = false;

    public MReadMoreLayout(Context context) {
        super(context);
        init(context);
    }

    public MReadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MReadMoreLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        setOrientation(VERTICAL);
        LayoutInflater.from(ctx).inflate(R.layout.view_read_more, this, true);

        tvContent = findViewById(R.id.tvContent);
        tvToggle = findViewById(R.id.tvToggle);

        tvToggle.setOnClickListener(v -> toggle());
    }

    public void setText(String text) {
        this.fullText = text;
        tvContent.setText(text);

        post(() -> {
            if (tvContent.getLineCount() > collapsedLines) {
                collapse();
            } else {
                tvToggle.setVisibility(GONE);
            }
        });
    }

    public void setCollapsedLines(int lines) {
        this.collapsedLines = lines;
        collapse();
    }

    private void collapse() {
        isExpanded = false;
        tvContent.setMaxLines(collapsedLines);
        tvToggle.setText("Read More");
        tvToggle.setVisibility(VISIBLE);
    }

    private void expand() {
        isExpanded = true;
        tvContent.setMaxLines(Integer.MAX_VALUE);
        tvToggle.setText("Read Less");
    }

    private void toggle() {
        if (isExpanded) collapse();
        else expand();
    }

    // Public customization
    public TextView getContentTextView() {
        return tvContent;
    }

    public TextView getToggleTextView() {
        return tvToggle;
    }
}
