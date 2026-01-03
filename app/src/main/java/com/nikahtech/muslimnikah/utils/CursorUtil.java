package com.nikahtech.muslimnikah.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;

public class CursorUtil {

    public static void apply(EditText editText, int cursorDrawableRes) {
        try {
            // 1️⃣ Force cursor visible
            editText.setCursorVisible(true);

            // 2️⃣ Android 10+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Drawable drawable = ContextCompat.getDrawable(
                        editText.getContext(), cursorDrawableRes
                );
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    editText.setTextCursorDrawable(drawable);
                }
                return;
            }

            // 3️⃣ Pre-Android 10 (FreedomOTPView FIX)
            @SuppressLint("SoonBlockedPrivateApi") Field cursorResField =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            cursorResField.setAccessible(true);
            cursorResField.set(editText, cursorDrawableRes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
