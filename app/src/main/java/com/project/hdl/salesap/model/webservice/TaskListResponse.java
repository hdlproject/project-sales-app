package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.tool.networking.BaseResponse;

import java.util.List;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class TaskListResponse extends BaseResponse {
    @SerializedName("list_of_task")
    List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
