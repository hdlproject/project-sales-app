package com.project.hdl.salesap.main.welcome;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.main.login.LoginActivity;
import com.project.hdl.salesap.main.login.LoginTask;
import com.project.hdl.salesap.main.tasklist.TaskListActivity;
import com.project.hdl.salesap.main.tasklist.TaskListTask;
import com.project.hdl.salesap.main.waypoints.WaypointsActivity;
import com.project.hdl.salesap.model.User;
import com.project.hdl.salesap.model.dao.UserDataAccess;
import com.project.hdl.salesap.model.webservice.TaskListRequest;
import com.project.hdl.salesap.service.RequestDataScheduler;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.application.LocationHelper;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

/**
 * Created by hendra.dl on 20/08/2017.
 */

public class WelcomeActivity extends AppCompatActivity {

    //layout component
    LinearLayout newsHolder;
    RelativeLayout profileHolder;
    TextView newsLabel, nameLabel, jobLabel, officeLabel, branchLabel;
    TextView targetLabel, currentGainLabel;

    Activity activity;
    public static TaskListTask taskListTask;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        activity = this;

        //initiate layout component
        newsHolder = (LinearLayout) findViewById(R.id.newsHolderInclude);
        profileHolder = (RelativeLayout) findViewById(R.id.profileInclude);
        newsLabel = (TextView) newsHolder.findViewById(R.id.newsLabel);
        nameLabel = (TextView) profileHolder.findViewById(R.id.nameLabel);
        jobLabel = (TextView) profileHolder.findViewById(R.id.jobLabel);
        officeLabel = (TextView) profileHolder.findViewById(R.id.officeLabel);
        branchLabel = (TextView) profileHolder.findViewById(R.id.branchLabel);
        targetLabel = (TextView) profileHolder.findViewById(R.id.targetLabel);
        currentGainLabel = (TextView) profileHolder.findViewById(R.id.currentGainLabel);

        user = UserDataAccess.getOne(activity);
        nameLabel.setText(user.getFull_name());
        jobLabel.setText(user.getJob());
        officeLabel.setText(user.getOffice());
        branchLabel.setText(user.getBranch());
        targetLabel.append(user.getTarget());
        if(user.getCurrent_gain() == null || "".equalsIgnoreCase(user.getCurrent_gain())){
            currentGainLabel.append("-");
        }else{
            currentGainLabel.append(user.getCurrent_gain());
        }

        //active marquee text
        newsLabel.setSelected(true);

        newsHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskListRequest taskListRequest = new TaskListRequest();
                taskListRequest.setLastDtmUpd("");
                taskListRequest.setLoginId(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
                taskListRequest.setImei("");
                String jsonTaskListRequest = GsonHelper.toJson(taskListRequest);

                if(taskListTask == null){
                    taskListTask = new TaskListTask(WelcomeActivity.this);
                }
                taskListTask.execute(jsonTaskListRequest);
            }
        });

        activateTaskReq();
        activateLocationReq();

        taskListTask = new TaskListTask(this);
    }

    private void activateTaskReq(){
        if(!RequestDataScheduler.isTaskReqSchedulerRunning()){
            RequestDataScheduler.doTaskRequest(activity, GlobalData.TASK_REQ_INTERVAL, TimeUnit.MINUTES);
        }
    }

    private void activateLocationReq(){
        LocationHelper.init(activity);
    }

    @Override
    public void onBackPressed() {
        //logout
        new MaterialDialog.Builder(WelcomeActivity.this)
                .title(R.string.label_info)
                .content(R.string.message_logout_alert)
                .positiveText(R.string.label_yes)
                .negativeText(R.string.label_no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPreferencesHelper.putBoolean(activity, GlobalConstant.LOGGED_IN, false);
                        if(RequestDataScheduler.isBaseSchedulerRunning()){
                            RequestDataScheduler.killScheduler();
                        }
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
                        ApplicationTool.goTo(activity, LoginActivity.class, stackBuilder);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcome_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.changePassword:
                ApplicationTool.goTo(activity, ChangePasswordActivity.class);
                break;
            case R.id.waypoints:
                ApplicationTool.goTo(activity, WaypointsActivity.class);
                break;
        }

        return true;
    }
}
