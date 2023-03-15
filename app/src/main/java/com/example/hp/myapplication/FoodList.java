package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.R;
import com.example.hp.myapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");
        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get intent here
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty()) {
            loadListFood(categoryId);
        }
    }

    private void loadListFood(String categoryId) {

        FirebaseRecyclerOptions<Food> options =
                new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(foodList.orderByChild("MenuId").equalTo(categoryId), Food.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item, parent, false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.food_name.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(holder.food_image);

                final Food local = model;
                holder.setItemClickListener((view, position1, isLongClick) -> {
                    Toast.makeText(FoodList.this, "" + local.getName(), Toast.LENGTH_SHORT).show();

                    Intent foodDetails = new Intent(FoodList.this, FoodDetail.class);
                    foodDetails.putExtra("FoodId", adapter.getRef(position1).getKey());
                    startActivity(foodDetails);

                });
            }
        };
        Log.d("TAG", "" + adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
