package com.nikahtech.muslimnikah.keystores;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class SecureKeyStoreUtil {
    private static final String PREF_NAME = "secure_prefs";
    private static final String KEY_AES = "aes_key";

    public static byte[] getOrCreateKey(Context context) throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String encodedKey = prefs.getString(KEY_AES, null);

        if (encodedKey != null) {
            return Base64.decode(encodedKey, Base64.NO_WRAP);
        } else {
            byte[] key = SecureKeyValueStore.generateKey();
            prefs.edit().putString(KEY_AES, Base64.encodeToString(key, Base64.NO_WRAP)).apply();
            return key;
        }
    }
}
