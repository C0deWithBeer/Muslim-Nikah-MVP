package com.nikahtech.muslimnikah.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public final class ImageUtil {

    private ImageUtil() {
        // Utility class
    }

    /**
     * Converts an image Uri into a WEBP RequestBody (safe + memory efficient)
     */
    @NonNull
    public static RequestBody webp(@NonNull Context context, @NonNull Uri uri)
            throws IOException {

        Bitmap bitmap = decodeBitmap(context, uri);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            bitmap.compress(
                    Bitmap.CompressFormat.WEBP_LOSSY,
                    85,
                    out
            );
        } else {
            bitmap.compress(
                    Bitmap.CompressFormat.WEBP,
                    85,
                    out
            );
        }

        byte[] bytes = out.toByteArray();

        return RequestBody.create(
                bytes,
                MediaType.parse("image/webp")
        );
    }

    /**
     * Decodes bitmap safely across all Android versions
     */
    private static Bitmap decodeBitmap(Context context, Uri uri)
            throws IOException {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.Source source =
                    ImageDecoder.createSource(context.getContentResolver(), uri);
            return ImageDecoder.decodeBitmap(source,
                    (decoder, info, src) -> decoder.setAllocator(
                            ImageDecoder.ALLOCATOR_SOFTWARE
                    ));
        } else {
            return MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri
            );
        }
    }
}
