package com.beccychan.cats;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FavouritesFragment extends Fragment {
    private RecyclerView favouritesRecyclerView;
    private FavouritesAdapter favouritesAdapter;
    private RecyclerView.LayoutManager favouritesLayoutManager;
    private ArrayList<CatBreed> favouritesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        favouritesList = load("favourites", favouritesList);
        view = buildRecyclerView(view);

        return view;
    }

    public void removeItem(int position) {
        favouritesList.remove(position);
        favouritesAdapter.notifyItemRemoved(position);
        delete("favourites", favouritesList);
    }

    private ArrayList<CatBreed> load(String key, ArrayList<CatBreed> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type menuJson = new TypeToken<ArrayList<CatBreed>>(){}.getType();
        list = gson.fromJson(json, menuJson);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    private void delete(String key, ArrayList<CatBreed> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("favourites", gson.toJson(list));
        editor.apply();
    }

    public View buildRecyclerView(View view) {
        favouritesRecyclerView = view.findViewById(R.id.favourites_recyclerView);
        //if we want to set the size: checkoutRecyclerView.setHasFixedSize(true);
        favouritesLayoutManager = new LinearLayoutManager(view.getContext());
        favouritesAdapter = new FavouritesAdapter(view.getContext(), favouritesList);

        favouritesRecyclerView.setLayoutManager(favouritesLayoutManager);
        favouritesRecyclerView.setAdapter(favouritesAdapter);

        favouritesAdapter.setOnItemClickListener(new FavouritesAdapter.OnItemClickListener(){
            @Override
            public void onDeleteClick (int position){
                removeItem(position);
            }
        });
        return view;
    }
}
