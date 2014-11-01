package com.tesis.restapp.restapp.models;

/**
 * Created by feresr on 6/11/14.
 */
public class Order_Item {

    private int id;
    private int order_id;
    private int item_id;
    private int quantity;
    private double price;
    private String updated_at;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }
    public int getItem_id() {
        return item_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public String getCreated_at() {
        return created_at;
    }
}
