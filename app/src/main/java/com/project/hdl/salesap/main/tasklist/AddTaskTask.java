package com.project.hdl.salesap.main.tasklist;

import android.app.Activity;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.model.webservice.AddTaskResponse;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

/**
 * Created by hendra.dl on 13/09/2017.
 */

public class AddTaskTask extends AsyncTask<String, Void, Boolean> {

    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;

    public AddTaskTask(Activity activity){
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
        AddTaskResponse addTaskResponse = null;

        if(NetworkingTool.isInternetConnected(activity)){
            String jsonAddTaskResponse = WebServiceHelper.postMessage(GlobalURL.GET_ADD_TASK_URL(), args[0], null);
            if (jsonAddTaskResponse != null) {
                addTaskResponse = GsonHelper.fromJson(jsonAddTaskResponse, AddTaskResponse.class);
                if(addTaskResponse.getErrorMessage() == null &&
                        GlobalConstant.SUCCESS.equalsIgnoreCase(addTaskResponse.getStatus())){
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
        AddTaskActivity.addTaskTask = null;
        if(result){
            TaskDataAccess.addOrReplace(activity, AddTaskActivity.task);
            TaskListActivity.taskList.add(AddTaskActivity.task);
            TaskListActivity.taskListAdapter.notifyDataSetChanged();

            ApplicationTool.goTo(activity, TaskListActivity.class);
        }else{

        }
    }
}
