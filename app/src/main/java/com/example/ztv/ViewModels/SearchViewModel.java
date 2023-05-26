package com.example.ztv.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ztv.Reposoteries.SearchTvShowRepository;
import com.example.ztv.TvResponses.TvShowResponse;

public class SearchViewModel extends ViewModel {
    private SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel() {
        searchTvShowRepository = new SearchTvShowRepository();
    }

    public LiveData<TvShowResponse> SearchTvShow(String query, int page) {
        return searchTvShowRepository.SearchTvShow(query, page);
    }

}
