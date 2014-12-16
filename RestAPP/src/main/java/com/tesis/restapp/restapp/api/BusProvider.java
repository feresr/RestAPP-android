package com.tesis.restapp.restapp.api;

import com.squareup.otto.Bus;

/**
 * Created by Fer on 11/16/2014.
 */
public final class BusProvider {
    private static Bus BUS;

    public static Bus getInstance() {
        if (BUS == null) {
            BUS = new Bus();
        }
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}