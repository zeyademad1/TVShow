package com.example.ztv.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ztv.Databases.TvShowDataBase;
import com.example.ztv.models.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private TvShowDataBase tvShowDataBase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        tvShowDataBase = TvShowDataBase.getInstance(application);
    }

    public Flowable<List<TvShow>> GetWatchListMovies() {
        return tvShowDataBase.tvShowDAO().GetTvShow();
    }
    @NonNull
    public Completable DeleteFromWatchList(TvShow tvShow){
        return tvShowDataBase.tvShowDAO().DeleteFromWatchList(tvShow);
    }
}
