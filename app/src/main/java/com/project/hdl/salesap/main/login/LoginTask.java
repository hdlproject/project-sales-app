package com.project.hdl.salesap.main.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.synchronize.SynchronizeActivity;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.webservice.LoginRequest;
import com.project.hdl.salesap.model.webservice.LoginResponse;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class LoginTask extends AsyncTask<String, Void, Boolean> {
    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;
    String loginId;
    String password;
    String syncList;

    public LoginTask(Activity activity){
        this.activity = activity;
        this.messageWait = activity.getString(R.string.message_please_wait);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        materialDialog = new MaterialDialog.Builder(this.activity)
                .content(R.string.message_please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }

    @Override
    protected Boolean doInBackground(String ... args) {
        loginId = args[0];
        password = args[1];
        if(NetworkingTool.isInternetConnected(activity)){
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLoginId(args[0]);
            loginRequest.setPassword(args[1]);
            loginRequest.setImei("");
            LoginResponse loginResponse = null;

            String jsonLoginRequest = GsonHelper.toJson(loginRequest);
            String jsonLoginResponse = WebServiceHelper.postMessage(GlobalURL.GET_LOGIN_URL(), jsonLoginRequest, null);
            if(jsonLoginResponse != null){
                loginResponse = GsonHelper.fromJson(jsonLoginResponse, LoginResponse.class);
                if(loginResponse.getErrorMessage() == null
                        && GlobalConstant.SUCCESS.equalsIgnoreCase(loginResponse.getStatus())){
//                    syncList = loginResponse.getSynchronize_list();
                    syncList = "user";
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (this.materialDialog.isShowing()){
            this.materialDialog.dismiss();
        }
        LoginActivity.loginTask = null;
        if(result){
            doSaveLoginStatus();
            ApplicationTool.goTo(activity, SynchronizeActivity.class);
        }else{

        }
    }

    private void doSaveLoginStatus(){
        SharedPreferencesHelper.putBoolean(this.activity, GlobalConstant.LOGGED_IN, true);
        SharedPreferencesHelper.putString(this.activity, GlobalConstant.LOGIN_ID, loginId);
        SharedPreferencesHelper.putString(this.activity, GlobalConstant.PASSWORD, password);
        SharedPreferencesHelper.putString(this.activity, GlobalConstant.SYNC_LIST, syncList);
    }
}
