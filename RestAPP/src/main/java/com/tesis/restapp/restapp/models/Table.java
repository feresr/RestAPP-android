package com.tesis.restapp.restapp.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Table implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    public Table() {  };

    private int id;
    private int seats;
    private String description;
    private int number;
    private int taken;
    private String created_at;

    //GETTERS ANN SETTERS
    private String updated_at;

    protected Table(Parcel in) {
        id = in.readInt();
        seats = in.readInt();
        description = in.readString();
        number = in.readInt();
        taken = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
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
        this.taken = taken ? 1 : 0;
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
        dest.writeInt(seats);
        dest.writeString(description);
        dest.writeInt(number);
        dest.writeInt(taken);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
