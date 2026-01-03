package com.nikahtech.muslimnikah.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.UUID;

/**
 * Utility class for retrieving stable device information such as:
 * - A persistent unique device ID (based on ANDROID_ID or generated UUID)
 * - The device name (manufacturer + model)
 *
 * This class ensures a stable identifier across app restarts while respecting privacy.
 */
public class DeviceInfoUtil {

    private static final String TAG = "DeviceInfoUtil";
    private static final String PREFS_NAME = "device_prefs";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_USED_ANDROID_ID = "used_android_id";

    /**
     * Returns a stable device ID.
     * First tries ANDROID_ID. If unavailable or invalid, generates a UUID and persists it.
     *
     * @param context Application context
     * @return Unique device ID
     */
    public static String getDeviceId(Context context) {
        try {
            @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );

            if (androidId != null && !androidId.isEmpty() && !androidId.equalsIgnoreCase("9774d56d682e549c")) {
                saveDeviceId(context, androidId, true);
                return androidId;
            } else {
                Log.w(TAG, "ANDROID_ID unavailable or invalid. Using fallback UUID.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching ANDROID_ID: " + e.getMessage(), e);
        }

        // Fallback: return stored UUID or generate a new one
        String savedId = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_DEVICE_ID, null);

        if (savedId != null) {
            return savedId;
        }

        String uuid = UUID.randomUUID().toString();
        saveDeviceId(context, uuid, false);
        return uuid;
    }

    private static void saveDeviceId(Context context, String id, boolean usedAndroidId) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_DEVICE_ID, id)
                .putBoolean(KEY_USED_ANDROID_ID, usedAndroidId)
                .apply();

        Log.d(TAG, "Device ID saved: " + id + " (usedAndroidId=" + usedAndroidId + ")");
    }

    /**
     * Checks if the current device ID was derived from ANDROID_ID.
     */
    public static boolean isAndroidIdUsed(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_USED_ANDROID_ID, false);
    }

    /**
     * Returns the device name in a user-friendly format.
     * Example: "Samsung Galaxy S23" or "Xiaomi Redmi Note 12"
     *
     * @return Readable device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model != null && model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * Capitalizes the first letter of the given string.
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

