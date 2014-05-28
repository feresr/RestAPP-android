package com.tesis.restapp.restapp.activities.main;

import com.tesis.restapp.restapp.models.Order;

/**
 * Created by feresr on 5/28/14.
 */
public interface MainHandler {

    public void onOrderSelected(int orderId);

    public void onNewOrderSelected();

}
