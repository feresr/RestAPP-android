package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.User;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class OrdersAdapter extends ArrayAdapter<Order> {

    private Context context;

    public OrdersAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;

        RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient();
        apiInterface.retrieveOrders(new Callback<List<Order>>() {
            @Override
            public void success(List<Order> orders, Response response) {
                onOrdersFetched(orders);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("server not found?", retrofitError.toString());
            }
        });

    }

    @Override
    public int getCount() {
        return Order.getOrders().size();
    }

    @Override
    public long getItemId(int position) {
        return Order.getOrders().get(position).getId();
    }

    @Override
    public Order getItem(int position) {
        return Order.getOrders().get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView description;
        TextView tableNumber;
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_order, null);

        }

        description = (TextView) v
                .findViewById(R.id.description_txt);
        tableNumber = (TextView) v.findViewById(R.id.table_number_txt);

        Order order = Order.getOrders().get(position);

        description.setText(order.getTable().getDescription());
        tableNumber.setText(Integer.toString(order.getTable().getNumber()));

        return v;
    }

    public void onOrdersFetched(List<Order> orders) {
        Order.setOrders(orders);
        notifyDataSetChanged();

    }
}