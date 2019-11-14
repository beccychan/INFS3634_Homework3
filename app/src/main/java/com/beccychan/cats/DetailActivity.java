package com.beccychan.cats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.beccychan.cats.HomeFragment.CAT_ID;

public class DetailActivity extends AppCompatActivity {

    private String catId;
    private String catImage;
    private String catName;
    private String temperament;
    private String catDescription;
    private String catDescriptionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getActionBar() != null){ getActionBar().setDisplayHomeAsUpEnabled(true); }

        Intent intent = getIntent();

        catId = intent.getStringExtra(CAT_ID);

        ImageView catImageView = findViewById(R.id.image_detail);
        TextView catNameView = findViewById(R.id.name_detail);
        TextView temperamentView = findViewById(R.id.temperament_detail);
        TextView catDescriptionTitleView = findViewById(R.id.descriptionTitle_detail);
        TextView catDescriptionView = findViewById(R.id.description_detail);

        Picasso.get().load("file:///android_asset/" + catImage).fit().centerInside().into(catImageView); //load from API instead
        catNameView.setText(catName);
        temperamentView.setText(temperament);
        catDescriptionView.setText(catDescription);
        catDescriptionTitleView.setText(catDescriptionTitle);

        ImageButton favButton = findViewById(R.id.favoriteButton_detail);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });

        /*if (savedInstanceState != null) {
            orderAmountCount = savedInstanceState.getInt("count");
            orderAmount.setText(String.valueOf(orderAmountCount));
        }*/
    }


    private void add() {
        ArrayList<FavouritesItem> favouritesList;
        FavouritesItem favourite = new FavouritesItem(Integer.valueOf(catId), catImage, catName, temperament, catDescriptionTitle, catDescription);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("favourites", null);
        Gson gson = new Gson();

        Type favouritesJson = new TypeToken<ArrayList<FavouritesItem>>(){}.getType();
        favouritesList = gson.fromJson(json, favouritesJson);

        if (favouritesList == null) {
            favouritesList = new ArrayList<>();
        }

        favourites.add(favourite);

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
