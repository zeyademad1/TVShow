package com.example.ztv.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ztv.Adapters.EpisodesAdapter;
import com.example.ztv.Adapters.ViewPagerAdapter;
import com.example.ztv.R;
import com.example.ztv.Utils.TempHolder;
import com.example.ztv.ViewModels.TVShowDetailsViewModel;
import com.example.ztv.databinding.ActivityTvshowDetailsBinding;
import com.example.ztv.databinding.BottomSheetEpisodesBinding;
import com.example.ztv.models.TvShow;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TVShowDetails extends AppCompatActivity {
    ActivityTvshowDetailsBinding binding;
    boolean readMoreOrLess = true;
    private TVShowDetailsViewModel viewModel;
    private TvShow tvShow;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetEpisodesBinding bottomSheetEpisodesBinding;
    private Boolean isAvailableInWatchList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        doInitialization();

    }

    private void CheckValidityInWatchList() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.GetTvShowByID(tvShow.getId())
                .subscribeOn(Schedulers.computation())
                .subscribe(tvShow1 -> {
                    isAvailableInWatchList = true;
                    binding.watchList.setImageResource(R.drawable.ic_done);
                    compositeDisposable.dispose();
                }));
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        binding.back.setOnClickListener(v -> {
            //To Return to previous page
            onBackPressed();
        });
        tvShow = (TvShow) getIntent().getSerializableExtra(MainActivity.BUNDLE_KEY);
        CheckValidityInWatchList();
        GetShowDetails();
    }

    private void GetShowDetails() {
        binding.setIsLoading(true);
        viewModel.GetShowDetails(String.valueOf(tvShow.getId())).observe(this
                , tvShowDetailsResponse -> {
                    binding.setIsLoading(false);
                    if (tvShowDetailsResponse != null) {
                        if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                            binding.watchList.setOnClickListener(c -> {
                                if (isAvailableInWatchList) {
                                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                                    compositeDisposable.add(viewModel.DeleteFromWatchList(tvShow)
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(Schedulers.io())
                                            .subscribe(() -> {
                                                Toast.makeText(getBaseContext(), "Deleted From WatchList Successfully!! ", Toast.LENGTH_SHORT).show();
                                                binding.watchList.setImageResource(R.drawable.ic_watch);
                                                isAvailableInWatchList = false;
                                                compositeDisposable.dispose();
                                                TempHolder.Is_WatchList_Updated = true;

                                            }));
                                } else {
                                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                                    compositeDisposable.add(viewModel.AddToWatchList(tvShow)
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(Schedulers.newThread())
                                            .subscribe(() -> {
                                                Toast.makeText(getBaseContext(), "Deleted From WatchList Successfully!! ", Toast.LENGTH_SHORT).show();
                                                binding.watchList.setImageResource(R.drawable.ic_done);
                                                isAvailableInWatchList = true;
                                                compositeDisposable.dispose();
                                                TempHolder.Is_WatchList_Updated = true;

                                            }));
                                }
                            });
                            binding.ImageShow.setVisibility(View.VISIBLE);
                            binding.tvDescription.setVisibility(View.VISIBLE);
                            binding.tvReadMore.setVisibility(View.VISIBLE);
                            binding.tvName.setVisibility(View.VISIBLE);
                            binding.tvNetwork.setVisibility(View.VISIBLE);
                            binding.tvStarted.setVisibility(View.VISIBLE);
                            binding.tvStatus.setVisibility(View.VISIBLE);
                            binding.layout.setVisibility(View.VISIBLE);
                            binding.divider.setVisibility(View.VISIBLE);
                            binding.bottomDivider.setVisibility(View.VISIBLE);
                            AttachImageToViewPager(tvShowDetailsResponse
                                    .getTvShowDetails().getPictures());
                            binding.setTvShowImageurl(tvShowDetailsResponse.
                                    getTvShowDetails().getImage_path());
                            binding.setTvShow(tvShow);
                            binding.setTvDescription(String.valueOf(HtmlCompat
                                    .fromHtml(tvShowDetailsResponse
                                                    .getTvShowDetails().getDescription(),
                                            HtmlCompat.FROM_HTML_MODE_COMPACT)));
                            binding.tvReadMore.setOnClickListener(v -> SwitchReadMore());
                            binding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                            binding.setLength(String.valueOf(tvShowDetailsResponse
                                    .getTvShowDetails().getRuntime()));
                            binding.setRating(String.valueOf(tvShowDetailsResponse
                                    .getTvShowDetails().getRating()));

                            /**
                             * For Website Button
                              **/
                            binding.btnWebsite.setOnClickListener(w -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                                startActivity(intent);
                            });
                            /**
                             * For Episodes Button
                              **/
                            binding.btnEpisodes.setOnClickListener(e -> {
                                if (bottomSheetDialog == null) {
                                    bottomSheetDialog = new BottomSheetDialog(TVShowDetails.this);
                                    bottomSheetEpisodesBinding = DataBindingUtil.inflate(
                                            LayoutInflater.from(TVShowDetails.this),
                                            R.layout.bottom_sheet_episodes,
                                            findViewById(R.id.episodeContainer),
                                            false);
                                }
                                /**
                                 *  For Bottom Sheet Dialouge
                                   */

                                bottomSheetDialog.setContentView(bottomSheetEpisodesBinding.getRoot());
                                bottomSheetEpisodesBinding.episodesList.setAdapter(new EpisodesAdapter(
                                        tvShowDetailsResponse.getTvShowDetails().getEpisodes()));
                                bottomSheetEpisodesBinding.Textile.setText(
                                        String.format("Episode | %s", tvShow.getTvName())
                                );
                                bottomSheetEpisodesBinding.close.setOnClickListener(c ->
                                        bottomSheetDialog.dismiss());
                                bottomSheetDialog.show();
                                FrameLayout frameLayout = bottomSheetDialog.findViewById(
                                        com.google.android.material.R.id.design_bottom_sheet
                                );
                                if (frameLayout != null) {
                                    BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior
                                            .from(frameLayout);
                                    bottomSheetBehavior.setPeekHeight(Resources.getSystem()
                                            .getDisplayMetrics().heightPixels);
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }

                            });
                        }

                    }
                });
    }

    private void SwitchReadMore() {
        if (readMoreOrLess) {
            binding.tvDescription.setMaxLines(Integer.MAX_VALUE);
            binding.tvDescription.setEllipsize(null);
            binding.tvReadMore.setText(R.string.read_less);
            readMoreOrLess = false;
        } else {
            binding.tvDescription.setMaxLines(4);
            binding.tvDescription.setEllipsize(TextUtils.TruncateAt.END);
            binding.tvReadMore.setText(R.string.read_more);
            readMoreOrLess = true;
        }
    }

    private void AttachImageToViewPager(@NonNull String[] images) {
        binding.swipePhotos.setAdapter(new ViewPagerAdapter(images));
        binding.swipePhotos.setOffscreenPageLimit(1);
        binding.swipePhotos.setVisibility(View.VISIBLE);
        binding.view.setVisibility(View.VISIBLE);
        SetupIndicator(images.length);
        binding.swipePhotos.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                SetupCurrentIndicator(position);
            }
        });
    }

    /**
     * Work on Indicators and Current progress
     */

    //Setup indicator
    private void SetupIndicator(int count) {
        ImageView[] images = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageView(getApplicationContext());
            images[i].setLayoutParams(layoutParams);
            images[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext()
                    , R.drawable.indicator_inactive));
            binding.indicator.addView(images[i]);
        }
        binding.indicator.setVisibility(View.VISIBLE);

    }

    //Setup Current Indicator
    private void SetupCurrentIndicator(int pos) {
        int child = binding.indicator.getChildCount();
        for (int i = 0; i < child; i++) {
            ImageView imageView = (ImageView) binding.indicator.getChildAt(i);
            if (pos == i) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.indicator_inactive));
            }
        }
    }

}

