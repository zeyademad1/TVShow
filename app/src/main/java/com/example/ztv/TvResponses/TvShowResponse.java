package com.example.ztv.TvResponses;

import com.example.ztv.models.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {

    @SerializedName("page")
    private int page;

    // We Will Use it for endless Paging with Recyclerview
    @SerializedName("pages")
    private int total_pages;

    @SerializedName("tv_shows")
    private List<TvShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }
}
