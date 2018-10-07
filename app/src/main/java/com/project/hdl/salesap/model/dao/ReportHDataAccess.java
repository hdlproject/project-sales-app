package com.project.hdl.salesap.model.dao;

import android.app.Activity;
import android.content.Context;

import com.project.hdl.salesap.model.DaoSession;
import com.project.hdl.salesap.model.ReportH;
import com.project.hdl.salesap.model.ReportHDao;
import com.project.hdl.salesap.tool.database.GreenDaoHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by hendra.dl on 28/08/2017.
 */

public class ReportHDataAccess {
    protected static DaoSession getDaoSession(Activity activity){
        return GreenDaoHelper.getDaoSession(activity);
    }

    protected static ReportHDao getReportHDao(Activity activity){
        return getDaoSession(activity).getReportHDao();
    }

    public static void addOrReplace(Activity activity, ReportH reportH){
        getReportHDao(activity).insertOrReplaceInTx(reportH);
        getDaoSession(activity).clear();
    }

    public static void addOrReplace(Activity activity, List<ReportH> reportHList){
        getReportHDao(activity).insertOrReplaceInTx(reportHList);
        getDaoSession(activity).clear();
    }

    public static List<ReportH> getAllByUidTask(Activity activity, String uidTask) {
        QueryBuilder<ReportH> qb = getReportHDao(activity).queryBuilder();
        qb.where(ReportHDao.Properties.Uid_report_h.eq(uidTask));
        qb.build();
        return qb.list();
    }

    public static List<ReportH> getAll(Activity activity) {
        QueryBuilder<ReportH> qb = getReportHDao(activity).queryBuilder();
        qb.build();
        return qb.list();
    }
}
