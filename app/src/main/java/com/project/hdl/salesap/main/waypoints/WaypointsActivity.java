package com.project.hdl.salesap.main.waypoints;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.tool.application.LocationHelper;
import com.project.hdl.salesap.tool.application.MapsTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class WaypointsActivity extends AppCompatActivity {
    String waypoints;
    String origin;
    String destination;
    String sensor;
    String key;
    String param;
    String url;
    List<Location> locationList;
    List<Task> taskList;
    Activity activity;
    public static WaypointsTask waypointsTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waypoints_activity);
        activity = this;

        taskList = TaskDataAccess.getAllWithRownum(activity, GlobalData.TASK_LIMIT_PER_DAY);
        locationList = new ArrayList<>();
        for(Task task : taskList){
            Location location = new Location("");
            location.setLatitude(Double.parseDouble(task.getLat()));
            location.setLongitude(Double.parseDouble(task.getLon()));
            locationList.add(location);
        }
        origin = MapsTool.getStringOrigin(LocationHelper.getLocation(activity));
        destination = MapsTool.getStringDest(MapsTool.getFarthestLocation(activity, locationList));
        waypoints = MapsTool.getStringWaypoints(MapsTool.getRemovedFarthestLocationList(activity, locationList));
        sensor = "sensor=false";
        key = MapsTool.getStringMapsKeyServer(activity);
        param = origin+"&"+destination+"&"+sensor+"&"+waypoints+"&"+key;
        url = GlobalURL.GET_WAYPOINTS_URL(param);

        waypointsTask = new WaypointsTask(activity);
        waypointsTask.execute(url);
    }
}
