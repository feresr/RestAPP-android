package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.tesis.restapp.restapp.database.DatabaseHandler;

import java.util.Collections;
import java.util.List;

public class Order {

    private int id;
    private int table_id;
    private double total;
    private int ready;
    private int active;
    private String created_at;
    private String updated_at;

    private Table table;
    private List<Item> items = Collections.emptyList();

    //GETTERS AND SETTERS
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotal(){
        double total = 0;
        for(Item item : items){
            total += item.getPrice();
        }
        return total;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReady() {
        return ready == 1;
    }

    public void setReady(boolean ready) {
        this.ready = ready? 1 : 0;
    }

    public boolean isActive() {
        return active == 1;
    }

    public int getTable_id() {
        return table_id;
    }
}
