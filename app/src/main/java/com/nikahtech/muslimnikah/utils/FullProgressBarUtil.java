package com.nikahtech.muslimnikah.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.nikahtech.muslimnikah.R;

public class FullProgressBarUtil {

    private Dialog dialog;
    private final Context context;

    public FullProgressBarUtil(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Show the progress dialog safely.
     */
    public void show() {
        if (dialog != null && dialog.isShowing()) return;

        // Check if context is still valid
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;
        if (activity.isFinishing() || activity.isDestroyed()) return;

        try {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);

            // Inflate the layout to avoid direct setContentView
            dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_fullscreen_progress, null, false));

            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }

            if (!((Dialog) dialog).isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            // Safe fail: e.g., activity destroyed
            e.printStackTrace();
        }
    }

    /**
     * Dismiss the dialog safely.
     */
    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null; // clear reference to prevent leaks
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if dialog is currently shown.
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
