package com.tesis.restapp.restapp.activities.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.OrderFragment;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;


/**
 * Adapter for the items inside a particular order
 */
public class ItemsInOrderAdapter extends ArrayAdapter<Item> {

    private Context context;
    private OrderFragment orderFragment;
    private Order order;
    private int orderId;
    DatabaseHandler db;

    public ItemsInOrderAdapter(Context context, int resource, int orderId, OrderFragment fragment) {
        super(context, resource);
        this.context = context;
        this.orderId = orderId;
        this.orderFragment = fragment;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        final ImageButton removeItemBtn;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_order_item, null);
            TextView description = (TextView) convertView.findViewById(R.id.item_description_txt);
            TextView price = (TextView) convertView.findViewById(R.id.item_price_txt);
            ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.item_remove_btn);
            viewHolder = new ViewHolder(price, description, imageButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        final Item item = getItem(position);
        if (item != null) {
            viewHolder.description.setText(item.getName());
            viewHolder.price.setText("$" + String.valueOf(item.getPrice()));
        }

        viewHolder.mImageButton.setVisibility(View.VISIBLE);
        viewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(context);
                db.removeItemFromOrder(context, order, getItem(position), viewHolder.mImageButton);

                viewHolder.mImageButton.setVisibility(View.GONE);
            }
        });


        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        this.order = db.getOrderById(orderId);
        orderFragment.updatePrice(order);
        super.notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView price;
        public TextView description;
        public ImageButton mImageButton;

        public ViewHolder(TextView price, TextView description, ImageButton imageButton) {
            this.price = price;
            this.description = description;
            this.mImageButton = imageButton;
        }
    }

}
