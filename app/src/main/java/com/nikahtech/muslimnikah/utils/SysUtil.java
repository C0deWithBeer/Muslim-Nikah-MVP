package com.nikahtech.muslimnikah.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class SysUtil {
    /**
     * Changes the status bar color to the specified color resource.
     * Works on API 21 (Lollipop) and above.
     *
     * @param activity The Activity context
     * @param colorRes Resource ID like R.color.your_color
     */
    public static void changeStatusBarColor(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(activity, colorRes));
        }
    }
}
