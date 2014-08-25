package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;

/**
 * Created by feresr on 6/16/14.
 */
public class CheckoutFragment extends Fragment implements View.OnClickListener {

    private Button closeOrder;
    private TextView orderTotalTxt;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkout, container,
                false);

        assert rootView != null;
        closeOrder = (Button) rootView.findViewById(R.id.close_order_btn);
        closeOrder.setOnClickListener(this);

        orderTotalTxt = (TextView) rootView.findViewById(R.id.order_total);
        orderTotalTxt.setText(String.valueOf(activity.getSelectedOrder().getTotal()));


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_order_btn:
                activity.onCloseOrder();
                break;

        }
    }


}
