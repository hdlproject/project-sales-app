package com.project.hdl.salesap.model.dao;

import android.app.Activity;
import android.content.Context;

import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.model.DaoSession;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.TaskDao;
import com.project.hdl.salesap.tool.database.GreenDaoHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class TaskDataAccess {
    protected static DaoSession getDaoSession(Activity activity){
        return GreenDaoHelper.getDaoSession(activity);
    }

    protected static TaskDao getTaskDao(Activity activity){
        return getDaoSession(activity).getTaskDao();
    }

    public static void addOrReplace(Activity activity, Task task){
        getTaskDao(activity).insertOrReplaceInTx(task);
        getDaoSession(activity).clear();
    }

    public static void addOrReplace(Activity activity, List<Task> taskList){
        getTaskDao(activity).insertOrReplaceInTx(taskList);
        getDaoSession(activity).clear();
    }

    public static List<Task> getAllByUidUserAndDate(Activity activity, String uidUser, Date startDate, Date endDate) {
        QueryBuilder<Task> qb = getTaskDao(activity).queryBuilder();
        qb.where(TaskDao.Properties.Login_id.eq(uidUser),
                TaskDao.Properties.Dtm_assign_tmp.between(startDate, endDate));
        qb.build();
        return qb.list();
    }

    public static List<Task> getAllByUidUser(Activity activity, String uidUser) {
        QueryBuilder<Task> qb = getTaskDao(activity).queryBuilder();
        qb.where(TaskDao.Properties.Login_id.eq(uidUser));
        qb.build();
        return qb.list();
    }

    public static List<Task> getAll(Activity activity) {
        QueryBuilder<Task> qb = getTaskDao(activity).queryBuilder();
        qb.whereOr(TaskDao.Properties.Status.notEq(GlobalConstant.TASK_FINISHED),
                TaskDao.Properties.Status.isNull());
        qb.build();
        return qb.list();
    }

    public static List<Task> getAllWithRownum(Activity activity, int rownum) {
        QueryBuilder<Task> qb = getTaskDao(activity).queryBuilder();
        qb.whereOr(TaskDao.Properties.Status.notEq(GlobalConstant.TASK_FINISHED),
                TaskDao.Properties.Status.isNull());
        qb.orderAsc(TaskDao.Properties.Dtm_assign);
        qb.limit(rownum);
        qb.build();
        return qb.list();
    }

    public static Task getOneByUid(Activity activity, String uidTask) {
        QueryBuilder<Task> qb = getTaskDao(activity).queryBuilder();
        qb.where(TaskDao.Properties.Uid_task.eq(uidTask));
        qb.build();
        return qb.list().get(0);
    }
}
