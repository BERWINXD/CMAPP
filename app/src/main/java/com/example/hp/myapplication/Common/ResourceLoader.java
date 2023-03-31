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
        else if(Objects.equals(resourceName, "Veg Samosas"))
            return R.drawable.vegsamosas;
        else if(Objects.equals(resourceName, "VEG Sandwich"))
            return R.drawable.vegsandwish;
        else if(Objects.equals(resourceName, "Chutney Cheese Sandwich"))
            return R.drawable.chutneycheesesandwich;
        else if(Objects.equals(resourceName, "VEG Grilled Sandwich"))
            return R.drawable.veggrilledsandwich;
        else if(Objects.equals(resourceName, "VEG Burger"))
            return R.drawable.vegburger;
        else if(Objects.equals(resourceName, "Bhaji Bread"))
            return R.drawable.bhajibread;
        else if(Objects.equals(resourceName, "Bhaji Paratha"))
            return R.drawable.bhajiparatha;
        else if(Objects.equals(resourceName, "VEG Noodles"))
            return R.drawable.vegnoodles;
        else if(Objects.equals(resourceName, "VEG Frriedd Rice"))
            return R.drawable.vegfriedrice;
        else if(Objects.equals(resourceName, "Tea"))
            return R.drawable.tea;
        else if(Objects.equals(resourceName, "Lime Soda"))
            return R.drawable.limesoda;
        else if(Objects.equals(resourceName, "Pineapple Juice"))
            return R.drawable.pineapplejuice;
        else if(Objects.equals(resourceName, "Falooda"))
            return R.drawable.falooda;
        else if(Objects.equals(resourceName, "Water Bottle"))
            return R.drawable.waterbottle;
        else if(Objects.equals(resourceName, "Tropicana Juice"))
            return R.drawable.tropicana;
        else if(Objects.equals(resourceName, "Chickoo Juice"))
            return R.drawable.chickoojuice;
        else if(Objects.equals(resourceName, "Watermelon Juice"))
            return R.drawable.watermelonjuice;
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
