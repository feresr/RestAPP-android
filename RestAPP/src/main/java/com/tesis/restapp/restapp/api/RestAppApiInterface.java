package com.tesis.restapp.restapp.api;

import com.tesis.restapp.restapp.database.CategoryRow;
import com.tesis.restapp.restapp.database.ItemRow;
import com.tesis.restapp.restapp.database.OrderRow;
import com.tesis.restapp.restapp.database.Order_itemRow;
import com.tesis.restapp.restapp.database.TableRow;
import com.tesis.restapp.restapp.models.User;
import com.tesis.restapp.restapp.database.Response;

import java.util.List;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import static com.tesis.restapp.restapp.Constants.*;

public interface RestAppApiInterface {

    @FormUrlEncoded
    @POST(URL_LOGIN)
    public void logIn(@Field("username") String username, @Field("password") String password, Callback<User> callback);

    @FormUrlEncoded
    @POST(URL_ORDER_ITEMS)
    public void addItemToOrder(@Field("order_id") int order, @Field("item_id") int item, @Field("quantity") int quantity, Callback<Response> callback);


    @DELETE(URL_ORDER_ITEMS + "/{id}")
    public void removeItemFromOrder(@Path("id") int id, Callback<Response> callback);

    @GET(URL_ORDERS)
    public void retrieveOrders(Callback<List<OrderRow>> callback);

    @GET(URL_ORDERS + "/{id}")
    public void retrieveOrder(@Path("id") int orderId, Callback<OrderRow> callback);

    @GET(URL_AVAILABLE_TABLES)
    public void retrieveTables(Callback<List<TableRow>> callback);

    @GET(URL_ITEMS)
    public void retrieveItems(Callback<List<ItemRow>> callback);

    @GET(URL_CATEGORIES)
    public void retrieveCategories(Callback<List<CategoryRow>> callback);

    @GET(URL_ORDER_ITEMS)
    public void retrieveOrderItems(Callback<List<Order_itemRow>> callback);

    @FormUrlEncoded
    @POST(URL_ORDERS)
    public void newOrder(@Field("table_id") int tableId, Callback<Response> callback);

    @DELETE(URL_ORDERS + "/{id}")
    public void closeOrder(@Path("id") int id, Callback<OrderRow> callback);

}
