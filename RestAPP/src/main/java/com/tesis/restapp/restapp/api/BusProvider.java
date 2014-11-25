package com.tesis.restapp.restapp.api;

import com.squareup.otto.Bus;

/**
 * Created by Fer on 11/16/2014.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}