package com.nikahtech.muslimnikah.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.databinding.DialogToastBinding;

public final class ToastUtil {

    private static Toast currentToast;
    private static final Handler handler = new Handler(Looper.getMainLooper());

    private ToastUtil() {}

    public static void show(Context context, String message, AppToastType type) {

        if (context == null || message == null || message.trim().isEmpty()) return;

        // Cancel previous toast
        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }

        DialogToastBinding binding =
                DialogToastBinding.inflate(LayoutInflater.from(context));

        binding.tvMessage.setText(message);

        int bgColor = (type == AppToastType.SUCCESS)
                ? ContextCompat.getColor(context, R.color.colorSuccess)
                : ContextCompat.getColor(context, R.color.colorAlert);

        binding.tvMessage.setBackgroundColor(bgColor);

        View toastView = binding.getRoot();

        // INITIAL STATE (below screen + invisible)
        toastView.setAlpha(0f);
        toastView.setTranslationY(100f);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                0,
                dpToPx(context, 64)
        );

        currentToast = toast;
        toast.show();

        // -----------------------
        // SLIDE UP + FADE IN
        // -----------------------
        toastView.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // -----------------------
        // SLIDE DOWN + FADE OUT
        // -----------------------
        handler.postDelayed(() -> {
            toastView.animate()
                    .translationY(100f)
                    .alpha(0f)
                    .setDuration(300)
                    .setInterpolator(new AccelerateInterpolator())
                    .withEndAction(() -> {
                        if (currentToast != null) {
                            currentToast.cancel();
                            currentToast = null;
                        }
                    })
                    .start();
        }, getToastDurationMillis(toast.getDuration()));
    }

    private static int getToastDurationMillis(int duration) {
        return duration == Toast.LENGTH_LONG ? 3500 : 2000;
    }

    private static int dpToPx(Context context, int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    public enum AppToastType {
        SUCCESS,
        ERROR
    }
}
