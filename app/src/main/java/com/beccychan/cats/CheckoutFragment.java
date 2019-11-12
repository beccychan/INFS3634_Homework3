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

public class CheckoutFragment extends Fragment {
    private RecyclerView checkoutRecyclerView;
    private CheckoutAdapter checkoutAdapter;
    private RecyclerView.LayoutManager checkoutLayoutManager;
    private TextView checkoutTotal;
    private Double total;
    private ArrayList<CheckoutItem> checkoutList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        checkoutList = load("orders", checkoutList);
        view = buildRecyclerView(view);

        return view;
    }

    public void removeItem(int position) {
        checkoutList.remove(position);
        checkoutAdapter.notifyItemRemoved(position);
        delete("orders", checkoutList);
        updateTotal();
    }

    private void updateTotal(){
        total = orderTotal(checkoutList);
        checkoutTotal.setText(this.getActivity().getString(R.string.total, NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(total)));
    }

    private ArrayList<CheckoutItem> load(String key, ArrayList<CheckoutItem> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type menuJson = new TypeToken<ArrayList<CheckoutItem>>(){}.getType();
        list = gson.fromJson(json, menuJson);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    private void delete(String key, ArrayList<CheckoutItem> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("orders", gson.toJson(list));
        editor.apply();
    }

    private Double orderTotal(ArrayList<CheckoutItem> list) {
        int count = 0;
        double sum = 0.0;
        while(list.size() > count) {
            CheckoutItem item = list.get(count);
            sum += (item.getItemAmount() * item.getItemPrice());
            count++;
        }
        return sum;
    }

    public View buildRecyclerView(View view) {
        checkoutRecyclerView = view.findViewById(R.id.checkout_recyclerView);
        //if we want to set the size: checkoutRecyclerView.setHasFixedSize(true);
        checkoutLayoutManager = new LinearLayoutManager(view.getContext());
        checkoutAdapter = new CheckoutAdapter(view.getContext(), checkoutList);

        checkoutRecyclerView.setLayoutManager(checkoutLayoutManager);
        checkoutRecyclerView.setAdapter(checkoutAdapter);

        checkoutTotal = view.findViewById(R.id.checkout_total);

        total = orderTotal(checkoutList);
        checkoutTotal.setText(this.getActivity().getString(R.string.total, NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(total)));

        checkoutAdapter.setOnItemClickListener(new CheckoutAdapter.OnItemClickListener(){
            @Override
            public void onDeleteClick (int position){
                removeItem(position);
            }
        });
        return view;
    }
}
