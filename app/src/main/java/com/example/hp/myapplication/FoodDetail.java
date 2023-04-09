package com.example.hp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.Common.ResourceLoader;
import com.example.hp.myapplication.CustomComponents.ElegantNumberButton;
import com.example.hp.myapplication.Model.CartModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class FoodDetail extends AppCompatActivity {

    private TextView food_name, food_price;
    private ElegantNumberButton itemCounter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        databaseReference = FirebaseDatabase.getInstance().getReference("AddToCart").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
        itemCounter = findViewById(R.id.number_button);
        FloatingActionButton cartButton = findViewById(R.id.btncart);

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);

        ImageView food_image = findViewById(R.id.img_food);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        food_price.setText(String.valueOf(getIntent().getFloatExtra("FoodPrice", 0)));
        food_name.setText(getIntent().getStringExtra("FoodName"));
        int resourceId = ResourceLoader.getResource(getIntent().getStringExtra("FoodName"));
        if (resourceId != 0)
            food_image.setImageResource(resourceId);

        cartButton.setOnClickListener(view -> {
            String ID_Cart = databaseReference.push().getKey();
            if (ID_Cart != null)
                databaseReference.child(ID_Cart).setValue(new CartModel(Integer.parseInt(itemCounter.getNumber()), food_name.getText().toString(), Float.parseFloat(food_price.getText().toString())));
            startActivity(new Intent(FoodDetail.this, Cart.class));
        });
    }
}
