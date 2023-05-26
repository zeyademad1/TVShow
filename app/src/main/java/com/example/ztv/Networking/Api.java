package com.example.ztv.Networking;

import androidx.annotation.NonNull;

import com.example.ztv.TvResponses.TVShowDetailsResponse;
import com.example.ztv.TvResponses.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @NonNull
    @GET("most-popular")
    Call<TvShowResponse> getPopularTvShows(
            @Query("page") int page);

    @NonNull
    @GET("show-details")
    Call<TVShowDetailsResponse> getTVDetails(@Query("q") String tvShowID);

    @GET("search")
    Call<TvShowResponse> SearchForTvShow(
            @Query("q") String query
            , @Query("page") int page
    );
}
