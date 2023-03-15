package com.example.hp.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hp.myapplication.CustomComponents.ElegantNumberButton;
import com.example.hp.myapplication.Database.Database;
import com.example.hp.myapplication.Model.Food;
import com.example.hp.myapplication.Model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase services initialize
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //views linking
        numberButton = findViewById(R.id.number_button1);
        btncart = findViewById(R.id.btncart);

        btncart.setOnClickListener(v -> {
            try (Database database1 = new Database(getBaseContext())) {
                database1.addToCart(new Order(foodId,
                        currentFood.getName(),
                        numberButton.getCounter(),
                        currentFood.getPrice()));
                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(FoodDetail.this, "Operation Failed", Toast.LENGTH_SHORT).show();
            }
        });

        food_description = findViewById(R.id.food_description1);
        food_name = findViewById(R.id.food_name1);
        food_price = findViewById(R.id.food_price1);

        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                //set Image
                Glide.with(getBaseContext()).load(currentFood.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
