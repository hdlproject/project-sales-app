package com.project.hdl.salesap.global;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class GlobalURL {
    private static final String MAIN = "http://project-piranha.pe.hu/api/";
    private static final String GET_TASK_LIST = "getTaskList.php";
    private static final String LOGIN = "login.php";
    private static final String SELLING_REPORT = "sellingReport.php";
    private static final String GET_USER = "getUser.php";
    private static final String CHANGE_PASSWORD = "changePassword.php";
    private static final String ADD_TASK = "addTask.php";
    private static final String WAYPOINTS_MAIN = "https://maps.googleapis.com/maps/api/directions/json?";

    public static String GET_TASK_LIST_URL(){
        return MAIN + GET_TASK_LIST;
    }

    public static String GET_LOGIN_URL(){
        return MAIN + LOGIN;
    }

    public static String GET_SELLING_REPORT_URL(){
        return MAIN + SELLING_REPORT;
    }

    public static String GET_USER_URL(){
        return MAIN + GET_USER;
    }

    public static String GET_CHANGE_PASSWORD_URL(){
        return MAIN + CHANGE_PASSWORD;
    }

    public static String GET_ADD_TASK_URL(){
        return MAIN + CHANGE_PASSWORD;
    }

    public static String GET_WAYPOINTS_URL(String paramURL){
        return WAYPOINTS_MAIN + paramURL;
    }
}
