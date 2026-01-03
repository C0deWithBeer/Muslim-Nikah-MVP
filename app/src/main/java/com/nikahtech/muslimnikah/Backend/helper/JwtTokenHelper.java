package com.nikahtech.muslimnikah.Backend.helper;

import android.content.Context;

import com.nikahtech.muslimnikah.keystores.SecureKeyStoreUtil;
import com.nikahtech.muslimnikah.keystores.SecureKeyValueStore;

public class JwtTokenHelper {

    private static SecureKeyValueStore tokenStore;
    private static String SecureDBName = "secure_db";
    private static String SecureDBTableName = "secure_table";

    private static void initializeSecureDB(Context context) throws Exception {
        if (tokenStore == null) {
            byte[] aesKey = SecureKeyStoreUtil.getOrCreateKey(context);
            tokenStore = new SecureKeyValueStore(
                    context.getApplicationContext(),
                    SecureDBName,
                    SecureDBTableName,
                    aesKey
            );
        }
    }


    public static boolean saveJwtTokens(Context context, String accessToken, String refreshToken) {
        try {
            initializeSecureDB(context);
            tokenStore.insertOrUpdate("accessToken", accessToken);
            tokenStore.insertOrUpdate("refreshToken", refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getJwtAccessToken(Context context) {
        try {
            initializeSecureDB(context);
            return tokenStore.get("accessToken");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve JWT access token", e);
        }
    }

    public static String getJwtRefreshToken(Context context) {
        try {
            initializeSecureDB(context);
            return tokenStore.get("refreshToken");
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve JWT refresh token", e);
        }
    }


    public static void removeAllTokens(Context context) {
        try {
            initializeSecureDB(context);
            tokenStore.delete("accessToken");
            tokenStore.delete("refreshToken");
        } catch (Exception e) {
            // Log or handle the error if needed, but do not crash the app
            e.printStackTrace();
        }
    }
}

