package com.nikahtech.muslimnikah.utils;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Utility class for fetching the FCM token in a simplified callback format.
 * Always triggers onComplete(success, token, error) exactly once.
 */
public class FcmTokenUtil {

    private static final String TAG = "FcmTokenUtil";

    public interface TokenCallback {
        /**
         * Called when the FCM token fetch completes.
         *
         * @param success true if token was fetched successfully
         * @param token   FCM token string (null if failed)
         * @param error   Exception in case of failure (null if success)
         */
        void onComplete(boolean success, String token, Exception error);
    }

    /**
     * Fetches the FCM token asynchronously.
     * Always triggers onComplete(success, token, error).
     */
    public static void fetchFcmToken(TokenCallback callback) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    boolean success = false;
                    String token = null;
                    Exception error = null;

                    try {
                        if (task.isSuccessful()) {
                            token = task.getResult();
                            success = true;
                            Log.d(TAG, "FCM Token fetched successfully: " + token);
                        } else {
                            error = task.getException();
                            Log.w(TAG, "Fetching FCM token failed", error);
                        }
                    } catch (Exception e) {
                        error = e;
                        Log.e(TAG, "Unexpected error fetching FCM token", e);
                    } finally {
                        if (callback != null) {
                            callback.onComplete(success, token, error);
                        }
                    }
                });
    }
}
