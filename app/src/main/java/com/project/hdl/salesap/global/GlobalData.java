package com.project.hdl.salesap.global;

/**
 * Created by hendra.dl on 20/08/2017.
 */

public class GlobalData {
    //scheduler
    public static int TASK_REQ_INTERVAL = 1; //in minute

    //layout
    public static int TASK_LAYOUT_COL_COUNT = 2;

    //maps
    public static float MAPS_ZOOM = 15;
    public static float VALID_SUBMIT_DISTANCE = 1000; //in meter
    public static long LOCATION_REQ_INTERVAL = 10000; //in milisecond

    //task list
    public static int TASK_LIMIT_PER_DAY = 8; //cause google waypoint limit

}
