package com.example.ztv.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ztv.Listners.OnShowClicked;
import com.example.ztv.R;
import com.example.ztv.databinding.ItemTvshowContainerBinding;
import com.example.ztv.models.TvShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    List<TvShow> shows;
    LayoutInflater layoutInflater;
    OnShowClicked onShowClicked;


    public TVShowAdapter(List<TvShow> shows, OnShowClicked onShowClicked) {
        this.shows = shows;
        this.onShowClicked = onShowClicked;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTvshowContainerBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_tvshow_container, parent, false);
        return new TVShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.onBind(shows.get(position));
    }


    @Override
    public int getItemCount() {
        return shows.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        ItemTvshowContainerBinding binding;

        public TVShowViewHolder(@NonNull ItemTvshowContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(TvShow tvshow) {
            binding.setTvShow(tvshow);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> {
                onShowClicked.onShowClicked(tvshow);
            });


        }
    }
}
