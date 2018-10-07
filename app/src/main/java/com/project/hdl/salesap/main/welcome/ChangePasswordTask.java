package com.project.hdl.salesap.main.welcome;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.login.LoginActivity;
import com.project.hdl.salesap.model.webservice.ChangePasswordResponse;
import com.project.hdl.salesap.service.RequestDataScheduler;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class ChangePasswordTask extends AsyncTask<String, Void, Boolean> {

    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;

    public ChangePasswordTask(Activity activity){
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
    protected Boolean doInBackground(String... args) {
        ChangePasswordResponse changePasswordResponse = null;

        if(NetworkingTool.isInternetConnected(activity)) {
            String jsonChangePasswordResponse = WebServiceHelper.postMessage
                    (GlobalURL.GET_CHANGE_PASSWORD_URL(), args[0], null);
            if(jsonChangePasswordResponse != null){
                changePasswordResponse = GsonHelper.fromJson(jsonChangePasswordResponse, ChangePasswordResponse.class);
                if(changePasswordResponse.getErrorMessage() == null
                        && GlobalConstant.SUCCESS.equalsIgnoreCase(changePasswordResponse.getStatus())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (this.materialDialog.isShowing()){
            this.materialDialog.dismiss();
        }
        ChangePasswordActivity.changePasswordTask = null;
        if(result){
            new MaterialDialog.Builder(activity)
                    .title(R.string.label_info)
                    .content(R.string.message_change_password_alert)
                    .positiveText(R.string.label_ok)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferencesHelper.putBoolean(activity, GlobalConstant.LOGGED_IN, false);
                            if(RequestDataScheduler.isBaseSchedulerRunning()){
                                RequestDataScheduler.killScheduler();
                            }
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
                            ApplicationTool.goTo(activity, LoginActivity.class);
                        }
                    })
                    .show();
        }
    }
}
