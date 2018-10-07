package com.project.hdl.salesap.service;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.google.gson.JsonParseException;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.tasklist.TaskListActivity;
import com.project.hdl.salesap.main.waypoints.WaypointsActivity;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.model.webservice.TaskListRequest;
import com.project.hdl.salesap.model.webservice.TaskListResponse;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.NORM_PRIORITY;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class RequestDataScheduler {
    private static ScheduledExecutorService scheduler;
    private static ScheduledFuture<?> taskRequestHandle;

    private static List<Task> taskList;
    private static boolean isTaskReqPause = false;

    public RequestDataScheduler(){
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public static void doTaskRequest(final Activity activity, int interval, TimeUnit timeUnit) {
        final Runnable taskRequest = new Runnable() {
            public void run() {
                if(!isTaskReqPause) {
                    if (!Thread.currentThread().isInterrupted()) {
                        TaskListRequest taskListRequest = new TaskListRequest();
                        taskListRequest.setLastDtmUpd("");
                        taskListRequest.setLoginId(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
                        taskListRequest.setImei("");
                        TaskListResponse taskListResponse = null;

                        try {
                            //request task data
                            String jsonTaskListRequest = GsonHelper.toJson(taskListRequest);
                            String jsonTaskListResponse = WebServiceHelper.postMessage(GlobalURL.GET_TASK_LIST_URL(), jsonTaskListRequest, null);
                            if (jsonTaskListResponse != null) {
                                //save task data to database
                                taskListResponse = GsonHelper.fromJson(jsonTaskListResponse, TaskListResponse.class);
                                taskList = taskListResponse.getTaskList();
                                TaskDataAccess.addOrReplace(activity, taskList);
                            }
                        } catch (JsonParseException jpe) {
                            jpe.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //show notification
                        //create pending intent task list
                        Intent taskListIntent = new Intent(activity, TaskListActivity.class);
                        TaskStackBuilder taskListStackBuilder = TaskStackBuilder.create(activity);
                        taskListStackBuilder.addParentStack(TaskListActivity.class);
                        taskListStackBuilder.addNextIntent(taskListIntent);
                        PendingIntent taskListPendingIntent =
                                taskListStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        //create pending intent waypoints
                        Intent waypointsIntent = new Intent(activity, WaypointsActivity.class);
                        TaskStackBuilder waypointsStackBuilder = TaskStackBuilder.create(activity);
                        waypointsStackBuilder.addParentStack(WaypointsActivity.class);
                        waypointsStackBuilder.addNextIntent(waypointsIntent);
                        PendingIntent waypointsPendingIntent =
                                waypointsStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        //custom notif view
//                    RemoteViews remoteViews = new RemoteViews(activity.getPackageName(), R.layout.task_list_notification);
//                    remoteViews.setTextViewText(R.id.titleLabel, activity.getString(R.string.message_task_notification, taskList.size()));
//                    remoteViews.setTextViewText(R.id.descLabel, activity.getString(R.string.label_task_notification));

                        //create notification
                        NotificationCompat.BigTextStyle bigTextStyle =
                                new NotificationCompat.BigTextStyle();
                        bigTextStyle.setBigContentTitle(activity.getString(R.string.message_task_notification, taskList.size()));
                        bigTextStyle.bigText(activity.getString(R.string.label_task_notification));

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity);
                        builder.setContentIntent(taskListPendingIntent);
                        builder.setContentTitle(activity.getString(R.string.message_task_notification, taskList.size()));
                        builder.setContentText(activity.getString(R.string.label_task_notification));
                        builder.setSmallIcon(R.drawable.apps_icon);
                        builder.setStyle(bigTextStyle);
                        builder.setPriority(NORM_PRIORITY);
                        builder.setDefaults(android.app.Notification.DEFAULT_ALL);
                        builder.setAutoCancel(true);
                        builder.addAction(R.drawable.waypoints_icon, activity.getString(R.string.label_cap_waypoints), waypointsPendingIntent);
                        builder.addAction(R.drawable.task_list_icon, activity.getString(R.string.label_task_list), taskListPendingIntent);

                        //show notification
                        NotificationManager mNotificationManager =
                                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(1, builder.build());
                    }
                }

            }
        };

        if(scheduler == null){
            new RequestDataScheduler();
        }

        try {
            taskRequestHandle =
                    scheduler.scheduleAtFixedRate(taskRequest, 0, interval, timeUnit);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isBaseSchedulerRunning(){
        if(scheduler != null){
            return true;
        }else{
            return false;
        }
    }

    public static void killScheduler(){
        taskRequestHandle.cancel(true);
        scheduler.shutdown();
        taskRequestHandle = null;
        scheduler = null;
    }


    public static boolean isTaskReqSchedulerRunning(){
        if(taskRequestHandle != null){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isTaskReqSchedulerPause(){
        return isTaskReqPause;
    }

    public static void pauseTaskReqScheduler(){
        isTaskReqPause = true;
    }

    public static void continueTaskReqScheduler(){
        isTaskReqPause = false;
    }
}
