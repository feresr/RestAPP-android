package com.tesis.restapp.restapp.activities.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.ItemsInOrderAdapter;
import com.tesis.restapp.restapp.models.Order;


/**
 * A placeholder fragment containing a simple view.
 */
public class OrderFragment extends Fragment {

    private Button closeOrder;
    private TextView orderTotal;
    private ListView itemsListView;
    private TextView emptyListViewText;
    private TextView tableNumber;
    private ItemsInOrderAdapter adapter;
    private Order order;
    private String TAG = OrderFragment.class.getSimpleName();
    public OrderFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int orderId = getArguments().getInt("order_id");
        order = Order.getOrderById(orderId);
        View rootView = inflater.inflate(R.layout.fragment_order, container,
                false);

        itemsListView = (ListView) rootView.findViewById(R.id.items_listview);

        adapter = new ItemsInOrderAdapter(getActivity(), R.layout.listview_order_item ,order);
        itemsListView.setAdapter(adapter);
        emptyListViewText = (TextView) rootView.findViewById(R.id.empty);
        itemsListView.setEmptyView(emptyListViewText);

        orderTotal = (TextView) rootView.findViewById(R.id.order_total);

        tableNumber = (TextView) rootView.findViewById(R.id.table_number_txt);
        tableNumber.setText(String.valueOf(order.getTable().getNumber()));

        Toast.makeText(getActivity(), String.valueOf(order.getItems().size()),
                Toast.LENGTH_SHORT).show();

        return rootView;
    }



}