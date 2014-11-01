package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;


/**
 * Created by feresr on 5/28/14.
 */
public class ItemsInOrderAdapter extends ArrayAdapter<Item> {

    private Context context;
    private Order order;
    private int orderId;
    DatabaseHandler db;
    public ItemsInOrderAdapter(Context context, int resource, int orderId) {
        super(context, resource);
        this.context = context;
        this.orderId = orderId;

        db = new DatabaseHandler(getContext());

        this.order = db.getOrderById(orderId);
        DatabaseHandler.registerAdapter(this);
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        TextView description;
        TextView price;
        View v = convertView;
        ImageButton removeItemBtn;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview_order_item, null);

        }

        description = (TextView) v
                .findViewById(R.id.item_description_txt);
        price = (TextView) v.findViewById(R.id.item_price_txt);

        final Item item = getItem(position);
        if(item != null) {
            description.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
        }


        removeItemBtn = (ImageButton) v.findViewById(R.id.item_remove_btn);
        removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);

                db.removeItemFromOrder(context, order, getItem(position));

            }
        });





        return v;
    }


    @Override
    public void notifyDataSetChanged() {
        this.order = db.getOrderById(orderId);
        super.notifyDataSetChanged();
    }

}
