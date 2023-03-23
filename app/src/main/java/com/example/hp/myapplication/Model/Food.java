package com.example.hp.myapplication.Model;

public class Food {
    private String name;
    private final String image;
    private final String description;
    private final String price;
    private final String menuId;

    public Food(String name, String image, String description, String price, String menuId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getMenuId() {
        return menuId;
    }
}
