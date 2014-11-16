package com.tesis.restapp.restapp.api.response;

import com.tesis.restapp.restapp.models.User;

/**
 * Created by Fer on 11/15/2014.
 */
public class ApiResponse {
    private Boolean success;
    private String message;
    private User user;

    public Boolean wasSuccessful() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
