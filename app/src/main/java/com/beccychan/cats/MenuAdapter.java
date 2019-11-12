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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context menuContext;
    private ArrayList<MenuItem> menuList;
    private OnItemClickListener menuListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        menuListener = listener;
    }

    public MenuAdapter(Context context, ArrayList<MenuItem> list) {
        menuContext = context;
        menuList = list;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(menuContext).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem currentItem = menuList.get(position);
        String menuImage = currentItem.getMenuImage();
        String menuName = currentItem.getMenuName();
        Double menuPrice = currentItem.getMenuPrice();
//        String menuDescription = currentItem.getMenuDescription();

        Picasso.get().load("file:///android_asset/" + menuImage).fit().centerInside().into(holder.menuImage);
        holder.menuName.setText(menuName);
        holder.menuPrice.setText(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(menuPrice));
//        holder.menuDescription.setText(menuDescription);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView menuImage;
        public TextView menuName;
        public TextView menuPrice;
//        public TextView menuDescription;

        public MenuViewHolder(View itemView) {
            super(itemView);
            menuImage = itemView.findViewById(R.id.menu_image);
            menuName = itemView.findViewById(R.id.menu_name);
            menuPrice = itemView.findViewById(R.id.menu_price);
//            menuDescription = itemView.findViewById(R.id.menu_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menuListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            menuListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
