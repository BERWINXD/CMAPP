package com.example.hp.myapplication.Common;

import com.example.hp.myapplication.R;

import java.util.Objects;

public class ResourceLoader {

    public static int getResource(String resourceName) {
        if (Objects.equals(resourceName, "Ros Omelt"))
            return R.drawable.rosomlette;
        else if (Objects.equals(resourceName, "Chicken Noodles"))
            return R.drawable.chickennoodles;
        else if (Objects.equals(resourceName, "NON-VEG Fried Rice"))
            return R.drawable.nonvegfriedrice;
        else if (Objects.equals(resourceName, "Chicken Sandwich"))
            return R.drawable.chicken_sandwich;
        else if (Objects.equals(resourceName, "Beef Burger"))
            return R.drawable.beefburger;
        else if (Objects.equals(resourceName, "Chicken Burger"))
            return R.drawable.chickenburger;
        else if (Objects.equals(resourceName, "VEG Fried Rice"))
            return R.drawable.nonvegfriedrice;
        return 0;

        // No Resources Available for:
        // 1) VEG Sandwich
        // 2) Veg Samosas
        // 3) VEG Burger
        // 4) Bhaji Bread
        // 5) Bhaji Paratha
        // 6) VEG Noodles
        // 7) Chutney Cheese Sandwich
        // 8) VEG Grilled Sandwich
        // 9) Tea
        // 10) Lime Soda
        // 11) Pineapple Juice
        // 12) Falooda
        // 13) Water Bottle
        // 14) Tropicana Juice
        // 15) Chickoo Juice
        // 16) Watermelon Juice
    }
}
