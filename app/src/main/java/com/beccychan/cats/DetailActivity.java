package com.beccychan.cats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView orderAmount;
    private int orderAmountCount = 1;

    private String catId;
    private String menuImage;
    private String menuName;
    private String menuPrice;
    private String menuDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getActionBar() != null){ getActionBar().setDisplayHomeAsUpEnabled(true); }

        Intent intent = getIntent();

        catId = intent.getStringExtra(CAT_ID);

        ImageView menuImageView = findViewById(R.id.menu_image_detail);
        TextView menuNameView = findViewById(R.id.menu_name_detail);
        TextView menuPriceView = findViewById(R.id.menu_price_detail);
        TextView menuDescriptionView = findViewById(R.id.menu_description_detail);

        Picasso.get().load("file:///android_asset/" + menuImage).fit().centerInside().into(menuImageView);
        menuNameView.setText(menuName);
        menuPriceView.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(Double.valueOf(menuPrice)));
        menuDescriptionView.setText(menuDescription);

        Button orderAdd = findViewById(R.id.order_add);

        orderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });

        if (savedInstanceState != null) {
            orderAmountCount = savedInstanceState.getInt("count");
            orderAmount.setText(String.valueOf(orderAmountCount));
        }
    }


    private void add() {
        ArrayList<FavouritesItem> favouritesList;
        FavouritesItem order = new FavouritesItem(orderAmountCount, Integer.valueOf(menuId), menuImage, menuName, Double.valueOf(menuPrice), menuDescription);

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("orders", null);
        Gson gson = new Gson();

        Type menuJson = new TypeToken<ArrayList<FavouritesItem>>(){}.getType();
        orderList = gson.fromJson(json, menuJson);

        if (orderList == null) {
            orderList = new ArrayList<>();
        }

        orderList.add(order);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        json = gson.toJson(orderList);
        editor.putString("favourites", json);
        editor.apply();

        Toast.makeText(getApplicationContext(),"Added to Favourites.", Toast.LENGTH_SHORT).show();
    }

//  Persists number of items ordered on screen rotation.
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", orderAmountCount);
    }
}
