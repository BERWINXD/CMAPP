package com.example.hp.myapplication.Model;

public class Category {

    private String name;



    private String image;
    public Category(){

    }

    public Category(String name,String image){
        this.name = name;
        this.image = image;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getImage(){
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
