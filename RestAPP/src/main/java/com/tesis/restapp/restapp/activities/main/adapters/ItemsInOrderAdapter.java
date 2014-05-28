package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by feresr on 5/28/14.
 */
public class ItemsInOrderAdapter extends ArrayAdapter<Item> {

    private Context context;
    private Order order;

    public ItemsInOrderAdapter(Context context, int resource, Order order) {
        super(context, resource);
        this.context = context;
        this.order = order;
        RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient();
        apiInterface.retrieveOrder(order.getId(), new Callback<Order>() {

            @Override
            public void success(Order order, Response response) {
                if (order != null) {
                    onOrderFetched(order);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    public int getCount() {
        return order.getItems().size();
    }

    @Override
    public long getItemId(int position) {
        return order.getItems().get(position).getId();
    }

    @Override
    public Item getItem(int position) {
        return order.getItems().get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView description;
        TextView price;
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_order_item, null);

        }

        description = (TextView) v
                .findViewById(R.id.item_description_txt);
        price = (TextView) v.findViewById(R.id.item_price_txt);

        Item item = getItem(position);
        if(item != null) {
            description.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
        }
        return v;
    }


    private void onOrderFetched(Order order){

        this.order = order;
        notifyDataSetChanged();

    }

}
