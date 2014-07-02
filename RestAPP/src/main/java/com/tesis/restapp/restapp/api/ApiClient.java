package com.tesis.restapp.restapp.api;
import com.tesis.restapp.restapp.models.User;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;


public class ApiClient{

    private static final String API_URL = "http://192.168.0.15:8000";

    private static RestAppApiInterface sRestAppService;

    public static RestAppApiInterface getRestAppApiClient() {
        if (sRestAppService == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade requestFacade) {
                            if (User.getUser()!= null) {
                                requestFacade.addHeader("Cookie", User.getUser().getToken().getValue());
                            }
                        }
                    })
                    .build();

            sRestAppService = restAdapter.create(RestAppApiInterface.class);
        }
        return sRestAppService;
    }

}

