package com.example.hp.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hp.myapplication.Common.ResourceLoader;
import com.example.hp.myapplication.CustomComponents.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

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
    }
}
