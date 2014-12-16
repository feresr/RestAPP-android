package com.tesis.restapp.restapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

     public Order() {  };


    private int id;
    private int table_id;
    private double total;
    private int ready;
    private int active;
    private String created_at;
    private String updated_at;
    private Table table;
    private List<Item> items = Collections.emptyList();

    public Order(Parcel in) {
        id = in.readInt();
        table_id = in.readInt();
        total = in.readDouble();
        ready = in.readInt();
        active = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        table = (Table) in.readValue(Table.class.getClassLoader());
        if (in.readByte() == 0x01) {
            items = new ArrayList<Item>();
            in.readList(items, Item.class.getClassLoader());
        } else {
            items = null;
        }
    }

    //GETTERS AND SETTERS
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void removeItem(Item item) {
        items.remove(item);
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
        this.ready = ready ? 1 : 0;
    }

    public boolean isActive() {
        return active == 1;
    }

    public int getTable_id() {
        return table_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(table_id);
        dest.writeDouble(total);
        dest.writeInt(ready);
        dest.writeInt(active);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeValue(table);
        if (items == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }
}
