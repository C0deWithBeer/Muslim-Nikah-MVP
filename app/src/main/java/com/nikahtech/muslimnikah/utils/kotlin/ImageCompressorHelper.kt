package com.codewithbeer.muslimnikah.Helper.Kotlin

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.runBlocking
import java.io.File

object ImageCompressorHelper {
    @JvmStatic
    fun compressImage(context: Context, actualImageFile: File, width: Int, height: Int, quality: Int, size: Long): File {
        Log.d("ImageCompressor", "Original size: ${actualImageFile.length() / 1024} KB")

        val compressed = runBlocking {
            Compressor.compress(context, actualImageFile) {
                resolution(width, height)               // force downscale
                quality(quality)                        // 0 = worst, 100 = best (lower = smaller file)
                format(Bitmap.CompressFormat.WEBP) // WEBP is usually smaller than JPEG
                size(size)                      // max 200 KB
            }
        }

        Log.d("ImageCompressor", "Compressed size: ${compressed.length() / 1024} KB")
        Log.d("ImageCompressor", "------------------------------------------------------------\n")


        return compressed
    }
}