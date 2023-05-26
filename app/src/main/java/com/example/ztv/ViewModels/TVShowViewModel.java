package com.example.ztv.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ztv.Reposoteries.TvShowRepository;
import com.example.ztv.TvResponses.TvShowResponse;

public class TVShowViewModel extends ViewModel {

    private  TvShowRepository repository;

    public TVShowViewModel(){
        repository = new TvShowRepository();
    }

    public LiveData<TvShowResponse> getTVShow(int page){
        return  repository.getTvShow(page);
    }


}
