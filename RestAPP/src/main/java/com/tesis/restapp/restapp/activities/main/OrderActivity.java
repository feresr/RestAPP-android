package com.tesis.restapp.restapp.activities.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.SearchActivity;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;

public class OrderActivity extends FragmentActivity implements OrderFragment.OrderFragmentCallbacks {

    private int id;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        if (getIntent() != null) {
            id = getIntent().getExtras().getInt(Order.class.getName());
        }

        if (savedInstanceState == null) {

            DatabaseHandler databaseHandler = new DatabaseHandler(this);
            order = databaseHandler.getOrderById(id);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, OrderFragment.newInstance(order))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
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
    public void onCloseOrder(Order order) {
        CheckoutFragment checkoutFragment = CheckoutFragment.newInstance(order);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.addToBackStack(null);
        transaction.replace(R.id.container, checkoutFragment);
        transaction.commit();
    }

    @Override
    public void onAddItem() {
        Intent i = new Intent(this, SearchActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("itemId", -1);
                if (result >= 0) {

                    DatabaseHandler db = new DatabaseHandler(this);
                    Item item = db.getItemById(result);

                    db.addItemToOrder(this, order, item);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //TODO:Write your code if there's no result
            }
        }
    }
}
