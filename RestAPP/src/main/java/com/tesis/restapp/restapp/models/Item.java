package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;

public class Item extends SugarRecord<Item> {

    @SerializedName("id")
    private int uai;
    private String description;
    private String name;
    private double price;
    private Category category;


    public Item(Context ctx) {
        super(ctx);
    }

    public Item(Context ctx, int uai, String description, String name, double price, Category category){
        super(ctx);
        this.uai = uai;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }


    //GETTERS AND SETTERS

    public int getUai() {
        return uai;
    }

    public void setUai(int id) {
        this.uai = id;
    }

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


}
