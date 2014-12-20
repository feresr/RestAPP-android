package com.tesis.restapp.restapp.api;

import com.tesis.restapp.restapp.api.response.ApiResponse;
import com.tesis.restapp.restapp.database.Response;
import com.tesis.restapp.restapp.models.Category;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Order_Item;
import com.tesis.restapp.restapp.models.Table;

import java.util.List;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import static com.tesis.restapp.restapp.Constants.URL_AVAILABLE_TABLES;
import static com.tesis.restapp.restapp.Constants.URL_CATEGORIES;
import static com.tesis.restapp.restapp.Constants.URL_ITEMS;
import static com.tesis.restapp.restapp.Constants.URL_LOGIN;
import static com.tesis.restapp.restapp.Constants.URL_ORDERS;
import static com.tesis.restapp.restapp.Constants.URL_ORDER_ITEMS;
import static com.tesis.restapp.restapp.Constants.URL_LOGOUT;

public interface RestAppApiInterface {

    @FormUrlEncoded
    @POST(URL_LOGIN)
    public void logIn(@Field("username") String username, @Field("password") String password, Callback<ApiResponse> callback);

    @FormUrlEncoded
    @POST(URL_ORDER_ITEMS)
    public void addItemToOrder(@Field("order_id") int order, @Field("item_id") int item, @Field("quantity") int quantity, Callback<Response> callback);


    @DELETE(URL_ORDER_ITEMS + "/{id}")
    public void removeItemFromOrder(@Path("id") int id, Callback<Response> callback);

    @GET(URL_ORDERS)
    public List<Order> retrieveOrders();

    @GET(URL_AVAILABLE_TABLES)
    public List<Table> retrieveTables();

    @GET(URL_AVAILABLE_TABLES)
    public void retrieveTablesAsync(Callback<List<Table>> callback);

    @GET(URL_ITEMS)
    public List<Item> retrieveItems();

    @GET(URL_CATEGORIES)
    public List<Category> retrieveCategories();

    @GET(URL_ORDER_ITEMS)
    public List<Order_Item> retrieveOrderItems();

    @FormUrlEncoded
    @POST(URL_ORDERS)
    public void newOrder(@Field("table_id") int tableId, Callback<Response> callback);

    @DELETE(URL_ORDERS + "/{id}")
    public void closeOrder(@Path("id") int id, Callback<Order> callback);

    @GET(URL_LOGOUT)
    public void logout(Callback<Response> callback);
}
