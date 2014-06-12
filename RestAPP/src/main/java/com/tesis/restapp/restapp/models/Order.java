package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Collections;
import java.util.List;

public class Order extends SugarRecord<Order>{

    @SerializedName("id")
    private int uai;
    private Table table;
    private String created_at;
    private String updated_at;
    @Ignore
    private List<Item> items = Collections.emptyList();

    public Order(Context context) {
        super(context);
    }


    public Order(Context context, int uai, Table table, String created_at, String updated_at, List<Item> items){
        super(context);
        this.uai =uai;
        this.table = table;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.items = items;
    }


    //GETTERS AND SETTERS

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getUai() {
        return uai;
    }

    public void setUai(int uai) {
        this.uai = uai;
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


}
