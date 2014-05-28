package com.tesis.restapp.restapp.models;

import java.util.ArrayList;

public class Item {

    public static String TAG_ID = "id";
    public static String TAG_NAME = "name";
    public static String TAG_DESCRIPTION = "description";
    public static String TAG_PRICE = "price";
    public static String TAG_CATEGORY_ID = "category_id";

    public static String TAG_CATEGORY_CAFE = "cafeteria";
    public static String TAG_CATEGORY_MAIN = "cafeteria";
    public static String TAG_CATEGORY_SANDWICH = "cafeteria";
    public static String TAG_CATEGORY_BEER = "cafeteria";
    public static String TAG_CATEGORY_WINE = "cafeteria";
    public static String TAG_CATEGORY_SODA = "cafeteria";
    public static String TAG_CATEGORY_DESSERT = "cafeteria";



    public static ArrayList<Item> items = new ArrayList<Item>();

    private int id;
    private String description;
    private String name;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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





}
