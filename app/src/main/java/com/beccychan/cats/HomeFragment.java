package com.beccychan.cats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements MenuAdapter.OnItemClickListener {
    public static final String MENU_ID = "menuId";
    public static final String MENU_IMAGE = "menuImage";
    public static final String MENU_NAME = "menuName";
    public static final String MENU_PRICE = "menuPrice";
    public static final String MENU_DESCRIPTION = "menuDescription";

    private RecyclerView menuRecyclerView;
    private MenuAdapter menuAdapter;
    private ArrayList<MenuItem> menuList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        menuRecyclerView = view.findViewById(R.id.menu_recyclerView);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadMenu();
        return view;
    }

    private void loadMenu() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = loadJSON("menu.json");
        editor.putString("menu", json);
        editor.apply();

        Type menuJson = new TypeToken<ArrayList<MenuItem>>(){}.getType();
        menuList = gson.fromJson(json, menuJson);

        menuAdapter = new MenuAdapter(getActivity(), menuList);
        menuRecyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(HomeFragment.this);
    }

    private String loadJSON(String filename) {
        String json = null;
        try {
            InputStream is = this.getActivity().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        MenuItem clickedItem = menuList.get(position);

        detailIntent.putExtra(MENU_ID, String.valueOf(clickedItem.getMenuId()));
        detailIntent.putExtra(MENU_IMAGE, clickedItem.getMenuImage());
        detailIntent.putExtra(MENU_NAME, clickedItem.getMenuName());
        detailIntent.putExtra(MENU_PRICE, String.valueOf(clickedItem.getMenuPrice()));
        detailIntent.putExtra(MENU_DESCRIPTION, clickedItem.getMenuDescription());

        startActivity(detailIntent);
    }
}
