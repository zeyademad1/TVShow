package com.example.ztv.Reposoteries;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ztv.Networking.Api;
import com.example.ztv.Networking.ApiClient;
import com.example.ztv.TvResponses.TVShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private static Api api;

    public TVShowDetailsRepository() {
        api = ApiClient.getRetrofit().create(Api.class);
    }

    @NonNull
    public  LiveData<TVShowDetailsResponse> GetTVDetails(String tvID) {
        MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
        api.getTVDetails(tvID).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
