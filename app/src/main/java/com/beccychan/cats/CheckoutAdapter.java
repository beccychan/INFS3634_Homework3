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

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>{
    private ArrayList<CheckoutItem> checkoutList;
    private Context checkoutContext;
    private OnItemClickListener checkoutListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        checkoutListener = listener;
    }

    public CheckoutAdapter (Context context, ArrayList<CheckoutItem> list) {
        checkoutList = list;
        checkoutContext = context;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        return new CheckoutViewHolder(v, checkoutListener);
    }

    @Override
    public int getItemCount() {
        return checkoutList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        CheckoutItem checkoutItem = checkoutList.get(position);
        String checkoutImage = checkoutItem.getItemImage();
        String checkoutName = checkoutItem.getItemName();
        Double checkoutPrice = checkoutItem.getItemPrice();
        Integer checkoutQuantity = checkoutItem.getItemAmount();

        Picasso.get().load("file:///android_asset/" + checkoutImage).fit().centerInside().into(holder.checkoutImage);
        holder.checkoutName.setText(checkoutName);
        holder.checkoutPrice.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(checkoutPrice * checkoutQuantity));
        holder.checkoutQuantity.setText(checkoutContext.getString(R.string.quantity, checkoutQuantity));

    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {
        public ImageView checkoutImage;
        public TextView checkoutName;
        public TextView checkoutPrice;
        public TextView checkoutQuantity;
        public Button deleteMenuItem;

        public CheckoutViewHolder(View itemView, final OnItemClickListener listener) {
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