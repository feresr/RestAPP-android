package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.OrdersAdapter;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    private MainHandler activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (MainHandler) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement MainHandler");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final DatabaseHandler db = new DatabaseHandler(this.getActivity());
        RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient(getActivity());
        apiInterface.retrieveOrders(new Callback<List<Order>>() {
            @Override
            public void success(List<Order> orders, Response response) {
                db.addOrders(orders);
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO: User logged out (or server not found). take out to the login screen. Do error handling
                Log.e("retrofit_error",error.getMessage());
            }
        });



        TextView usernameTxtView = (TextView) rootView.findViewById(R.id.firstLastNameTxt);
        TextView username = (TextView) rootView.findViewById(R.id.usernameTxt);

        usernameTxtView.setText(User.getUser(getActivity()).getFirstname() + " " + User.getUser(getActivity()).getLastname());
        username.setText("#" + User.getUser(getActivity()).getUsername());

        OrdersAdapter adapter = new OrdersAdapter(getActivity(), R.id.orders_listview);
        ListView listView = (ListView)rootView.findViewById(R.id.orders_listview);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

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
}
