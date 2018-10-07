package com.project.hdl.salesap.tool.database;

import com.google.gson.Gson;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class GsonHelper {
    private static Gson gson;

    public GsonHelper(){
        gson = new Gson();
    }

    public static <T> T fromJson(String json, java.lang.Class<T> classOfT) {
        if(gson == null){
            new GsonHelper();
        }
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object src){
        if(gson == null){
            new GsonHelper();
        }
        return gson.toJson(src);
    }
}
