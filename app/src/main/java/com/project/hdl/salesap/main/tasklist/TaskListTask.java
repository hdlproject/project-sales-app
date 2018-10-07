package com.project.hdl.salesap.main.tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.model.webservice.TaskListResponse;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

import java.util.List;

/**
 * Created by hendra.dl on 01/09/2017.
 */

public class TaskListTask extends AsyncTask<String, Void, Boolean> {
    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;
    List<Task> taskList;

    public TaskListTask(Activity activity){
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
        TaskListResponse taskListResponse = null;

        if(NetworkingTool.isInternetConnected(activity)){
            String jsonTaskListResponse = WebServiceHelper.postMessage(GlobalURL.GET_TASK_LIST_URL(), args[0], null);
            if (jsonTaskListResponse != null) {
                //save task data to database
                taskListResponse = GsonHelper.fromJson(jsonTaskListResponse, TaskListResponse.class);
                taskList = taskListResponse.getTaskList();
                TaskDataAccess.addOrReplace(activity, taskList);
                return true;
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
        WelcomeActivity.taskListTask = null;
        if(result){
            ApplicationTool.goTo(activity, TaskListActivity.class);
        }else{

        }
    }

}
