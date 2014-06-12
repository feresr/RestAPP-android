package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;


public class Category{


    private int id;
    private String name;



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
