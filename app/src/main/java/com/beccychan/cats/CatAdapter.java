package com.beccychan.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> implements Filterable {
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
        catListFull = new ArrayList<>(catList);
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(catContext).inflate(R.layout.cat_item, parent, false);
        return new CatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        CatBreed currentCat = catList.get(position);
        //String catImage = currentCat.get(); //need to get from linking API
        String catName = currentCat.getName();
//        String catDescription = currentCat.getCatDescription();

        //will need to call API to load image
       // Picasso.get().load("file:///android_asset/" + catImage).fit().centerInside().into(holder.catImage);
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
            //catImage = itemView.findViewById(R.id.menu_image); //need to get from cat_item
            catName = itemView.findViewById(R.id.cat_name);
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

    @Override
    public Filter getFilter(){
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter(){
        //performFiltering will automatically be running in the background thread and
        //will not freeze the app when running
        //the constraint variable we pass through will be the user search input
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            List<CatBreed> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(catListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                //iterate through all our items to see which item matches search
                //(getText1 is the first line)
                for(CatBreed cat : catListFull){
                    if (cat.getSearchText().toLowerCase().contains(filterPattern)){ //or can use startsWith(filterPattern)
                        filteredList.add(cat);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            catList.clear(); //remove any items so that we can put items from filteredList
            catList.addAll((List) results.values);
            notifyDataSetChanged(); //notify refresh

        }
    };
}
