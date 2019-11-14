package com.beccychan.cats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

//icon_stats, value_stats, key_stats

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsViewHolder> {
    private ArrayList<CatBreed> catList;


    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        public ImageView statsIcon;
        public TextView statsValue;
        public TextView statsKey;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            statsIcon = itemView.findViewById(R.id.icon_stats);
            statsValue = itemView.findViewById(R.id.value_stats);
            statsKey = itemView.findViewById(R.id.key_stats)
        }
    }

    public StatsAdapter(ArrayList<CatBreed> list) {
        catList = list;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_stats, parent, false);
        StatsViewHolder statsViewHolder = new StatsViewHolder(v);
        return statsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        CatBreed currentItem = catList.get(position);
        HashMap<String, String> temporaryList = new HashMap<>();
        temporaryList.put("Origin", currentItem.getOrigin());
        temporaryList.put("Life Span", currentItem.getLife_span());
        temporaryList.put("Weight", currentItem.getWeight().getMetric());
        temporaryList.put("Dog Friendly", currentItem.getDog_friendly());
        temporaryList.put("Wikipedia Link", currentItem.getWikipedia_url());


        holder.statsValue.setText(currentItem.get);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
