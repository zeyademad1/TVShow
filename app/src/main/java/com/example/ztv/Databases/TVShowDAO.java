package com.example.ztv.Databases;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ztv.models.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDAO {

    @Query("SELECT * FROM tvShow")
    Flowable<List<TvShow>> GetTvShow();

    @NonNull
    @Insert
    Completable AddToWatchList(TvShow tvShow);

    @NonNull
    @Delete
    Completable DeleteFromWatchList(TvShow tvShow);

    @Query("select * from tvShow where id=:id")
    Flowable<TvShow> GetTvShowById(int id);

}
