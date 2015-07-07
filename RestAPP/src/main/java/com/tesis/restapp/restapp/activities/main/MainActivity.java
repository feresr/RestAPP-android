package com.tesis.restapp.restapp.activities.main;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.intro.IntroActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.BusProvider;
import com.tesis.restapp.restapp.api.DataBaseSyncCompleteEvent;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Order;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MainHandler {

    private static final String KEY_DIALOG_SHOWING = "DIALOG_SHOWING";

    private ProgressDialog pDialog;
    private TablesFragment tablesFragment;
    private RestAppApiInterface apiInterface;
    private DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        tablesFragment = (TablesFragment) getSupportFragmentManager().findFragmentByTag(TablesFragment.class.getName());
        if (tablesFragment == null) {
            tablesFragment = new TablesFragment();
        }

        if (savedInstanceState == null) {
            dashboardFragment = new DashboardFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();
            new SyncDB(this).execute();

        } else {
            //If the progress dialog was showing before configuration changes. it should keep
            //showing now, right?.
            if (savedInstanceState.getBoolean(KEY_DIALOG_SHOWING)) {
                pDialog.setMessage("Actualizando BD....");
                pDialog.show();
            }
        }
    }

    @Override
    public void onOrderSelected(Order order) {
        Intent i = new Intent(this, OrderActivity.class);
        i.putExtra(Order.class.getName(), order.getId());
        startActivity(i);
    }

    @Override
    public void onNewOrderSelected() {
        Intent i = new Intent(this, TablesActivity.class);
        startActivity(i);
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
    protected void onPause() {

        final DatabaseHandler db = new DatabaseHandler(this);
        db.close();
        BusProvider.getInstance().unregister(this);
        super.onPause();
    }

    private class SyncDB extends AsyncTask<Void, Void, RetrofitError> {

        Context context;

        public SyncDB(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Actualizando BD....");
            pDialog.show();


        }

        @Override
        protected RetrofitError doInBackground(Void... params) {


            apiInterface = ApiClient.getRestAppApiClient(getApplicationContext());
            final DatabaseHandler db = new DatabaseHandler(context);
            try {
                db.addCategories(apiInterface.retrieveCategories());
                db.addItems(apiInterface.retrieveItems());
                db.addTables(apiInterface.retrieveTables());
                db.addOrderItems(apiInterface.retrieveOrderItems());
                db.addOrders(apiInterface.retrieveOrders());
            } catch (RetrofitError e) {
                return e;
            }
            return null;
        }


        @Override
        protected void onPostExecute(RetrofitError error) {

            dashboardFragment.updateAdapter();
            BusProvider.getInstance().post(new DataBaseSyncCompleteEvent(error));

            super.onPostExecute(error);
        }
    }

    @Subscribe
    public void onDBSyncComplete(DataBaseSyncCompleteEvent event) {
        pDialog.dismiss();

        if (event.getError() != null) {
            Log.d("SAD", event.getError().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Error");
            builder.setMessage("There was a server error");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                pDialog.setMessage("Loggin out...");
                pDialog.show();
                apiInterface.logout(new Callback<com.tesis.restapp.restapp.database.Response>() {
                    @Override
                    public void success(com.tesis.restapp.restapp.database.Response response, Response response2) {
                        Toast.makeText(getApplicationContext(), "Estás afuera :)", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                        startActivity(intent);
                        // This makes savedInstance null again, so the db sync happens again on
                        // the subsequent logins.
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //TODO: handle log out errors
                    }
                });
                return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

    }

}


