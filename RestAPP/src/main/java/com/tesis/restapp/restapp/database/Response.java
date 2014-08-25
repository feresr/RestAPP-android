package com.tesis.restapp.restapp.database;

/**
 * Created by feresr on 8/9/14.
 */
public class Response {

    private final boolean success;
    private final String message;
    private final int id;
    private final String errors;

    public Response(boolean success, String message, int id, String errors) {

        this.success = success;
        this.message = message;
        this.id = id;
        this.errors = errors;
    }

    public Response(boolean success, String message, int id) {

        this.success = success;
        this.message = message;
        this.id = id;
        errors = "";
    }


    public boolean wasSuccessful() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public String getErrors() {
        return errors;
    }
}
