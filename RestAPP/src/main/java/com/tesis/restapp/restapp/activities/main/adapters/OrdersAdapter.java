package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Order;

import java.util.ArrayList;
import java.util.List;


public class OrdersAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orders = new ArrayList<Order>();
    private DatabaseHandler db;

    public OrdersAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        db = new DatabaseHandler(getContext());
        DatabaseHandler.registerAdapter(this);
        db.close();
        aNotifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orders.size();
    }


    //Asyncrounosly fetch orders from db
    public void aNotifyDataSetChanged()
    {
        final OrdersAdapter adapter = this;
        final Handler h = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                db = new DatabaseHandler(getContext());
                orders = db.getAllOrders();
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

    @Override
    public long getItemId(int position) {
        return orders.get(position).getId();
    }

    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView descriptionTextView;
        TextView tableNumberTextView;
        TextView totalPriceTextView;
        TextView itemsTextView;
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_order, null);

        }

        descriptionTextView = (TextView) v.findViewById(R.id.description_txt);
        tableNumberTextView = (TextView) v.findViewById(R.id.table_number_txt);
        totalPriceTextView = (TextView) v.findViewById(R.id.order_total);
        itemsTextView = (TextView) v.findViewById(R.id.items_txt);

        Order order = getItem(position);
        int numberOfItems = order.getNumberOfItems();
        String items = (numberOfItems == 1)? "ITEM" : "ITEMS";

        if (order.getTable().getDescription() != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(order.getTable().getDescription());
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }
        tableNumberTextView.setText(Integer.toString(order.getTable().getNumber()));
        itemsTextView.setText(numberOfItems + " " + items);
        totalPriceTextView.setText("$" + String.valueOf(order.getTotal()));
        return v;
    }


}