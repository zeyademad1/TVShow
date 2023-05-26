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

public class SearchTvShowRepository {
    Api api;

    public SearchTvShowRepository() {
        api = ApiClient.getRetrofit().create(Api.class);
    }

    public LiveData<TvShowResponse> SearchTvShow(String query , int page){
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        api.SearchForTvShow(query , page).enqueue(new Callback<TvShowResponse>() {
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
