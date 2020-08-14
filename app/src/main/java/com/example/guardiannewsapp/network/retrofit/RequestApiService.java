package com.example.guardiannewsapp.network.retrofit;

import com.example.guardiannewsapp.network.entities.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RequestApiService {
    @GET("search?show-elements=image&show-fields=thumbnail")
    Call<BaseResponse> search(@Header("api-key") String apiKey);
}
