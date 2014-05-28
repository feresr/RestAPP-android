package com.tesis.restapp.restapp.api;

import com.tesis.restapp.restapp.Constants;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;
import com.tesis.restapp.restapp.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by feresr on 5/24/14.
 */
public interface RestAppApiInterface {

    @FormUrlEncoded
    @POST(Constants.URL_LOGIN)
    public void logIn(@Field("username") String username, @Field("password") String password, Callback<User> callback);



    @GET(Constants.URL_ORDERS)
    public void retrieveOrders(Callback<List<Order>> callback);

    @GET(Constants.URL_ORDERS + "/{id}")
    public void retrieveOrder(@Path("id") int orderId, Callback<Order> callback);


    @GET(Constants.URL_AVAILABLE_TABLES)
    public void retrieveTables(Callback<List<Table>> callback);

}
