package com.nikahtech.muslimnikah.Backend.Api;

import com.nikahtech.muslimnikah.Backend.ApiResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ImageUploadApi {

    /**
     * Step 1: Request a presigned URL from the backend.
     * The backend responds with a secure temporary URL
     * where the image can be uploaded directly to R2.
     *
     * @param fileName the unique filename (key) to be uploaded.
     * @return ApiResponse containing the presigned upload URL.
     */
    @GET("api/r2/presigned-url")
    Call<ApiResponse<String>> getPresignedUrl(@Query("fileName") String fileName);

    /**
     * Step 2: Upload the image file directly to R2 using the presigned URL.
     * This request does NOT go to your backend — it goes straight to R2.
     *
     * @param url  the presigned URL obtained from {@link #getPresignedUrl(String)}
     * @param file the image file body to be uploaded (image/webp or other content type)
     * @return ResponseBody — Cloudflare R2 does not return JSON; a 200/204 means success.
     */
    @PUT
    Call<ResponseBody> uploadImage(@Url String url, @Body RequestBody file);

    /**
     * Step 3: Delete an uploaded image by its filename (key).
     * Requests the backend to delete the object from R2.
     *
     * @param fileName the filename/key to delete from the bucket.
     * @return ApiResponse indicating success or failure.
     */
    @DELETE("api/r2/delete")
    Call<ApiResponse<Map<String, Object>>> deleteImage(@Query("fileName") String fileName);

}
