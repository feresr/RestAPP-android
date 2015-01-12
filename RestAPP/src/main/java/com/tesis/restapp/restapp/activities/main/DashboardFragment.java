package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.OrdersAdapter;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.User;


public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private MainHandler activity;
    private OrdersAdapter adapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView usernameTxtView = (TextView) rootView.findViewById(R.id.firstLastNameTxt);
        TextView username = (TextView) rootView.findViewById(R.id.usernameTxt);
        User user = User.getUser(getActivity());
        usernameTxtView.setText(user.getFirstname() + " " + user.getLastname());
        username.setText("#" + user.getUsername());

        adapter = new OrdersAdapter(getActivity(), R.id.orders_listview);
        ListView listView = (ListView) rootView.findViewById(R.id.orders_listview);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        listView.setEmptyView(rootView.findViewById(android.R.id.empty));
        Button newOrderBtn = (Button) rootView.findViewById(R.id.neworder_btn);
        newOrderBtn.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        activity.onOrderSelected((Order) parent.getAdapter().getItem(position));
    }

    @Override
    public void onClick(View v) {
        activity.onNewOrderSelected();
    }

    public void updateAdapter() {
        adapter.aNotifyDataSetChanged();
    }
}
