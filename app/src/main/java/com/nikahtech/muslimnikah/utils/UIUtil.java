package com.nikahtech.muslimnikah.utils;

import android.content.res.Resources;

public class UIUtil {
    public static int dp(int value) {
        return (int) (value * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int sp(float value) {
        return Math.round(value * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }
}
