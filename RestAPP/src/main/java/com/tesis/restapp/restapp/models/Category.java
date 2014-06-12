package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;


public class Category{

    @SerializedName("id")
    private int uai;
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUai() {
        return uai;
    }

    public void setUai(int uai) {
        this.uai = uai;
    }
}
