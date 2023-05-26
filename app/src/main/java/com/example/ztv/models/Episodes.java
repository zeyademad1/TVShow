package com.example.ztv.models;

import com.google.gson.annotations.SerializedName;

public class Episodes {

    @SerializedName("season")
    private int season;

    @SerializedName("episode")
    private int episode;

    @SerializedName("name")
    private String name;

    @SerializedName("air_date")
    private String  date;

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
