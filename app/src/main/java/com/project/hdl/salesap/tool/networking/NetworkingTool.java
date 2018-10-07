package com.project.hdl.salesap.tool.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hendra.dl on 20/08/2017.
 */

public class NetworkingTool {

    public static boolean isInternetConnected(Context context) {
        try {
            final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null) {
                    return ni.isConnected();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
