package com.example.hp.myapplication.Model;



import androidx.annotation.NonNull;

public class Product {
    private String pId;
    private String pName;
    private String pType;
    private float price;



    public Product() {
    }  // Needed for Firebase

    public Product(String pName, String pType, String pId, float price) {
        this.pName = pName;
        this.pType = pType;
        this.pId = pId;
        this.price = price;
    }

    public Product(String pId, Product p) {
        this.pId = pId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pId='" + pId + '\'' +
                ", pName='" + pName + '\'' +
                ", pType='" + pType + '\'' +
                ", price=" + price +
                '}';
    }
}
