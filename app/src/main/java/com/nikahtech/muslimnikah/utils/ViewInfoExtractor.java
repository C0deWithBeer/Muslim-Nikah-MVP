package com.nikahtech.muslimnikah.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.textview.MaterialTextView;

/**
 * Utility class for extracting structured info from MaterialTextView.
 */
public class ViewInfoExtractor {

    private static final String TAG = "ViewInfoExtractor";

    /**
     * Extracts view information (text, icon, tint, etc.) from a MaterialTextView.
     */
    public static TextViewInfo extractTextViewInfo(Context context, MaterialTextView textView) {
        if (textView == null) return null;

        try {
            // 🆔 Get view ID name (safely)
            String idName;
            try {
                idName = context.getResources().getResourceEntryName(textView.getId());
            } catch (Exception e) {
                idName = "no_id";
            }

            // 📝 Get text content
            String textContent = textView.getText() != null ? textView.getText().toString() : "";

            // 🎨 Get drawableStart (index 0)
            Drawable[] drawables = textView.getCompoundDrawables();
            Drawable startDrawable = drawables != null ? drawables[0] : null;
            String drawableName = null;

            // 1) Preferred: check tag set on the view (Integer or String)
            Object tagObj = textView.getTag();
            if (tagObj != null) {
                try {
                    // If developer set integer resource id as tag
                    if (tagObj instanceof Integer) {
                        int resId = (Integer) tagObj;
                        drawableName = resolveDrawableName(context, resId);
                    } else {
                        // If developer set string tag such as "icon_phone"
                        String tagStr = String.valueOf(tagObj);
                        int resId = context.getResources().getIdentifier(tagStr, "drawable", context.getPackageName());
                        if (resId != 0) drawableName = resolveDrawableName(context, resId);
                        else drawableName = tagStr; // keep raw tag
                    }
                } catch (Exception e) {
                    Log.w(TAG, "Tag present but failed to resolve drawable name: " + e.getMessage());
                }
            }

            // 2) Fallback: try to introspect drawable to get a resource id (best-effort, reflection)
            if (drawableName == null && startDrawable != null) {
                drawableName = tryExtractResNameFromDrawable(context, startDrawable);
            }

            // 3) Final fallback: unknown
            if (drawableName == null) drawableName = "unknown_drawable";

            // 🎨 Get drawable tint color (if available)
            String tintHex = "#000000"; // Default
            try {
                ColorStateList tintList = textView.getCompoundDrawableTintList();
                if (tintList != null) {
                    int color = tintList.getDefaultColor();
                    tintHex = String.format("#%06X", (0xFFFFFF & color));
                }
            } catch (Exception e) {
                // ignore
            }

            // 🧱 Build model
            TextViewInfo info = new TextViewInfo();
            info.setViewId(idName);
            info.setText(textContent);
            info.setDrawableStart(drawableName);
            info.setDrawableTintHex(tintHex);
            return info;

        } catch (Exception e) {
            Log.e(TAG, "Failed to extract TextView info", e);
            return null;
        }
    }

    private static String resolveDrawableName(Context context, int resId) {
        try {
            return context.getResources().getResourceEntryName(resId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Best-effort: tries to extract a resource name from a Drawable using reflection.
     * Works sometimes for VectorDrawableCompat / Resources$BitmapDrawable constant states.
     */
    private static String tryExtractResNameFromDrawable(Context context, Drawable d) {
        try {
            // unwrap compat wrappers
            Drawable unwrapped = DrawableCompat.unwrap(d);
            if (unwrapped == null) unwrapped = d;
            // try known fields via reflection (not guaranteed)
            try {
                // many Drawable.ConstantState implementations may hold a resId field
                Object constantState = unwrapped.getConstantState();
                if (constantState != null) {
                    // try "mResId" or "mResourceId" names that appear in some implementations
                    for (String fieldName : new String[]{"mResId", "mResourceId", "mResIdValue"}) {
                        try {
                            java.lang.reflect.Field f = constantState.getClass().getDeclaredField(fieldName);
                            f.setAccessible(true);
                            Object val = f.get(constantState);
                            if (val instanceof Integer) {
                                int rid = (Integer) val;
                                String name = resolveDrawableName(context, rid);
                                if (name != null) return name;
                            }
                        } catch (NoSuchFieldException ignored) {
                            // try next
                        }
                    }
                }
            } catch (Throwable t) {
                // reflection can fail on some devices/implementations — ignore
            }

            // Last resort: return the class simple name of the drawable (useful for debugging)
            return unwrapped.getClass().getSimpleName();

        } catch (Exception e) {
            Log.w(TAG, "Reflection extraction failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * POJO model representing TextView info.
     */
    public static class TextViewInfo {
        private Integer position;
        private String viewId;          // e.g., "sbf4_txt8"
        private String text;            // e.g., "Priority Customer Support."
        private String drawableStart;   // e.g., "icon_phone"
        private String drawableTintHex; // e.g., "#014D17"

        public TextViewInfo() {
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public String getViewId() {
            return viewId;
        }

        public void setViewId(String viewId) {
            this.viewId = viewId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDrawableStart() {
            return drawableStart;
        }

        public void setDrawableStart(String drawableStart) {
            this.drawableStart = drawableStart;
        }

        public String getDrawableTintHex() {
            return drawableTintHex;
        }

        public void setDrawableTintHex(String drawableTintHex) {
            this.drawableTintHex = drawableTintHex;
        }
    }
}
