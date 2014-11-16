package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.ItemsInOrderAdapter;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Item;
import com.tesis.restapp.restapp.models.Order;


/**
 * A placeholder fragment containing a simple view.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    private Button closeOrder;
    private ListView itemsListView;
    private TextView emptyListViewText;
    private TextView tableNumber;
    private ItemsInOrderAdapter adapter;
    private MainHandler activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (MainHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainHandler");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_order, container,
                false);

        itemsListView = (ListView) rootView.findViewById(R.id.items_listview);

        adapter = new ItemsInOrderAdapter(
                getActivity(),
                R.layout.listview_order_item,
                activity.getSelectedOrder().getId());

        itemsListView.setAdapter(adapter);
        emptyListViewText = (TextView) rootView.findViewById(R.id.empty);
        itemsListView.setEmptyView(emptyListViewText);

        tableNumber = (TextView) rootView.findViewById(R.id.table_number_txt);
        tableNumber.setText(String.valueOf(activity.getSelectedOrder().getTable().getNumber()));

        Toast.makeText(getActivity(), String.valueOf(activity.getSelectedOrder().getItems().size()),
                Toast.LENGTH_SHORT).show();

        closeOrder = (Button) rootView.findViewById(R.id.close_order_btn);
        closeOrder.setOnClickListener(this);



        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.order, menu);
        getActivity().getActionBar().setTitle("Order");
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_item:
                activity.onAddItemOptionSelected();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.close_order_btn:

                activity.onCloseOrderSelected();

                break;

        }
    }
}