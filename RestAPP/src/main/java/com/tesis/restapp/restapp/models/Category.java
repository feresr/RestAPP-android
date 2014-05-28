package com.tesis.restapp.restapp.models;

import java.util.ArrayList;

/**
 * Created by feresr on 5/17/14.
 */
public class Category {

    private static ArrayList<Category> categories = new ArrayList<Category>();

    private int id;
    private String name;

    public static String TAG_ID = "id";
    public static String TAG_NAME = "name";


    public static ArrayList<Category> getCategories(){

        return categories;

    }


    public static void addCategory(Category category){

        categories.add(category);

    }

    public static void clearCategories(){

        categories.clear();

    }


    public Category(){


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
