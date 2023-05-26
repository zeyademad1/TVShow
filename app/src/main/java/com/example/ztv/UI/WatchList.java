package com.example.ztv.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.ztv.Adapters.WatchListAdapter;
import com.example.ztv.Listners.WatchListListeners;
import com.example.ztv.R;
import com.example.ztv.Utils.TempHolder;
import com.example.ztv.ViewModels.WatchListViewModel;
import com.example.ztv.databinding.ActivityWatchListBinding;
import com.example.ztv.models.TvShow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchList extends AppCompatActivity implements WatchListListeners {
    ActivityWatchListBinding binding;
    private WatchListAdapter adapter;
    private List<TvShow> WatchListMovies;
    private WatchListViewModel watchListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        watchListViewModel = new ViewModelProvider(this)
                .get(WatchListViewModel.class);
        binding.close.setOnClickListener(c -> onBackPressed());
        binding.listWatchLater.setHasFixedSize(true);
        WatchListMovies = new ArrayList<>();
        loadWatchList();
    }

    void loadWatchList() {
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.GetWatchListMovies().observeOn(Schedulers.computation())
                .subscribe((tvShows -> {
                    binding.setIsLoading(false);
                    if (WatchListMovies.size() > 0) {
                        WatchListMovies.clear();
                    }
                    WatchListMovies.addAll(tvShows);
                    adapter = new WatchListAdapter(WatchListMovies, this);
                    binding.listWatchLater.setAdapter(adapter);
                    compositeDisposable.dispose();
                })));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempHolder.Is_WatchList_Updated) {
            loadWatchList();
            TempHolder.Is_WatchList_Updated = false;
        }
    }

    @Override
    public void onWatchListClicked(TvShow tvShow) {
        Intent intent = new Intent(WatchList.this, TVShowDetails.class);
        intent.putExtra(MainActivity.BUNDLE_KEY, tvShow);
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(TvShow tvShow, int position) {
        // use the delete method in the viewModel!!
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.DeleteFromWatchList(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe(() -> {
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
                    compositeDisposable.dispose();
                }));

    }
}