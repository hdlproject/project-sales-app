package com.project.hdl.salesap.main.tasklist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.tool.application.ApplicationTool;

import java.util.List;

/**
 * Created by hendra.dl on 23/08/2017.
 */

public class TaskListActivity extends AppCompatActivity {

    //layout component
    public static TaskListAdapter taskListAdapter;
    RecyclerView recyclerView;

    Activity activity;
    public static List<Task> taskList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list_activity);
        activity = this;

        //initiate layout component
        recyclerView = (RecyclerView) findViewById(R.id.taskListAdapter);

        taskList = TaskDataAccess.getAllWithRownum(activity, GlobalData.TASK_LIMIT_PER_DAY);
        taskListAdapter = new TaskListAdapter(this, taskList);

        recyclerView.setLayoutManager(new GridLayoutManager(activity, GlobalData.TASK_LAYOUT_COL_COUNT));
        recyclerView.setAdapter(taskListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.addTask:
                ApplicationTool.goTo(activity, AddTaskActivity.class);
                break;
        }

        return true;
    }

}
