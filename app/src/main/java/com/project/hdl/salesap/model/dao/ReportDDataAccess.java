package com.project.hdl.salesap.model.dao;

import android.app.Activity;
import android.content.Context;

import com.project.hdl.salesap.model.DaoSession;
import com.project.hdl.salesap.model.ReportD;
import com.project.hdl.salesap.model.ReportDDao;
import com.project.hdl.salesap.tool.database.GreenDaoHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by hendra.dl on 28/08/2017.
 */

public class ReportDDataAccess {
    protected static DaoSession getDaoSession(Activity activity){
        return GreenDaoHelper.getDaoSession(activity);
    }

    protected static ReportDDao getReportDDao(Activity activity){
        return getDaoSession(activity).getReportDDao();
    }

    public static void addOrReplace(Activity activity, ReportD reportD){
        getReportDDao(activity).insertOrReplaceInTx(reportD);
        getDaoSession(activity).clear();
    }

    public static void addOrReplace(Activity activity, List<ReportD> reportDList){
        getReportDDao(activity).insertOrReplaceInTx(reportDList);
        getDaoSession(activity).clear();
    }

    public static List<ReportD> getAllByUidReportH(Activity activity, String uidReportH) {
        QueryBuilder<ReportD> qb = getReportDDao(activity).queryBuilder();
        qb.where(ReportDDao.Properties.Uid_report_h.eq(uidReportH));
        qb.build();
        return qb.list();
    }

    public static List<ReportD> getAll(Activity activity) {
        QueryBuilder<ReportD> qb = getReportDDao(activity).queryBuilder();
        qb.build();
        return qb.list();
    }
}
