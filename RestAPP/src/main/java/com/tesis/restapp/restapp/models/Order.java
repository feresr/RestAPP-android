package com.tesis.restapp.restapp.models;

import java.util.Collections;
import java.util.List;

public class Order {

    public static List<Order> getOrders() {
        return orders;
    }

    public static void setOrders(List<Order> orders) {
        Order.orders = orders;
    }

    public static Order getOrderById(int id) {

        for (Order order : orders) {

            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public static List<Order> orders = Collections.emptyList();

    private int id;
    private Table table;
    private int user_id;
    private String created_at;
    private String updated_at;
    private List<Item> items;

    public int getId() {
        return id;
    }


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
