package com.tesis.restapp.restapp.models;


import android.content.Context;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

public class Table {

    private int id;
    private String description;
    private int seats;
    private int number;
    private boolean taken;


    //GETTERS ANN SETTERS



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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
