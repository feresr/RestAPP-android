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

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_order, null);

            TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description_txt);
            TextView tableNumberTextView = (TextView) convertView.findViewById(R.id.table_number_txt);
            TextView totalPriceTextView = (TextView) convertView.findViewById(R.id.order_total);
            TextView itemsTextView = (TextView) convertView.findViewById(R.id.items_txt);

            viewHolder = new ViewHolder(descriptionTextView, tableNumberTextView,totalPriceTextView,itemsTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        Order order = getItem(position);
        int numberOfItems = order.getNumberOfItems();
        String items = (numberOfItems == 1)? "ITEM" : "ITEMS";

        if (order.getTable().getDescription() != null) {
            viewHolder.descriptionTextView.setVisibility(View.VISIBLE);
            viewHolder.descriptionTextView.setText(order.getTable().getDescription());
        } else {
            viewHolder.descriptionTextView.setVisibility(View.GONE);
        }
        viewHolder.tableNumberTextView.setText(Integer.toString(order.getTable().getNumber()));
        viewHolder.itemsTextView.setText(numberOfItems + " " + items);
        viewHolder.totalPriceTextView.setText("$" + String.valueOf(order.getTotal()));
        return convertView;
    }

    private static class ViewHolder {
        public TextView descriptionTextView;
        public TextView tableNumberTextView;
        public TextView totalPriceTextView;
        public TextView itemsTextView;

        public ViewHolder(TextView descriptionTextView, TextView tableNumberTextView, TextView totalPriceTextView, TextView itemsTextView) {
            this.descriptionTextView = descriptionTextView;
            this.tableNumberTextView = tableNumberTextView;
            this.totalPriceTextView = totalPriceTextView;
            this.itemsTextView = itemsTextView;
        }
    }
}