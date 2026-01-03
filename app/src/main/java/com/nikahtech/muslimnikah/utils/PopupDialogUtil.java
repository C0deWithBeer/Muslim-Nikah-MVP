package com.nikahtech.muslimnikah.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.nikahtech.muslimnikah.R;

public final class PopupDialogUtil {

    private PopupDialogUtil() {
        // Utility class
    }

    public enum Type {
        SUCCESS,
        ERROR,
        WARNING,
        DOC
    }

    public static void show(
            @NonNull Context context,
            @NonNull Type type,
            String title,
            @NonNull String message,
            String buttonText,
            View.OnClickListener actionListener
    ) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_popup, null, false);
        dialog.setContentView(view);

        setupWindow(dialog);

        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        MaterialButton btnAction = view.findViewById(R.id.actionBtn);

        // ---------- Title handling ----------
        if (TextUtils.isEmpty(title)) {
            txtTitle.setVisibility(View.GONE);
        } else {
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
        }

        txtMessage.setTextSize(16);
        txtMessage.setText(message);

        // ---------- Style by type ----------
        DialogStyle style = DialogStyle.from(type, context);

        imgIcon.setImageResource(style.iconRes);
        btnAction.setBackgroundTintList(style.buttonColor);

        // ---------- Button ----------
        btnAction.setText(
                TextUtils.isEmpty(buttonText) ? "Okay" : buttonText
        );

        btnAction.setOnClickListener(v -> {
            dialog.dismiss();
            if (actionListener != null) {
                actionListener.onClick(v);
            }
        });

        dialog.show();
    }

    // -------------------- Helpers --------------------

    private static void setupWindow(Dialog dialog) {
        if (dialog.getWindow() == null) return;

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
        dialog.getWindow().getAttributes().windowAnimations =
                R.style.PopupDialogAnimation;
    }

    private static final class DialogStyle {
        final int iconRes;
        final android.content.res.ColorStateList buttonColor;

        private DialogStyle(int iconRes, android.content.res.ColorStateList buttonColor) {
            this.iconRes = iconRes;
            this.buttonColor = buttonColor;
        }

        static DialogStyle from(Type type, Context context) {

            int icon;
            int color;

            switch (type) {
                case SUCCESS:
                    icon = R.drawable.icon_success;
                    color = R.color.colorSuccess;
                    break;

                case ERROR:
                    icon = R.drawable.icon_failure;
                    color = R.color.colorAlert;
                    break;

                case DOC:
                    icon = R.drawable.icon_document;
                    color = R.color.colorInfo;
                    break;

                case WARNING:
                default:
                    icon = R.drawable.icon_failure;
                    color = R.color.colorWarning;
                    break;
            }

            return new DialogStyle(
                    icon,
                    ContextCompat.getColorStateList(context, color)
            );
        }
    }
}
