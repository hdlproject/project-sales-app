package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.tool.networking.BaseRequest;

/**
 * Created by hendra.dl on 13/09/2017.
 */

public class AddTaskRequest extends BaseRequest {
    @SerializedName("task")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
