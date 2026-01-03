package com.nikahtech.muslimnikah.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base64Util {

    // Convert File → Base64 String
    public static String encodeFileToBase64(File file) throws IOException {
        byte[] fileContent;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileContent = new byte[(int) file.length()];
            int bytesRead = fileInputStream.read(fileContent);
            if (bytesRead != fileContent.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        }
        return Base64.encodeToString(fileContent, Base64.NO_WRAP);
    }

    // Convert Base64 String → byte[]
    public static byte[] decodeBase64ToBytes(String base64String) {
        return Base64.decode(base64String, Base64.DEFAULT);
    }
}
