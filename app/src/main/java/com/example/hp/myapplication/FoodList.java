package com.example.hp.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.Model.Product;
import com.example.hp.myapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    private List<Product> products;

    private static final String TAG = "FoodList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recyclerView = findViewById(R.id.recycler_food);
        products = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

                                Log.d(TAG, "loadListFood: " + product);
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

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
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
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
