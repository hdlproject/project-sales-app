package com.project.hdl.salesap.tool.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.model.DaoMaster;
import com.project.hdl.salesap.model.DaoSession;

/**
 * Created by hendra.dl on 20/08/2017.
 */

public class GreenDaoHelper {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static SQLiteDatabase db;

    public GreenDaoHelper(Activity activity){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, GlobalConstant.DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoMaster getDaoMaster(Activity activity){
        if(daoMaster==null){
            new GreenDaoHelper(activity);
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Activity activity) {
        if(daoSession==null){
            new GreenDaoHelper(activity);
        }
        return daoSession;
    }

    public static void clear(Activity activity){
        if(daoSession!=null) {
            daoSession.clear();
            daoSession=null;
        }
    }

    public static void clearAll(Activity activity) {
        if(daoSession!=null) {
            daoSession.clear();
            daoSession=null;
            db.close();
        }
    }

}
