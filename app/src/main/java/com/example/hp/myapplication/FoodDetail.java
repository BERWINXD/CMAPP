package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.Common.ResourceLoader;
import com.example.hp.myapplication.CustomComponents.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        reference = FirebaseDatabase.getInstance().getReference("AddToCart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        numberButton = findViewById(R.id.number_button);
        btncart = findViewById(R.id.btncart);

        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);

        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        food_price.setText(String.valueOf(getIntent().getFloatExtra("FoodPrice", 0)));
        food_name.setText(getIntent().getStringExtra("FoodName"));
        int resourceId = ResourceLoader.getResource(getIntent().getStringExtra("FoodName"));
        if (resourceId != 0)
            food_image.setImageResource(resourceId);

        String ID_Cart = reference.push().getKey();
        Log.d("Firebase", "onCreate: " + ID_Cart);
        btncart.setOnClickListener(view -> {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("Product_Name", food_name.getText().toString());
            parameters.put("Product_Price", food_price.getText().toString());

            // Since ID_Cart is generated at OnCreate, only the first item will be added,
            // Subsequent items will just update it.
            reference.child(ID_Cart).setValue(parameters);
            startActivity(new Intent(FoodDetail.this, ShowCart.class));
        });
    }
}
