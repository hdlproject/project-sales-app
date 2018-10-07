package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.model.ReportD;
import com.project.hdl.salesap.model.ReportH;
import com.project.hdl.salesap.tool.networking.BaseRequest;

import java.util.List;

/**
 * Created by hendra.dl on 28/08/2017.
 */

public class SellingReportRequest extends BaseRequest{
    @SerializedName("report_h")
    ReportH reportH;
    @SerializedName("list_of_report_d")
    List<ReportD> reportDList;

    public ReportH getReportH() {
        return reportH;
    }

    public void setReportH(ReportH reportH) {
        this.reportH = reportH;
    }

    public List<ReportD> getReportDList() {
        return reportDList;
    }

    public void setReportDList(List<ReportD> reportDList) {
        this.reportDList = reportDList;
    }
}
