package com.example.hp.myapplication;

public class ShowCart_Model {
    private String Product_Name;
    private String Product_Price;

    public ShowCart_Model() {

    }

    public ShowCart_Model(String product_Name, String price) {
        this.Product_Name = product_Name;
        this.Product_Price = price;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_Price() {
        return Product_Price;
    }

    public void setProduct_Price(String price) {
        Product_Price = price;
    }
}
