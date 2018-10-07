package com.project.hdl.salesap.tool.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class SharedPreferencesHelper {
    private static SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Activity activity){
        sharedPreferences = activity.getSharedPreferences(GlobalConstant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void putBoolean(Activity activity, String key, Boolean value){
        if(sharedPreferences == null){
            new SharedPreferencesHelper(activity);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putString(Activity activity, String key, String value){
        if(sharedPreferences == null){
            new SharedPreferencesHelper(activity);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Activity activity, String key){
        if(sharedPreferences == null){
            new SharedPreferencesHelper(activity);
        }
        return sharedPreferences.getBoolean(key, false);
    }

    public static String getString(Activity activity, String key){
        if(sharedPreferences == null){
            new SharedPreferencesHelper(activity);
        }
        return sharedPreferences.getString(key, null);
    }
}
