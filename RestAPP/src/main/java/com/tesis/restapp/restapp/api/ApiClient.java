package com.tesis.restapp.restapp.api;
import android.content.Context;

import com.tesis.restapp.restapp.models.User;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


public class ApiClient{

    private static String apiIP = "192.168.1.39";
    private static RestAppApiInterface sRestAppService;
    private static String token;
    public static RestAppApiInterface getRestAppApiClient(final Context context) {
        if (sRestAppService == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("http://" + apiIP + "/RestAPP-api/public/index.php/")
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade requestFacade) {
                            if (token != null) {
                                requestFacade.addHeader("Cookie", token);
                                requestFacade.addHeader("Accept", "application/json");
                            }
                        }
                    })
                    .build();

            sRestAppService = restAdapter.create(RestAppApiInterface.class);
        }
        return sRestAppService;
    }

    public static void setServerIP(String ip) {
        apiIP = ip;
        sRestAppService = null;
    }
    public static String getServerIp(){
        return apiIP;
    }

    public static void setToken(String userToken) {
        token = userToken;
    }
}

