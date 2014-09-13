package com.tesis.restapp.restapp.activities.main;

import com.tesis.restapp.restapp.models.Order;
import com.tesis.restapp.restapp.models.Table;

/**
 * Created by feresr on 5/28/14.
 */
public interface MainHandler {

    public void onOrderSelected(int orderId, boolean newOrder);

    public void onNewOrderSelected();

    public void onTableSelected(Table table);

    public void onAddItemOptionSelected();

    public Order getSelectedOrder();

    public void onCloseOrderSelected();

    public void onCloseOrder();

}
