package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;


public class Item{

    private int id;
    private String description;
    private String name;
    private double price;
    private Category category;





    //GETTERS AND SETTERS

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
