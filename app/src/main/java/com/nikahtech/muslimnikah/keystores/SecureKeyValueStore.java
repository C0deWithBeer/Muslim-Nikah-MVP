package com.nikahtech.muslimnikah.keystores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureKeyValueStore {

    private final SQLiteDatabase database;
    private final String tableName;
    private static final int DATABASE_VERSION = 1;
    private static final String columnKey = "`key`";  // Escaped for safety
    private static final String columnValue = "value";

    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int AES_KEY_SIZE = 256;

    private final SecretKey secretKey;

    public SecureKeyValueStore(Context context, String databaseName, String tableName, byte[] keyBytes) {
        this.tableName = tableName;

        // Initialize secret key from bytes
        this.secretKey = new SecretKeySpec(keyBytes, "AES");

        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(context, databaseName, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                createTableIfNotExists(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + tableName);
                createTableIfNotExists(db);
            }

            private void createTableIfNotExists(SQLiteDatabase db) {
                String tableCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        columnKey + " TEXT PRIMARY KEY, " +
                        columnValue + " TEXT);";
                db.execSQL(tableCreate);
            }
        };

        this.database = dbHelper.getWritableDatabase();
        ensureTableExists();
    }

    private void ensureTableExists() {
        String tableCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                columnKey + " TEXT PRIMARY KEY, " +
                columnValue + " TEXT);";
        database.execSQL(tableCreate);
    }

    /** Insert or update encrypted value */
    public void insertOrUpdate(String key, String value) throws Exception {
        String encryptedValue = encrypt(value);

        ContentValues values = new ContentValues();
        values.put(columnKey, key);
        values.put(columnValue, encryptedValue);

        database.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /** Get and decrypt value */
    public String get(String key) throws Exception {
        String[] columns = { columnValue };
        String selection = columnKey + " = ?";
        String[] selectionArgs = { key };

        try (Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                String encryptedValue = cursor.getString(cursor.getColumnIndexOrThrow(columnValue));
                return decrypt(encryptedValue);
            }
        }
        return null;
    }

    public void delete(String key) {
        if (key == null || key.trim().isEmpty()) {
            // Skip null or empty keys — nothing to delete
            return;
        }

        try {
            // Check if the key actually exists in the table before deleting
            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnKey + " = ?";
            try (Cursor cursor = database.rawQuery(query, new String[]{key})) {
                if (cursor != null && cursor.moveToFirst()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        String selection = columnKey + " = ?";
                        String[] selectionArgs = { key };
                        database.delete(tableName, selection, selectionArgs);
                    }
                }
            }
        } catch (Exception e) {
            // Log error instead of throwing, to avoid crashes
            e.printStackTrace();
        }
    }


    public void clear() {
        database.delete(tableName, null, null);
    }

    public boolean containsKey(String key) {
        String[] columns = { columnKey };
        String selection = columnKey + " = ?";
        String[] selectionArgs = { key };

        try (Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    public void close() {
        database.close();
    }

    // ================= ENCRYPTION/DECRYPTION =================
    private String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Store IV + encrypted text (Base64)
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.encodeToString(combined, Base64.NO_WRAP);
    }

    private String decrypt(String cipherText) throws Exception {
        byte[] combined = Base64.decode(cipherText, Base64.NO_WRAP);
        byte[] iv = new byte[12];
        byte[] encrypted = new byte[combined.length - 12];

        System.arraycopy(combined, 0, iv, 0, 12);
        System.arraycopy(combined, 12, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /** Utility to generate a new AES key */
    public static byte[] generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }
}
