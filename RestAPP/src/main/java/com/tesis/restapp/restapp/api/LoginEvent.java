package com.tesis.restapp.restapp.api;


public class LoginEvent {

    public static final int SUCCESS = 1;
    public static final int MISSING_TOKEN = 2;
    public static final int INVALID_CREDENTIALS = 3;
    public static final int HTTP_ERRORS = 4;
    public static final int SERVER_NOT_FOUND = 5;

    private int result;


    public LoginEvent(int result){
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
