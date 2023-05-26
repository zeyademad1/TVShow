package com.example.ztv.UI;

import static com.example.ztv.UI.MainActivity.BUNDLE_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ztv.Adapters.TVShowAdapter;
import com.example.ztv.Listners.OnShowClicked;
import com.example.ztv.R;
import com.example.ztv.ViewModels.SearchViewModel;
import com.example.ztv.databinding.ActivitySearchWatchListBinding;
import com.example.ztv.models.TvShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchTvShows extends AppCompatActivity implements OnShowClicked {
    private ActivitySearchWatchListBinding binding;
    private SearchViewModel searchViewModel;
    private TVShowAdapter adapter;
    private List<TvShow> tvShowList;
    private int currentPage = 1;
    private int totalPages = 1;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        binding.imageBack.setOnClickListener(c -> onBackPressed());
        tvShowList = new ArrayList<>();
        binding.SearchList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (binding.SearchList.canScrollVertically(1)) {
                    if (currentPage <= totalPages) {
                        currentPage += 1;
                        Search(binding.searchEt.getText().toString());
                    }

                }
            }
        });


        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                if (!s.toString().trim().isEmpty()) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> Search(s.toString()));
                        }
                    }, 800);
                } else {
                    tvShowList.clear();
                    adapter.notifyDataSetChanged();
                }

            }
        });
        binding.searchEt.requestFocus();
    }

    private void Search(String query) {
        searchViewModel.SearchTvShow(query, currentPage).observe(this
                , tvShowResponse -> {
                    toggleLoading();
                    if (tvShowResponse != null) {
                        totalPages = tvShowResponse.getTotal_pages();
                        if (tvShowResponse.getTvShows() != null) {
                            toggleLoading();
                            int oldPages = tvShowList.size();
                            tvShowList.addAll(tvShowResponse.getTvShows());
                            adapter = new TVShowAdapter(tvShowList, this);
                            binding.SearchList.setAdapter(adapter);
                            binding.SearchList.setHasFixedSize(true);
                            adapter.notifyItemRangeInserted(oldPages, tvShowList.size());
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
        Intent intent = new Intent(SearchTvShows.this, TVShowDetails.class);
        intent.putExtra(BUNDLE_KEY, tvShow);
        startActivity(intent);
    }
}