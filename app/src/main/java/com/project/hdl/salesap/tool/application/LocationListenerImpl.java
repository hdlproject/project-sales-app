package com.project.hdl.salesap.tool.application;

import android.location.Location;

import com.google.android.gms.location.LocationListener;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class LocationListenerImpl implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        LocationHelper.location = location;
    }
}
