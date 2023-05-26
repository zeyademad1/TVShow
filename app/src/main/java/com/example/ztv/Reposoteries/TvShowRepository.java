package com.example.ztv.Reposoteries;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ztv.Networking.Api;
import com.example.ztv.Networking.ApiClient;
import com.example.ztv.TvResponses.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {

    private  Api api;


    public TvShowRepository() {
        api = ApiClient.getRetrofit().create(Api.class);
    }

    @NonNull
    public LiveData<TvShowResponse> getTvShow(int page) {
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        api.getPopularTvShows(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
