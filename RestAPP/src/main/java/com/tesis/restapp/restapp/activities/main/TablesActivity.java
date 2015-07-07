package com.tesis.restapp.restapp.activities.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.database.Response;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;

import retrofit.Callback;
import retrofit.RetrofitError;

public class TablesActivity extends ActionBarActivity implements TablesFragment.TablesCallback{
    private ProgressDialog pDialog;
    private static final String KEY_DIALOG_SHOWING = "DIALOG_SHOWING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        if (savedInstanceState != null) {
            //If the progress dialog was showing before configuration changes. it should keep
            //showing now, right?.
            if (savedInstanceState.getBoolean(KEY_DIALOG_SHOWING)) {
                pDialog.setMessage("Creando orden");
                pDialog.show();
            }
        }

    }

    @Override
    public void onTableSelected(final Table table) {
        pDialog.setMessage("Creando orden...");
        pDialog.show();
        final DatabaseHandler db = new DatabaseHandler(this);

        RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient(this);
        apiInterface.newOrder(table.getId(), new Callback<Response>() {
            @Override
            public void success(com.tesis.restapp.restapp.database.Response apiResponse, retrofit.client.Response response) {
                if (apiResponse.wasSuccessful()) {
                    Order order = new Order();
                    order.setTable(table);
                    order.setId(apiResponse.getId());
                    db.addOrder(order);
                    onOrderSelected(order);

                } else {
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_DIALOG_SHOWING, pDialog.isShowing());
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onStop();
    }

    public void onTableOccupied() {
        Toast.makeText(this, "Alguien ya ocupó esta mesa", Toast.LENGTH_LONG).show();
    }

    public void onOrderSelected(Order order) {
        Intent i = new Intent(this, OrderActivity.class);
        i.putExtra(Order.class.getName(), order.getId());
        this.finish();
        startActivity(i);
    }
}
