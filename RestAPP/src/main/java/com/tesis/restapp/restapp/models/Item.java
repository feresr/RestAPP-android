package com.tesis.restapp.restapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    public Item() {  };

    private int id;
    private String name;
    private String description;
    private float price;
    private Category category;
    private int category_id;
    private String created_at;


    //GETTERS AND SETTERS
    private String updated_at;

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readFloat();
        category = (Category) in.readValue(Category.class.getClassLoader());
        category_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeFloat(price);
        dest.writeValue(category);
        dest.writeInt(category_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
