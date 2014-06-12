package com.tesis.restapp.restapp.models;


import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class Table extends SugarRecord<Table> {

    @SerializedName("id")
    private int uai;
    private String description;
    private int seats;
    private int number;


    public Table(Context context) {
        super(context);
    }

    public Table(Context context, int uai, String description, int seats, int number) {
        super(context);
        this.uai = uai;
        this.seats = seats;
        this.number = number;
        this.description = description;

    }

    //GETTERS ANN SETTERS

    public int getUai() {
        return uai;
    }

    public void setUai(int uai) {
        this.uai = uai;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
