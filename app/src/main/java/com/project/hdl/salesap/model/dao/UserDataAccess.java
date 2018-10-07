package com.project.hdl.salesap.model.dao;

import android.app.Activity;
import android.content.Context;

import com.project.hdl.salesap.model.DaoSession;
import com.project.hdl.salesap.model.User;
import com.project.hdl.salesap.model.UserDao;
import com.project.hdl.salesap.tool.database.GreenDaoHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by hendra.dl on 04/09/2017.
 */

public class UserDataAccess {
    protected static DaoSession getDaoSession(Activity activity){
        return GreenDaoHelper.getDaoSession(activity);
    }

    protected static UserDao getUserDao(Activity activity){
        return getDaoSession(activity).getUserDao();
    }

    public static void addOrReplace(Activity activity, User user){
        getUserDao(activity).insertOrReplaceInTx(user);
        getDaoSession(activity).clear();
    }

    public static User getOne (Activity activity) {
        QueryBuilder<User> qb = getUserDao(activity).queryBuilder();
        qb.build();
        return qb.list().get(0);
    }
}
