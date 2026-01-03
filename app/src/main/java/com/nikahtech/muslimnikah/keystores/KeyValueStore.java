package com.nikahtech.muslimnikah.keystores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

public class KeyValueStore {

    private static final String TAG = "KeyValueStore";
    private static final String PREFS_NAME = "kvstore_prefs";
    private static final String ENCRYPTED_KEY_PREF = "encrypted_mmkv_key";
    private static final String KEYSTORE_ALIAS = "MuslimNikah_KV_RSA";
    private static final String MMKV_ID_PREFIX = "muslim_nikah_mmkv_";

    private MMKV mmkv;

    public KeyValueStore(Context context, String storeId) {
        String id = MMKV_ID_PREFIX + (storeId == null ? "default" : storeId);
        try {
            String root = MMKV.initialize(context.getApplicationContext());
            Log.d(TAG, "MMKV root: " + root);

            String cryptKey = obtainOrCreateEncryptedMMKVKey(context);

            if (cryptKey != null) {
                mmkv = MMKV.mmkvWithID(id, MMKV.SINGLE_PROCESS_MODE, cryptKey);
                Log.d(TAG, "Encrypted MMKV initialized for storeId=" + id);
            } else {
                mmkv = MMKV.mmkvWithID(id, MMKV.SINGLE_PROCESS_MODE);
                Log.w(TAG, "Fallback to unencrypted MMKV for storeId=" + id);
            }
        } catch (Exception e) {
            Log.e(TAG, "KeyValueStore init failed. Using plain MMKV.", e);
            mmkv = MMKV.mmkvWithID(id, MMKV.SINGLE_PROCESS_MODE);
        }
    }

    // --- Core Methods ---
    public synchronized void insertOrUpdate(String key, String value) { mmkv.encode(key, value); }
    public synchronized String get(String key) { return mmkv.decodeString(key, null); }
    public synchronized void delete(String key) { mmkv.removeValueForKey(key); }
    public synchronized void clearAll() { mmkv.clearAll(); }
    public synchronized boolean containsKey(String key) { return mmkv.containsKey(key); }
    public synchronized void close() { mmkv.close(); }

    public synchronized Map<String, String> getAllData() {
        Map<String, String> allData = new HashMap<>();

        try {
            String[] keys = mmkv.allKeys();
            if (keys != null) {
                for (String key : keys) {
                    String value = mmkv.decodeString(key, null);
                    allData.put(key, value);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to fetch all MMKV data", e);
        }

        return allData;
    }

    // --- Typed helpers ---
    public synchronized void putBoolean(String key, boolean v) { mmkv.encode(key, v); }
    public synchronized boolean getBoolean(String key) { return mmkv.decodeBool(key, false); }

    public synchronized void putInt(String key, int v) { mmkv.encode(key, v); }
    public synchronized int getInt(String key) { return mmkv.decodeInt(key, 0); }

    public synchronized void putLong(String key, long v) { mmkv.encode(key, v); }
    public synchronized long getLong(String key) { return mmkv.decodeLong(key, 0L); }

    public synchronized void putFloat(String key, float v) { mmkv.encode(key, v); }
    public synchronized float getFloat(String key) { return mmkv.decodeFloat(key, 0f); }

    public synchronized void putBytes(String key, byte[] b) { mmkv.encode(key, b); }
    public synchronized byte[] getBytes(String key) { return mmkv.decodeBytes(key); }

    // --- Encryption Key Handling ---
    private String obtainOrCreateEncryptedMMKVKey(Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String encKey = prefs.getString(ENCRYPTED_KEY_PREF, null);

            if (encKey != null) {
                byte[] encrypted = Base64.decode(encKey, Base64.DEFAULT);
                byte[] decrypted = keystoreDecrypt(encrypted);
                if (decrypted != null && decrypted.length > 0)
                    return new String(decrypted, StandardCharsets.UTF_8);
            }

            // create new key
            String newKey = UUID.randomUUID().toString().replace("-", "");
            byte[] encrypted = keystoreEncrypt(newKey.getBytes(StandardCharsets.UTF_8), context);
            if (encrypted != null) {
                prefs.edit().putString(ENCRYPTED_KEY_PREF,
                        Base64.encodeToString(encrypted, Base64.DEFAULT)).apply();
                return newKey;
            }
        } catch (Exception e) {
            Log.e(TAG, "obtainOrCreateEncryptedMMKVKey failed", e);
        }
        return null;
    }

    private byte[] keystoreEncrypt(byte[] plain, Context context) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            if (!ks.containsAlias(KEYSTORE_ALIAS)) generateRSAKeyPair(context);
            PublicKey pub = ks.getCertificate(KEYSTORE_ALIAS).getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            return cipher.doFinal(plain);
        } catch (Exception e) {
            Log.e(TAG, "Encrypt failed", e);
            return null;
        }
    }

    private byte[] keystoreDecrypt(byte[] encrypted) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            PrivateKey priv = ((KeyStore.PrivateKeyEntry) ks.getEntry(KEYSTORE_ALIAS, null)).getPrivateKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priv);
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            Log.e(TAG, "Decrypt failed", e);
            return null;
        }
    }

    private void generateRSAKeyPair(Context context) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                        KEYSTORE_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .build();
                kpg.initialize(spec);
            } else {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 30);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(
                        context.getApplicationContext())
                        .setAlias(KEYSTORE_ALIAS)
                        .setSubject(new X500Principal("CN=" + KEYSTORE_ALIAS))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                kpg.initialize(spec);
            }
            kpg.generateKeyPair();
            Log.d(TAG, "RSA keypair generated");
        } catch (Exception e) {
            Log.e(TAG, "RSA keypair generation failed", e);
        }
    }
}
