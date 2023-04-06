package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Common.ResourceLoader;
import com.example.hp.myapplication.Model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutmanager;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Products");

        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        products = new ArrayList<>();
        recyclerView.setLayoutManager(layoutmanager);

        String categoryId = getIntent().getStringExtra("item");

        loadListFood(categoryId);
    }

    private void loadListFood(String categoryId) {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Products")
                .orderByChild("Product-Type")
                .equalTo(categoryId)
                .get()
                .addOnCompleteListener(snapshot -> {
                    if (!snapshot.isSuccessful()) {
                        Log.e("firebase", "Error getting data", snapshot.getException());
                    } else {
                        Map<String, Object> message = (Map<String, Object>) snapshot.getResult().getValue();
                        if (message != null) {
                            for (Object o : message.values()) {
                                String s = String.valueOf(o);
                                String[] params = s.substring(1, s.length() - 1).split(",");
                                Product product = new Product(
                                        params[2].trim().split("=")[1],
                                        params[3].trim().split("=")[1],
                                        params[0].trim().split("=")[1],
                                        Float.parseFloat(params[1].split("=")[1]));
                                products.add(product);
                            }
                            recyclerView.setAdapter(new CustomAdapter(products));
                        }
                    }
                });
    }

}


class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final List<Product> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;
        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.text);
            imageView = view.findViewById(R.id.image);
            cardView = view.findViewById(R.id.row_item_wrapper);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }

    public CustomAdapter(List<Product> localDataSet) {
        this.localDataSet = localDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet.get(position).getpName());
        int resource = ResourceLoader.getResource(localDataSet.get(position).getpName());
        if (resource != 0)
            viewHolder.getImageView().setImageResource(resource);
        viewHolder.getCardView().setOnClickListener(listener -> {
            Intent intent = new Intent(listener.getContext(), FoodDetail.class);
            intent.putExtra("FoodId", localDataSet.get(position).getpId());
            intent.putExtra("FoodName", localDataSet.get(position).getpName());
            intent.putExtra("FoodPrice", localDataSet.get(position).getPrice());
            listener.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
