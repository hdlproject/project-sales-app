package com.project.hdl.salesap.main.synchronize;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.User;
import com.project.hdl.salesap.model.dao.UserDataAccess;
import com.project.hdl.salesap.model.webservice.UserResponse;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

/**
 * Created by hendra.dl on 04/09/2017.
 */

public class SynchronizeUserTask extends AsyncTask<String, Void, Boolean>{

    Activity activity;
    User user;
    String label;

    public SynchronizeUserTask(Activity activity, String label){
        this.activity = activity;
        this.label = label;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SynchronizeAdapter.labelStatusMap.put(label, activity.getString(R.string.label_progress_status));
                SynchronizeActivity.synchronizeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected Boolean doInBackground(String... args) {
        UserResponse userResponse = null;

        try {
            if (NetworkingTool.isInternetConnected(activity)) {
                String jsonUserResponse = WebServiceHelper.postMessage(GlobalURL.GET_USER_URL(), args[0], null);
                if (jsonUserResponse != null) {
                    //save user data to database
                    userResponse = GsonHelper.fromJson(jsonUserResponse, UserResponse.class);
                    user = userResponse.getUser();
                    user.setLogin_id(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
                    UserDataAccess.addOrReplace(activity, user);
                    return true;
                }
                return false;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        SynchronizeActivity.synchronizeUserTask = null;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SynchronizeAdapter.labelStatusMap.put(label, activity.getString(R.string.label_done_status));
                SynchronizeActivity.synchronizeAdapter.notifyDataSetChanged();
            }
        });
        ApplicationTool.goTo(activity, WelcomeActivity.class);
    }

}
