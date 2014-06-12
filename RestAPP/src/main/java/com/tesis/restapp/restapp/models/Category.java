package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Category extends SugarRecord<Category>{

    @SerializedName("id")
    private int uai;
    private String name;

    public Category(Context ctx){
        super(ctx);
    }

    public Category(Context ctx, int uai, String name ){
        super(ctx);
        this.uai = uai;
        this.name = name;
    }

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
