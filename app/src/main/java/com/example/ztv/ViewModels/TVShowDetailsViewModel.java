package com.example.ztv.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ztv.Databases.TvShowDataBase;
import com.example.ztv.Reposoteries.TVShowDetailsRepository;
import com.example.ztv.TvResponses.TVShowDetailsResponse;
import com.example.ztv.models.TvShow;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TvShowDataBase tvShowDataBase;

    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowDataBase = TvShowDataBase.getInstance(application);
    }

    public LiveData<TVShowDetailsResponse> GetShowDetails(String tvID) {
        return tvShowDetailsRepository.GetTVDetails(tvID);
    }

    @NonNull
    public Completable AddToWatchList(TvShow tvShow) {
        return tvShowDataBase.tvShowDAO().AddToWatchList(tvShow);
    }

    public Flowable<TvShow> GetTvShowByID(int id) {
        return tvShowDataBase.tvShowDAO().GetTvShowById(id);
    }
    public Completable DeleteFromWatchList(TvShow tvShow){
        return tvShowDataBase.tvShowDAO().DeleteFromWatchList(tvShow);
    }
}
