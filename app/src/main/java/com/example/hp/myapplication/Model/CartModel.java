package com.example.hp.myapplication.Model;

public class CartModel {
    private String productName, key;
    private float productPrice;
    private int productAmount;


    public CartModel() {
        this.productName = "";
        this.productPrice = 0;
        this.productAmount = 0;
    }

    public CartModel(int productAmount, String productName, float productPrice) {
        this.productAmount = productAmount;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float price) {
        productPrice = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
