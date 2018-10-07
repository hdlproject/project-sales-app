package com.project.hdl.salesap.main.tasklist;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.application.LocationHelper;
import com.project.hdl.salesap.tool.database.DatabaseTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

/**
 * Created by hendra.dl on 06/09/2017.
 */

public class AddTaskActivity extends AppCompatActivity {
    //layout component
    Button addTaskButton;
    EditText custNameText, destAddressText, phoneNumbText;
    EditText latText, lonText;

    Activity activity;
    public static Task task;
    public static AddTaskTask addTaskTask;

    Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        activity = this;

        //init layout component
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        custNameText = (EditText) findViewById(R.id.custNameText);
        destAddressText = (EditText) findViewById(R.id.destAddressText);
        phoneNumbText = (EditText) findViewById(R.id.phoneNumbText);
        latText = (EditText) findViewById(R.id.latText);
        lonText = (EditText) findViewById(R.id.lonText);

        location = LocationHelper.getLocation(activity);
        latText.setText(Double.toString(location.getLatitude()));
        lonText.setText(Double.toString(location.getLongitude()));

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new Task();
                task.setLogin_id(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
                task.setCust_name(custNameText.getText().toString());
                task.setDest_address(destAddressText.getText().toString());
                task.setDtm_assign(DatabaseTool.getCurrentDtmMillis());
                task.setPhone_numb(phoneNumbText.getText().toString());
                task.setUid_task(DatabaseTool.getSafeUID());
                if(location != null){
                    task.setLat(Double.toString(location.getLatitude()));
                    task.setLon(Double.toString(location.getLongitude()));
                }

                String jsonAddTaskRequest = GsonHelper.toJson(task);
                addTaskTask.execute(jsonAddTaskRequest);
            }
        });

        addTaskTask = new AddTaskTask(activity);
    }
}
