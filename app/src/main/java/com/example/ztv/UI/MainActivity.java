package com.example.ztv.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ztv.Adapters.TVShowAdapter;
import com.example.ztv.Listners.OnShowClicked;
import com.example.ztv.NetworkReceiver;
import com.example.ztv.R;
import com.example.ztv.ViewModels.TVShowViewModel;
import com.example.ztv.databinding.ActivityMainBinding;
import com.example.ztv.models.TvShow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnShowClicked {

    public static final String BUNDLE_KEY = "tv_show";
    private final List<TvShow> tvShows = new ArrayList<>();
    ActivityMainBinding binding;
    NetworkReceiver changeListner;
    IntentFilter filter;
    private TVShowViewModel viewModel;
    private TVShowAdapter adapter;
    private int currentPage = 1;
    private int totalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
        changeListner = new NetworkReceiver();
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    }

    private void doInitialization() {

        viewModel = new ViewModelProvider(this).get(TVShowViewModel.class);
        adapter = new TVShowAdapter(tvShows, this);
        binding.TvShowList.setHasFixedSize(true);
        binding.TvShowList.setLayoutManager(new LinearLayoutManager(this));
        binding.TvShowList.setAdapter(adapter);
        binding.imageSearch.setOnClickListener(x -> startActivity(
                new Intent(MainActivity.this, SearchTvShows.class)));

        /* For endless Paging */
        binding.TvShowList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.TvShowList.canScrollVertically(1)) {
                    if (currentPage <= totalPages) {
                        currentPage += 1;
                        GetTvShows();
                    }
                }
            }
        });
        GetTvShows();
        binding.imageWatchList.setOnClickListener(r -> startActivity(
                new Intent(MainActivity.this, WatchList.class)));
    }


    @SuppressLint("NotifyDataSetChanged")
    private void GetTvShows() {
        toggleLoading();
        viewModel.getTVShow(currentPage).observe(this, tvShowResponse -> {
            if (tvShowResponse != null) {
                toggleLoading();
                if (tvShowResponse.getTvShows() != null) {
                    tvShows.addAll(tvShowResponse.getTvShows());
                    adapter.notifyDataSetChanged();
                    totalPages = tvShowResponse.getTotal_pages();
                    int oldCount = tvShows.size();
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }
        } else {
            /* the page is 2 and more , we will activate the second progress bar */
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()) {
                binding.setIsLoadingMore(false);
            } else {
                binding.setIsLoadingMore(true);
            }

        }
    }

    @Override
    public void onShowClicked(TvShow tvShow) {
        Intent intent = new Intent(MainActivity.this, TVShowDetails.class);
        intent.putExtra(BUNDLE_KEY, tvShow);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(changeListner, filter);


    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(changeListner);
        super.onDestroy();
    }


}