package com.project.hdl.salesap.tool.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.List;

/**
 * Created by hendra.dl on 28/08/2017.
 */

public class ApplicationTool {

    public static boolean isPermissionGranted(Activity activity, String permission){
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }

    public static boolean isNeedExplanation(Activity activity, String permissionName){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermission(Activity activity, String permissionName){
        ActivityCompat.requestPermissions(activity, new String[]{permissionName}, 0);
    }

    public static void requestPermission(Activity activity, List<String> permissionNameList){
        ActivityCompat.requestPermissions(activity, permissionNameList.toArray(new String[0]), 0);
    }

    public static boolean checkPlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 0).show();
                return false;
            } else {
                return false;
            }
        }
        return true;
    }

    public static void goTo(Activity sourceActivity, Class destClass){
        Intent intent = new Intent(sourceActivity, destClass);
        sourceActivity.startActivity(intent);
    }

    public static void goTo(Activity sourceActivity, Class destClass, TaskStackBuilder stackBuilder){
        Intent intent = new Intent(sourceActivity, destClass);
        stackBuilder.addNextIntent(intent);
        stackBuilder.startActivities();
    }

    public static void goTo(Activity sourceActivity, Class destClass, Bundle bundle){
        Intent intent = new Intent(sourceActivity, destClass);
        intent.putExtras(bundle);
        sourceActivity.startActivity(intent);
    }

    public static void goTo(Activity sourceActivity, Class destClass, TaskStackBuilder stackBuilder, Bundle bundle){
        Intent intent = new Intent(sourceActivity, destClass);
        intent.putExtras(bundle);
        stackBuilder.addNextIntent(intent);
        stackBuilder.startActivities();
    }
}
