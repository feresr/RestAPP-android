package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.intro.LogInFragment;
import com.tesis.restapp.restapp.models.Order;

public class MainActivity extends Activity implements MainHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            DashboardFragment dashboardFragment = new DashboardFragment();
            dashboardFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.container, dashboardFragment)
                    .commit();
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
    public void onOrderSelected(int orderId) {
        OrderFragment orderFragment = new OrderFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putInt("order_id", orderId);
        orderFragment.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, orderFragment);
        transaction.commit();
    }

    @Override
    public void onNewOrderSelected() {
        TablesFragment tablesFragment = new TablesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, tablesFragment);
        transaction.commit();
    }
}
