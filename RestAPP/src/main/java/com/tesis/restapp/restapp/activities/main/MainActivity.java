package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.SearchActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.CategoryRow;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.database.ItemRow;
import com.tesis.restapp.restapp.database.OrderRow;
import com.tesis.restapp.restapp.database.Order_itemRow;
import com.tesis.restapp.restapp.database.TableRow;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements MainHandler {
    private ProgressDialog pDialog;
    private RestAppApiInterface apiInterface;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);


        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();


            syncDb();


        }

    }

    private void syncDb() {

        pDialog.setMessage("Actualizando BD....");
        pDialog.show();

        apiInterface = ApiClient.getRestAppApiClient();

        final DatabaseHandler db = new DatabaseHandler(this);
        apiInterface.retrieveCategories(new Callback<List<CategoryRow>>() {
            @Override
            public void success(List<CategoryRow> categoryRows, Response response) {
                if (categoryRows != null) {
                    db.addCategories(categoryRows);
                }
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });

        apiInterface.retrieveItems(new Callback<List<ItemRow>>() {
            @Override
            public void success(List<ItemRow> itemRows, Response response) {
                if (itemRows != null) {
                    db.addItems(itemRows);
                }
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });

        apiInterface.retrieveTables(new Callback<List<TableRow>>() {
            @Override
            public void success(List<TableRow> tableRows, Response response) {
                if (tableRows != null) {
                    db.addTables(tableRows);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        apiInterface.retrieveOrders(new Callback<List<OrderRow>>() {
            @Override
            public void success(List<OrderRow> orderRows, Response response) {
                if (orderRows != null) {
                    db.addOrders(orderRows);

                }
                pDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        apiInterface.retrieveOrderItems(new Callback<List<Order_itemRow>>() {
            @Override
            public void success(List<Order_itemRow> order_itemRows, Response response) {
                if (order_itemRows != null) {
                    db.addOrderItems(order_itemRows);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderSelected(int orderId) {
        OrderFragment orderFragment = new OrderFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        DatabaseHandler db = new DatabaseHandler(this);
        selectedOrder =  db.getOrderById(orderId);

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, orderFragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("itemId", -1);
                if(result>=0){

                    DatabaseHandler db = new DatabaseHandler(this);
                    Item item =  db.getItemById(result);
                    db.addItemToOrder(this, selectedOrder, item);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onNewOrderSelected() {
        TablesFragment tablesFragment = new TablesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, tablesFragment);
        transaction.commit();
    }

    public void onTableOccupied() {
        Toast.makeText(this, "Alguien ya ocupó esta mesa", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTableSelected(int tableId) {

        pDialog.setMessage("Creando orden...");
        pDialog.show();
/*
        apiInterface.newOrder(tableId, new Callback<Order>() {
            @Override
            public void success(Order order, Response response) {

                pDialog.dismiss();

                if(order != null){
                    //Order.addOrder(order);
                    //onOrderSelected(order.getId());

                }else{
                    pDialog.dismiss();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();
            }
        });*/

    }

    @Override
    public void onAddItemOptionSelected() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public Order getSelectedOrder() {
        return  selectedOrder;
    }

    @Override
    protected void onPause() {
        if (pDialog != null)
            pDialog.dismiss();


        super.onPause();
    }
}
