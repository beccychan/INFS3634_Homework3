package com.beccychan.cats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements CatAdapter.OnItemClickListener {
    public static final String CAT_ID = "catId";
    public static String catBreedURL = "catBreedURL";

    private RecyclerView catRecyclerView;
    private CatAdapter catAdapter;
    private ArrayList<CatBreed> catList;

    /*
    String searchUrl = "https://api.thecatapi.com/v1/images/search?breed_id=" + catID;

    StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    String apiText = response.toString();
                    System.out.println("Cat ID is:" + catID);
                    System.out.println("Cat Response: " + apiText);
                    CatImage[] catImageObj = gson.fromJson(apiText, CatImage[].class);

                    if (catImageObj.length != 0) {
                        catImageUrl = catImageObj[0].getUrl();
                        CatBreed[] catBreeds = catImageObj[0].getBreeds();
                        tempCat = catBreeds[0];
                        actionsAfterImageQuerySuccess();
                    } else {
                        System.out.println("API TEXT RETURNED NOTHING");
                        actionsAfterImageQueryFailure();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("NO BUENO", error.toString());
            Log.d("PARSE FINISHED", "WITH ERROR");
            //com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host "api.nytimes.com": No address associated with hostname
        }
    }
    ) {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<String, String>();
            //headers.put("x-api-key", "e11cbe67-4e8f-4f95-85e6-c634fc9582b5");
            return headers;
        }
    }; */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        catRecyclerView = view.findViewById(R.id.cat_recyclerView);
        catRecyclerView.setHasFixedSize(true);
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Context context;
        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        catBreedURL = "https://api.thecatapi.com/v1/breeds";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, catBreedURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.substring(500));
                //create new gson
                //get cat images/breeds from gson
                Gson gson = new Gson();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-key", "99c5c080-ad1d-481e-b5c3-f5e3572732a0");
                return headers;
            }
        };


//        loadMenu();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.cat_search_menu, menu);
        android.view.MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadMenu() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = loadJSON("menu.json");
        editor.putString("menu", json);
        editor.apply();

        Type menuJson = new TypeToken<ArrayList<CatBreed>>(){}.getType();
        catList = gson.fromJson(json, menuJson);

        /*Type collectionType = new TypeToken<List<Cat>>(){}.getType();
        catList = (List<Cat>) new Gson()
                .fromJson( apiText , collectionType);*/

        catAdapter = new CatAdapter(getActivity(), catList);
        catRecyclerView.setAdapter(catAdapter);
        catAdapter.setOnItemClickListener(HomeFragment.this);
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
        CatBreed clickedItem = catList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("clickedCat", clickedItem);

//        detailIntent.putExtra(CAT_ID, clickedItem.getId());
//        detailIntent.putExtra(CAT_NAME, clickedItem.getName());
//        detailIntent.putExtra(CAT_TEMPERAMENT, clickedItem.getTemperament());
//        detailIntent.putExtra(CAT_DESCRIPTION, clickedItem.getDescription());
//        detailIntent.putExtra(CAT_ORIGIN, clickedItem.getOrigin());
//        detailIntent.putExtra(CAT_LIFESPAN, clickedItem.getLife_span());
//        detailIntent.putExtra(, clickedItem.getDog_friendly());
//        detailIntent.putExtra(, clickedItem.getWeight().getMetric());
//        detailIntent.putExtra(, clickedItem.getWikipedia_url());


        detailIntent.putExtras(bundle);
        startActivity(detailIntent);
    }
}
