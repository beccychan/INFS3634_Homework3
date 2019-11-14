package com.beccychan.cats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private CatBreed clickedCat;

    private RecyclerView statsRecyclerView;
    private StatsAdapter statsAdapter;
    private RecyclerView.LayoutManager statsLayoutManager;
    private ArrayList<Stat> statsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getActionBar() != null){ getActionBar().setDisplayHomeAsUpEnabled(true); }

        Intent intent = getIntent();
        clickedCat = (CatBreed) intent.getSerializableExtra("clickedCat");

        loadUI();
    }

    public void loadUI() {
        ImageView catImageView = findViewById(R.id.image_detail);
        TextView catNameView = findViewById(R.id.name_detail);
        TextView temperamentView = findViewById(R.id.temperament_detail);
        TextView catDescriptionView = findViewById(R.id.description_detail);

        //ADD GSON API CALL TO LOAD THE DATA

//        Picasso.get().load("file:///android_asset/" + catImage).fit().centerInside().into(catImageView); //load from API instead
        catNameView.setText(clickedCat.getName());
        temperamentView.setText(clickedCat.getTemperament());
        catDescriptionView.setText(clickedCat.getDescription());

        //RECYCLERVIEW
        statsRecyclerView = findViewById(R.id.stats_recyclerView);
        statsRecyclerView.setHasFixedSize(true);
        statsLayoutManager = new LinearLayoutManager(this);

        statsList = addStats(clickedCat);
        statsAdapter = new StatsAdapter(statsList);

        statsRecyclerView.setLayoutManager(statsLayoutManager);
        statsRecyclerView.setAdapter(statsAdapter);

        //FAVOURITES BUTTON
        ImageButton favButton = findViewById(R.id.favoriteButton_detail);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
    }

    public ArrayList<Stat> addStats(CatBreed cat) {
        ArrayList<Stat> catStats = new ArrayList<>();

        catStats.add(new Stat("Origin", cat.getOrigin()));
        catStats.add(new Stat("Life Span", cat.getLife_span()));
        catStats.add(new Stat("Weight", cat.getWeight().getMetric()));
        catStats.add(new Stat("Dog Friendly", cat.getDog_friendly()));
        catStats.add(new Stat("Wikipedia URL", cat.getWikipedia_url()));

        return catStats;
    }

    private void add() {
        ArrayList<CatBreed> favouritesList;
        CatBreed favourite = clickedCat;

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("favourites", null);
        Gson gson = new Gson();

        Type favouritesJson = new TypeToken<ArrayList<CatBreed>>(){}.getType();
        favouritesList = gson.fromJson(json, favouritesJson);

        if (favouritesList == null) {
            favouritesList = new ArrayList<>();
        }

        favouritesList.add(favourite);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(favouritesList);
        editor.putString("favourites", json);
        editor.apply();

        Toast.makeText(getApplicationContext(),"Added to Favourites.", Toast.LENGTH_SHORT).show();
    }
/*
//  Persists number of items ordered on screen rotation.
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", orderAmountCount);
    }*/
}
