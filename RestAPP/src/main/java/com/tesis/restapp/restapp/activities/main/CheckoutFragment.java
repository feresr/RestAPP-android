package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.models.Order;

/**
 * Presents the user with the due amount and the option to confirm the order closure.
 */
public class CheckoutFragment extends Fragment implements View.OnClickListener {

    private Button closeOrder;
    private TextView orderTotalTxt;
    private OrderFragment.OrderFragmentCallbacks activity;
    private Order order;

    public static CheckoutFragment newInstance(Order order) {
        CheckoutFragment checkoutFragment = new CheckoutFragment();
        Bundle b = new Bundle();
        b.putParcelable(Order.class.getName(), order);
        checkoutFragment.setArguments(b);
        return checkoutFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (OrderFragment.OrderFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CheckoutFragmentCallbacks");
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
        if (getArguments() != null) {
            order = getArguments().getParcelable(Order.class.getName());
        }

        orderTotalTxt = (TextView) rootView.findViewById(R.id.order_total);
        orderTotalTxt.setText(String.valueOf(order.getTotal()));


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_order_btn:
                activity.onCloseOrder(order);
                break;

        }
    }


}
