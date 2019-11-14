package com.beccychan.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    private Context catContext;
    private ArrayList<CatBreed> catList;
    private ArrayList<CatBreed> catListFull;
    private OnItemClickListener catListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        catListener = listener;
    }

    public CatAdapter(Context context, ArrayList<CatBreed> list) {
        catContext = context;
        catList = list;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(catContext).inflate(R.layout.menu_item, parent, false);
        return new CatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CatBreed currentCat = catList.get(position);
        String catImage = currentCat.get(); //need to get from linking API
        String catName = currentCat.getName();
//        String catDescription = currentCat.getCatDescription();

        //will need to call API to load image
        Picasso.get().load("file:///android_asset/" + catImage).fit().centerInside().into(holder.catImage);
        holder.catName.setText(catName);
       // holder.menuPrice.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(menuPrice));
//        holder.menuDescription.setText(menuDescription);
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        public ImageView catImage;
        public TextView catName;
//        public TextView menuDescription;

        public CatViewHolder(View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.menu_image); //need to get from menu_item
            catName = itemView.findViewById(R.id.menu_name);
//            menuDescription = itemView.findViewById(R.id.cat_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (catListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            catListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
