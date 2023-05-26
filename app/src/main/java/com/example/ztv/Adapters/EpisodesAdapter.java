package com.example.ztv.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ztv.R;
import com.example.ztv.databinding.EpisodeItemBinding;
import com.example.ztv.models.Episodes;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.TVShowViewHolder> {
    List<Episodes> episodes;
    LayoutInflater layoutInflater;


    public EpisodesAdapter(List<Episodes> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        EpisodeItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.episode_item, parent, false);
        return new TVShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.onBind(episodes.get(position));
    }


    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        EpisodeItemBinding binding;

        public TVShowViewHolder(@NonNull EpisodeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(@NonNull Episodes episode) {
            String title = "S";
            String season = String.valueOf(episode.getSeason());
            // if the number in one digit put 0 before it like 01 02 03 ....etc
            if (season.length() == 1) {
                season = "0".concat(season);
            }
            String episodeNumber = String.valueOf(episode.getEpisode());
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(" " + season).concat(" " + episodeNumber);
            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getDate());
        }
    }
}
