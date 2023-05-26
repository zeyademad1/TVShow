package com.example.ztv.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "tvShow")
public class TvShow  implements Serializable{
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String tvName;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("country")
    private String country;

    @SerializedName("network")
    private String network;

    @SerializedName("status")
    private String status;

    @SerializedName("image_thumbnail_path")
    private String image_thumbnail;

    public void setId(int id) {
        this.id = id;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage_thumbnail(String image_thumbnail) {
        this.image_thumbnail = image_thumbnail;
    }

    public int getId() {
        return id;
    }

    public String getTvName() {
        return tvName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getImage_thumbnail() {
        return image_thumbnail;
    }
}
