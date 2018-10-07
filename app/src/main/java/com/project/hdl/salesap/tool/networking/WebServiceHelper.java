package com.project.hdl.salesap.tool.networking;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hendra.dl on 21/08/2017.
 */

public class WebServiceHelper {
    private static OkHttpClient client;
    private static MediaType jsonType;
    private static Request request;

    public WebServiceHelper(){
        client = new OkHttpClient();
        jsonType = MediaType.parse("application/json; charset=utf-8");
    }

    public static String postMessage(String url, String jsonBody, String authHeader) {
        if(client==null){
            new WebServiceHelper();
        }
        RequestBody body = body = RequestBody.create(jsonType, jsonBody);
        if(authHeader == null){
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        }else{
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", authHeader)
                    .build();
        }
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200){
                return response.body().string();
            }else{
                return null;
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getMessage(String url, String authHeader) {
        if(client==null){
            new WebServiceHelper();
        }
        if(authHeader == null){
            request = new Request.Builder()
                    .url(url)
                    .build();
        }else{
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", authHeader)
                    .build();
        }
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200){
                return response.body().string();
            }else{
                return null;
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
