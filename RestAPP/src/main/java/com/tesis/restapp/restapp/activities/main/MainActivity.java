package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.SearchActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Order_Item;
import com.tesis.restapp.restapp.models.Category;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements MainHandler {
    private ProgressDialog pDialog;
    private RestAppApiInterface apiInterface;
    private int orderId;

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

            new SyncDB().execute(this);

        }else{

            orderId = savedInstanceState.getInt("selectedOrder");

        }

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
    public void onOrderSelected(int orderId, boolean newOrder) {
        OrderFragment orderFragment = new OrderFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        this.orderId =  orderId;
        if(newOrder) {
            getFragmentManager().popBackStack();
        }
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

                    db.addItemToOrder(this, this.getSelectedOrder(), item);
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

        transaction.addToBackStack(null)
                .replace(R.id.container, tablesFragment)
                .commit();
    }

    public void onTableOccupied() {
        Toast.makeText(this, "Alguien ya ocupó esta mesa", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTableSelected(final Table table) {

        pDialog.setMessage("Creando orden...");
        pDialog.show();
        final DatabaseHandler db = new DatabaseHandler(this);

        apiInterface = ApiClient.getRestAppApiClient(this);
        apiInterface.newOrder(table.getId(), new Callback<com.tesis.restapp.restapp.database.Response>() {
            @Override
            public void success(com.tesis.restapp.restapp.database.Response apiResponse, Response response) {
                if(apiResponse.wasSuccessful()) {
                    Order order = new Order();
                    order.setTable(table);
                    order.setId(apiResponse.getId());
                    db.addOrder(order);
                    onOrderSelected(order.getId(), true);
                }else{
                    onTableOccupied();
                }
                pDialog.dismiss();
                db.close();
            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();
                //SERVER ERROR

                db.close();
            }
        });

    }

    @Override
    public void onAddItemOptionSelected() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public Order getSelectedOrder() {

        DatabaseHandler db = new DatabaseHandler(this);
        return db.getOrderById(orderId);

    }

    @Override
    public void onCloseOrderSelected() {
        CheckoutFragment checkoutFragment = new CheckoutFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, checkoutFragment);
        transaction.commit();
    }

    @Override
    public void onCloseOrder() {
       final DatabaseHandler db = new DatabaseHandler(this);
       apiInterface = ApiClient.getRestAppApiClient(this);
       apiInterface.closeOrder(orderId,new Callback<Order>() {
           @Override
           public void success(Order order, Response response) {
               db.removeOrder(order);
               getFragmentManager().beginTransaction()
                       .replace(R.id.container, new DashboardFragment())
                       .commit();
               db.close();
           }

           @Override
           public void failure(RetrofitError error) {
               Log.d(this.getClass().getSimpleName(), error.toString());
               db.close();
           }
       });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedOrder", orderId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        if (pDialog != null)
            pDialog.dismiss();

        final DatabaseHandler db = new DatabaseHandler(this);
        db.close();
        super.onPause();
    }

    private class SyncDB extends AsyncTask<Context, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Actualizando BD....");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Context... params) {


                apiInterface = ApiClient.getRestAppApiClient(getApplicationContext());

                final DatabaseHandler db = new DatabaseHandler(params[0]);

                apiInterface.retrieveCategories(new Callback<List<Category>>() {
                    @Override
                    public void success(List<Category> categories, Response response) {
                        if (categories != null) {
                            db.addCategories(categories);

                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                apiInterface.retrieveItems(new Callback<List<Item>>() {
                    @Override
                    public void success(List<Item> items, Response response) {
                        if (items != null) {
                            db.addItems(items);
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                apiInterface.retrieveTables(new Callback<List<Table>>() {
                    @Override
                    public void success(List<Table> tables, Response response) {
                        if (tables != null) {
                            db.addTables(tables);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("retrofit_error",error.getMessage());
                    }
                });

                apiInterface.retrieveOrderItems(new Callback<List<Order_Item>>() {
                    @Override
                    public void success(List<Order_Item> order_itemRows, Response response) {
                        if (order_itemRows != null) {
                            db.addOrderItems(order_itemRows);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}


