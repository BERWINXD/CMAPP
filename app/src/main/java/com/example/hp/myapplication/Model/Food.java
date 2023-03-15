package com.example.hp.myapplication.Model;

public class Food {
    private String Name;
    private final String Image;
    private final String Description;
    private final String Price;

    public Food(String name, String image, String description, String price, String menuId){
        Name = name;
        Image = image;
        Description = description;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

}
