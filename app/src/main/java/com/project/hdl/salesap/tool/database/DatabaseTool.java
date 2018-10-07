package com.project.hdl.salesap.tool.database;

import com.fasterxml.uuid.Generators;

import java.util.Date;

/**
 * Created by hendra.dl on 21/08/2017.
 */

public class DatabaseTool {
    public static String getUID() {
        String uuid = Generators.timeBasedGenerator().generate().toString().toUpperCase();
        return uuid;
    }

    public static String getSafeUID() {
        //Mobile signed UID for more safe mobile generated UID to operate on web server
        String uuid = "M~" + Generators.timeBasedGenerator().generate().toString().toUpperCase();
        return uuid;
    }

    public static String getCurrentDtmMillis() {
        return Long.toString(System.currentTimeMillis());
    }
}
