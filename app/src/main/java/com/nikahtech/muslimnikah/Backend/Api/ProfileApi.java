package com.nikahtech.muslimnikah.Backend.Api;


import com.nikahtech.muslimnikah.Backend.ApiResponse;
import com.nikahtech.muslimnikah.Backend.dto.eto.ProfileDto;
import com.nikahtech.muslimnikah.models.Profile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileApi {

    @POST("api/profiles/save/{userId}")
    Call<ApiResponse<ProfileDto>> saveProfile(
            @Path("userId") Long userId,
            @Body ProfileDto profileDto
    );

    @PUT("api/profiles/{profileId}")
    Call<ApiResponse<ProfileDto>> updateProfile(
            @Path("profileId") Long profileId,
            @Body ProfileDto profileDto
    );

    @GET("api/profiles/{profileId}")
    Call<ApiResponse<ProfileDto>> getProfileById(
            @Path("profileId") Long profileId
    );
}

