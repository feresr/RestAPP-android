package com.tesis.restapp.restapp.activities.main;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.SearchActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrderActivity extends AppCompatActivity implements OrderFragment.OrderFragmentCallbacks {

    private int id;
    private Order order;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setMessage("Closing order");
        if (getIntent() != null) {
            id = getIntent().getExtras().getInt(Order.class.getName());
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        if (savedInstanceState == null) {

            order = databaseHandler.getOrderById(id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, OrderFragment.newInstance(order))
                    .commit();

        } else {
            if (savedInstanceState.getBoolean(pDialog.getClass().getName(), false)){
                pDialog.show();
            }
            id = savedInstanceState.getInt(Order.class.getSimpleName());
            order = databaseHandler.getOrderById(id);
        }
        databaseHandler.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderCheckout(Order order) {
        CheckoutFragment checkoutFragment = CheckoutFragment.newInstance(order);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, checkoutFragment);
        transaction.commit();
    }

    @Override
    public void onCloseOrder(final Order order) {
        RestAppApiInterface apiInterface= ApiClient.getRestAppApiClient(this);
        pDialog.show();
        final DatabaseHandler db = new DatabaseHandler(this);
        apiInterface.closeOrder(order.getId(), new Callback<com.tesis.restapp.restapp.database.Response>() {
            @Override
            public void success(com.tesis.restapp.restapp.database.Response response, Response response2) {
                if (response.wasSuccessful()) {
                    pDialog.dismiss();
                    order.close();
                    Toast.makeText(getApplicationContext(), response.getMessage() , Toast.LENGTH_SHORT).show();
                    db.removeOrder(order);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onAddItem() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void dismissDialog() {
        pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("itemId", -1);
                if (result >= 0) {
                    pDialog.setMessage("Adding item...");
                    pDialog.show();
                    DatabaseHandler db = new DatabaseHandler(this);
                    Item item = db.getItemById(result);

                    db.addItemToOrder(this, order, item);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //TODO:Write code if there's no result
            }
        }
    }

    @Override
    protected void onStop() {
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(pDialog.getClass().getName(), pDialog.isShowing());
        outState.putInt(Order.class.getSimpleName(), id);
        super.onSaveInstanceState(outState);
    }
}
