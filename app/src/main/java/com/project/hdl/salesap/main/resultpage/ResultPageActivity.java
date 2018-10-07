package com.project.hdl.salesap.main.resultpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.service.RequestDataScheduler;
import com.project.hdl.salesap.tool.application.ApplicationTool;

/**
 * Created by hendra.dl on 01/09/2017.
 */

public class ResultPageActivity extends AppCompatActivity{

    //constant
    public static final String RESULT_STATUS_KEY = "RESULT_STATUS";
    public static final String RESULT_MESSAGE_KEY = "RESULT_MESSAGE";
    public static final String UID_TASK_KEY = "UID_TASK";

    //layout compinent
    ImageView resultImage;
    TextView resultMessageLabel;

    Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page_activity);
        activity = this;

        //initiate layout component
        resultImage = (ImageView) findViewById(R.id.resultImage);
        resultMessageLabel = (TextView) findViewById(R.id.resultMessageLabel);

        if(GlobalConstant.SUCCESS.equalsIgnoreCase(getIntent().getExtras().getString(RESULT_STATUS_KEY))){
            resultImage.setImageResource(R.drawable.success_image);
            Task task = TaskDataAccess.getOneByUid(activity, getIntent().getExtras().getString(UID_TASK_KEY));
            task.setStatus(GlobalConstant.TASK_FINISHED);
            TaskDataAccess.addOrReplace(activity, task);
        }else{
            resultImage.setImageResource(R.drawable.failed_image);
        }

        resultMessageLabel.setText(getIntent().getExtras().getString(RESULT_MESSAGE_KEY));

        resultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RequestDataScheduler.isTaskReqSchedulerPause()){
                    RequestDataScheduler.continueTaskReqScheduler();
                }
                ApplicationTool.goTo(activity, WelcomeActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(RequestDataScheduler.isTaskReqSchedulerPause()){
            RequestDataScheduler.continueTaskReqScheduler();
        }
        super.onBackPressed();
    }
}
