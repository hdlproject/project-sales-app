package com.project.hdl.salesap.tool.application;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.project.hdl.salesap.global.GlobalData;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class LocationHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    Activity activity;
    public static Location location;
    private static GoogleApiClient googleApiClient;
    private static LocationRequest locationRequest;
    private static LocationListenerImpl locationListener;

    public LocationHelper(Activity activity){
        this.activity = activity;
        locationListener = new LocationListenerImpl();
        if(ApplicationTool.checkPlayServices(activity)){
            initLocationClient();
            initLocationRequest();
        }
    }

    public static void init(Activity activity){
        new LocationHelper(activity);
    }

    private void initLocationClient(){
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    private void initLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(0);
        locationRequest.setInterval(GlobalData.LOCATION_REQ_INTERVAL);
        locationRequest.setFastestInterval(GlobalData.LOCATION_REQ_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public static void startPeriodicRequest(Activity activity){
        if(googleApiClient == null){
            new LocationHelper(activity);
        }else if(!googleApiClient.isConnected()){
            googleApiClient.connect();
        }

        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, locationListener);
        }catch(SecurityException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Location getLocation(Activity activity){
        if(googleApiClient == null){
            new LocationHelper(activity);
        }else if(!googleApiClient.isConnected()){
            googleApiClient.connect();
        }

        try{
            Location location;
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            return location;
        }catch(SecurityException se){
            se.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void stopPeriodicRequest(){
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, locationListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startPeriodicRequest(activity);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }
}
