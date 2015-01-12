package com.tesis.restapp.restapp.models;

import android.content.Context;

import com.tesis.restapp.restapp.database.DatabaseHandler;

import retrofit.client.Header;

/**
 * Created by feresr on 5/24/14.
 */
public class User {



    public User() {
        user = this;
    }

    public static User getUser(Context context) {
        return user;
    }


    private static User user;
    private String token;
    private int id;
    private String firstname;
    private String lastname;
    private String username;


    public void setToken(String e) {
        token = e;
    }
    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    @Override
    public String toString() {
        return "Hi there, I'm " + this.firstname + " " + this.lastname + ". Mi id is: " + String.valueOf(id) + " Username: " + this.username;
    }
}
