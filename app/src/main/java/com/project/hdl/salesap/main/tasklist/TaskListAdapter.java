package com.project.hdl.salesap.main.tasklist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.main.sellingreport.SellingReportActivity;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.main.maps.MapsActivity;
import com.project.hdl.salesap.tool.application.ApplicationTool;

import java.util.List;

/**
 * Created by hendra.dl on 23/08/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {
    List<Task> taskList;
    Activity activity;

    public TaskListAdapter(Activity activity, List<Task> taskList){
        this.taskList = taskList;
        this.activity = activity;
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder{
        public TextView custNameLabel, phoneNumbLabel, assignDateLabel;
        public ImageView mapsImage, reportImage;
        public Task task;

        public TaskListViewHolder(View view){
            super(view);
            custNameLabel = (TextView) view.findViewById(R.id.custNameLabel);
            phoneNumbLabel = (TextView) view.findViewById(R.id.phoneNumbLabel);
            assignDateLabel = (TextView) view.findViewById(R.id.assignDateLabel);
            mapsImage = (ImageView) view.findViewById(R.id.mapsImage);
            reportImage = (ImageView) view.findViewById(R.id.reportImage);
        }
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);

        return new TaskListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskListViewHolder holder, int position) {
        holder.task = taskList.get(position);
        if(holder.task.getCust_name().isEmpty()){
            holder.custNameLabel.setText(R.string.label_default_null);
        }else{
            holder.custNameLabel.setText(holder.task.getCust_name());
        }
        holder.custNameLabel.setSelected(true);
        if(holder.task.getPhone_numb().isEmpty()){
            holder.phoneNumbLabel.setText(R.string.label_default_null);
        }else{
            holder.phoneNumbLabel.setText(holder.task.getPhone_numb());
        }
        if(holder.task.getDtm_assign().isEmpty()){
            holder.assignDateLabel.setText(R.string.label_default_null);
        }else{
            holder.assignDateLabel.setText(holder.task.getDtm_assign());
        }
        holder.mapsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMaps(holder.task);
            }
        });
        holder.reportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSellingReport(holder.task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    private void gotoMaps(Task task){
        Bundle bundle = new Bundle();
        bundle.putDouble(MapsActivity.LAT_MAP_KEY, Double.parseDouble(task.getLat()));
        bundle.putDouble(MapsActivity.LON_MAP_KEY, Double.parseDouble(task.getLon()));
        ApplicationTool.goTo(activity, MapsActivity.class, bundle);
    }

    private void gotoSellingReport(Task task){
        Bundle bundle = new Bundle();
        bundle.putString(SellingReportActivity.UID_TASK_KEY, task.getUid_task());
        bundle.putDouble(MapsActivity.LAT_MAP_KEY, Double.parseDouble(task.getLat()));
        bundle.putDouble(MapsActivity.LON_MAP_KEY, Double.parseDouble(task.getLon()));
        ApplicationTool.goTo(activity, SellingReportActivity.class, bundle);
    }

}