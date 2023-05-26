package com.example.ztv.Listners;

import com.example.ztv.models.TvShow;

public interface WatchListListeners {

    void onWatchListClicked(TvShow tvShow);

    void onDeleteClicked(TvShow tvShow, int position);
}
