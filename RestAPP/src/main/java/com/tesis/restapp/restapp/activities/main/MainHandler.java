package com.tesis.restapp.restapp.activities.main;

import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;

/**
 * Created by feresr on 5/28/14.
 */
public interface MainHandler {

    public void onOrderSelected(Order order);

    public void onNewOrderSelected();

    public void onTableSelected(Table table);

}
