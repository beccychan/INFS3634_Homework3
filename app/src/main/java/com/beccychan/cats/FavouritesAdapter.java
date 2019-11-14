package com.beccychan.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>{
    private ArrayList<FavouritesItem> favouritesList;
    private Context checkoutContext;
    private OnItemClickListener favouritesListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        favouritesListener = listener;
    }

    public FavouritesAdapter(Context context, ArrayList<FavouritesItem> list) {
        favouritesList = list;
        checkoutContext = context;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_item, parent, false);
        return new FavouritesViewHolder(v, favouritesListener);
    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }


    //Make decision on what to put into the favourites screen.
    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        FavouritesItem favouritesItem = favouritesList.get(position);
        String favouriteImage = checkoutItem.getItemImage();
        String favouriteName = checkoutItem.getItemName();
        Double favouritePrice = checkoutItem.getItemPrice();
        Integer favouriteQuantity = checkoutItem.getItemAmount();

        //breed
        //weight (metrics)
        //

        Picasso.get().load("file:///android_asset/" + checkoutImage).fit().centerInside().into(holder.checkoutImage);
        holder.checkoutName.setText(checkoutName);
        holder.checkoutPrice.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(checkoutPrice * checkoutQuantity));
        holder.checkoutQuantity.setText(checkoutContext.getString(R.string.quantity, checkoutQuantity));

    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView checkoutImage;
        public TextView checkoutName;
        public TextView checkoutPrice;
        public TextView checkoutQuantity;
        public Button deleteMenuItem;

        public FavouritesViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            checkoutImage = itemView.findViewById(R.id.checkout_image);
            checkoutName = itemView.findViewById(R.id.checkout_name);
            checkoutPrice = itemView.findViewById(R.id.checkout_price);
            checkoutQuantity = itemView.findViewById(R.id.checkout_quantity);
            deleteMenuItem = itemView.findViewById(R.id.checkout_delete);

            deleteMenuItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }


    }

}