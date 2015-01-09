package com.tesis.restapp.restapp.api;

import retrofit.RetrofitError;

/**
 * Created by Fer on 1/8/2015.
 */
public class DataBaseSyncCompleteEvent {

    RetrofitError error;
    public DataBaseSyncCompleteEvent(RetrofitError error){
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }


}
