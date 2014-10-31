package com.tesis.restapp.restapp.models;


import android.content.Context;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

public class Table {

    private int id;
    private int seats;
    private String description;
    private int number;
    private int taken;
    private String created_at;
    private String updated_at;

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
        return taken == 1;
    }

    public void setTaken(boolean taken) {
        this.taken = taken? 1 : 0;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
