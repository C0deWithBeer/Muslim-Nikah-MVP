package com.nikahtech.muslimnikah.keystores;

import android.content.Context;
import android.util.Log;

import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.Backend.dto.eto.UserDto;
import com.nikahtech.muslimnikah.utils.JSONUtil;

import java.util.Map;

public class LocalDBManager {

    private static final String TAG = "LocalDBManager";
    private static KeyValueStore store;
    private static final Object lock = new Object();

    public static final String UserDB = "userdb";
    public static final String AuthDB = "authdb";
    public static final String ProfileDB = "profiledb";


    private LocalDBManager() {}

    /**
     * Initialize the single secure KeyValueStore.
     * Call once in Application.onCreate (recommended).
     *
     * @param context application context
     */
    public static void initialize(Context context) {
        initialize(context, null);
    }

    /**
     * Initialize with explicit storeId and optional passphrase (passphrase ignored; keystore manages encryption).
     *
     * @param context application context
     * @param storeId optional custom store id; if null, a default is used.
     */
    public static void initialize(Context context, String storeId) {
        synchronized (lock) {
            if (store == null) {
                try {
                    String sId = (storeId == null || storeId.isEmpty()) ? "app_main_store" : storeId;
                    store = new KeyValueStore(context.getApplicationContext(), sId);
                    Log.d(TAG, "LocalDBManager initialized with storeId=" + sId);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to initialize LocalDBManager", e);
                    // do not crash; leave store null
                }
            }
        }
    }

    private static void ensureInit(Context context) {
        if (store == null) {
            initialize(context);
        }
    }

    public static Map<String, String> getAllDataFromDB(Context context) {
        return store.getAllData();
    }

    public static KeyValueStore getStore(Context context) {
        ensureInit(context);
        return store;
    }

    public static ProfileDto getProfile(Context context) {
        ProfileDto saved = JSONUtil.fromJson(LocalDBManager.getStore(context).get("profile"), ProfileDto.class);
        Log.d(TAG, "LocalDB: "+saved.toString());
        if (saved != null && saved.getId() != null) {
            return saved;
        } else {
            return null;
        }
    }

    public static void wipeAll(Context context) {
        ensureInit(context);
        try {
            store.clearAll();
            store.close();
        } catch (Exception e) {
            Log.e(TAG, "wipeAll failed", e);
        } finally {
            store = null;
        }
    }
}
