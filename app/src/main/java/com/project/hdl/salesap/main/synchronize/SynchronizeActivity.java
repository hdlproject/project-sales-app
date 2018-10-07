package com.project.hdl.salesap.main.synchronize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.main.login.LoginActivity;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.webservice.UserRequest;
import com.project.hdl.salesap.model.webservice.UserResponse;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hendra.dl on 04/09/2017.
 */

public class SynchronizeActivity extends AppCompatActivity{

    //constant
    public static final String SYNC_USER_KEY = "USER";

    //layout component
    public static SynchronizeAdapter synchronizeAdapter;
    ListView synchronizeListView;

    Activity activity;
    List<String> synchronizeList;
    String synchronizeListString;
    public static SynchronizeUserTask synchronizeUserTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synchronize_activity);
        activity = this;

        //initiate layout component
        synchronizeListView = (ListView) findViewById(R.id.synchronizeListAdaper);

        synchronizeListString = SharedPreferencesHelper.getString(activity, GlobalConstant.SYNC_LIST);
        synchronizeList = Arrays.asList(synchronizeListString.split(","));
        synchronizeAdapter = new SynchronizeAdapter
                (this, R.layout.synchronize_activity, R.id.synchronizeLabel, synchronizeList);
        synchronizeListView.setAdapter(synchronizeAdapter);

        doSync();
    }

    private void doSync(){
        for(String sync : synchronizeList){
            if(SynchronizeActivity.SYNC_USER_KEY.equalsIgnoreCase(sync)){
                synchronizeUserTask = new SynchronizeUserTask(this, sync);

                UserRequest userRequest = new UserRequest();
                userRequest.setLoginId(SharedPreferencesHelper.getString(this, GlobalConstant.LOGIN_ID));
                userRequest.setImei("");
                String jsonUserRequest = GsonHelper.toJson(userRequest);
                synchronizeUserTask.execute(jsonUserRequest);
            }
        }
    }

}
