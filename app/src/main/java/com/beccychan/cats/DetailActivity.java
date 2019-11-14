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

import static com.beccychan.cats.HomeFragment.MENU_DESCRIPTION;
import static com.beccychan.cats.HomeFragment.MENU_ID;
import static com.beccychan.cats.HomeFragment.MENU_IMAGE;
import static com.beccychan.cats.HomeFragment.MENU_NAME;
import static com.beccychan.cats.HomeFragment.MENU_PRICE;

public class DetailActivity extends AppCompatActivity {
    private TextView orderAmount;
    private int orderAmountCount = 1;

    private String menuId;
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

        menuId = intent.getStringExtra(MENU_ID);
        menuImage = intent.getStringExtra(MENU_IMAGE);
        menuName = intent.getStringExtra(MENU_NAME);
        menuPrice = intent.getStringExtra(MENU_PRICE);
        menuDescription = intent.getStringExtra(MENU_DESCRIPTION);

        ImageView menuImageView = findViewById(R.id.menu_image_detail);
        TextView menuNameView = findViewById(R.id.menu_name_detail);
        TextView menuPriceView = findViewById(R.id.menu_price_detail);
        TextView menuDescriptionView = findViewById(R.id.menu_description_detail);

        Picasso.get().load("file:///android_asset/" + menuImage).fit().centerInside().into(menuImageView);
        menuNameView.setText(menuName);
        menuPriceView.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(Double.valueOf(menuPrice)));
        menuDescriptionView.setText(menuDescription);

        orderAmount = findViewById(R.id.order_amount);
        Button orderIncrease = findViewById(R.id.order_increase);
        Button orderDecrease = findViewById(R.id.order_decrease);
        Button orderAdd = findViewById(R.id.order_add);

        orderIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        orderDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

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

    private void increase() {
        if (orderAmountCount < 10) {
            orderAmountCount++;
            orderAmount.setText(String.valueOf(orderAmountCount));
        }
    }

    private void decrease() {
        if (orderAmountCount > 1) {
            orderAmountCount--;
            orderAmount.setText(String.valueOf(orderAmountCount));
        }
    }

    private void add() {
        ArrayList<FavouritesItem> orderList;
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
        editor.putString("orders", json);
        editor.apply();

        Toast.makeText(getApplicationContext(),"Your order has been added.", Toast.LENGTH_SHORT).show();
    }

//  Persists number of items ordered on screen rotation.
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", orderAmountCount);
    }
}
