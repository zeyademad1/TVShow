package com.example.ztv.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ztv.R;
import com.example.ztv.databinding.ViewpagerImageBinding;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

    String[] images;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(String[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ViewpagerImageBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.viewpager_image, parent, false);
        return new ViewPagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.onBind(images[position]);
    }


    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        ViewpagerImageBinding binding;

        public ViewPagerViewHolder(@NonNull ViewpagerImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void onBind(String imageurl) {
            binding.setImageurl(imageurl);
        }
    }
}
