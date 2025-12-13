package com.nikahtech.muslimnikah.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A highly reusable item decoration utility for applying
 * padding to the first item, last item, and spacing between items.
 */
public class RecyclerPaddingDecorator extends RecyclerView.ItemDecoration {

    private final int firstPadding;
    private final int lastPadding;
    private final int itemSpacing;
    private final int orientation; // RecyclerView.VERTICAL or RecyclerView.HORIZONTAL

    public RecyclerPaddingDecorator(
            @Px int firstPadding,
            @Px int lastPadding,
            @Px int itemSpacing,
            int orientation
    ) {
        this.firstPadding = firstPadding;
        this.lastPadding = lastPadding;
        this.itemSpacing = itemSpacing;
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter() != null ? parent.getAdapter().getItemCount() : 0;

        if (orientation == RecyclerView.VERTICAL) {
            if (position == 0) {
                outRect.top = firstPadding;
            } else {
                outRect.top = itemSpacing;
            }

            if (position == itemCount - 1) {
                outRect.bottom = lastPadding;
            }
        } else {
            if (position == 0) {
                outRect.left = firstPadding;
            } else {
                outRect.left = itemSpacing;
            }

            if (position == itemCount - 1) {
                outRect.right = lastPadding;
            }
        }
    }
}
