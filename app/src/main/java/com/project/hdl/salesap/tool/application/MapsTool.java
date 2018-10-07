package com.project.hdl.salesap.tool.application;

import android.app.Activity;
import android.location.Location;

import com.project.hdl.salesap.R;

import java.util.List;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class MapsTool {
    public static String getStringWaypoints(List<Location> locationList){
        String initWaypoints = "waypoints=";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initWaypoints);
        for(Location location : locationList){
            stringBuilder.append(Double.toString(location.getLatitude()));
            stringBuilder.append(",");
            stringBuilder.append(Double.toString(location.getLongitude()));
            stringBuilder.append("|");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    public static String getStringOrigin(Location location){
        String initOrigin = "origin=";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initOrigin);
        stringBuilder.append(Double.toString(location.getLatitude()));
        stringBuilder.append(",");
        stringBuilder.append(Double.toString(location.getLongitude()));
        return stringBuilder.toString();
    }

    public static String getStringDest(Location location){
        String initDest = "destination=";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initDest);
        stringBuilder.append(Double.toString(location.getLatitude()));
        stringBuilder.append(",");
        stringBuilder.append(Double.toString(location.getLongitude()));
        return stringBuilder.toString();
    }

    public static Location getFarthestLocation(Activity activity, List<Location> locationList){
        Location currentLocation = LocationHelper.getLocation(activity);
        Location farthestLocation = currentLocation;
        float farthestDistance = 0;
        for(Location location : locationList) {
            float[] distance = new float[2];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
            if (distance[0] >= farthestDistance) {
                farthestDistance = distance[0];
                farthestLocation = location;
            }
        }
        return farthestLocation;
    }

    public static List<Location> getRemovedFarthestLocationList(Activity activity, List<Location> locationList){
        Location currentLocation = LocationHelper.getLocation(activity);
        float farthestDistance = 0;
        int index = 0;
        int farthestIndex = 0;
        for(Location location : locationList) {
            float[] distance = new float[2];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                    currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
            if (distance[0] >= farthestDistance) {
                farthestDistance = distance[0];
                farthestIndex = index;
            }
            index++;
        }
        locationList.remove(farthestIndex);
        return locationList;
    }

    public static String getStringMapsKeyServer(Activity activity){
        String initKey = "key=";
        String key = activity.getString(R.string.google_maps_key_server);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initKey);
        stringBuilder.append(key);
        return stringBuilder.toString();
    }

    public static String getStringMapsKey(Activity activity){
        String initKey = "key=";
        String key = activity.getString(R.string.google_maps_key);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(initKey);
        stringBuilder.append(key);
        return stringBuilder.toString();
    }
}
