package com.tesis.restapp.restapp.models;


import java.util.ArrayList;
import java.util.List;

public class Table {

    public static String TAG_NUMBER = "number";
    public static String TAG_SEATS = "seats";
    public static String TAG_DESCRIPTION = "description";
    public static String TAG_ID = "id";
    public static String TAG_TAKEN = "taken";


    private static List<Table> tables = new ArrayList<Table>();

    public static void setTables(List<Table> tables) {
        Table.tables = tables;
    }


    public static List<Table> getTables(){

        return tables;

    }



    private int id;
    private String description;
    private int seats;
    private int number;








    private boolean taken;


    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
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



    public Table(int number, int seats, String description){
        this.number = number;
        this.seats = seats;
        this.description = description;
    }

    public Table(int number, int seats){
        this.number = number;
        this.seats = seats;
        this.description = "";
    }


    public Table(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
