package com.project.hdl.salesap.main.sellingreport;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;

import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.resultpage.ResultPageActivity;
import com.project.hdl.salesap.main.tasklist.TaskListActivity;
import com.project.hdl.salesap.model.webservice.SellingReportRequest;
import com.project.hdl.salesap.model.webservice.SellingReportResponse;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

/**
 * Created by hendra.dl on 01/09/2017.
 */

public class SellingReportTask extends AsyncTask<String, Void, SellingReportResponse> {
    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;
    String resultStatus;
    String resultMessage;


    public SellingReportTask(Activity activity){
        this.activity = activity;
        this.messageWait = activity.getString(R.string.message_please_wait);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        materialDialog = new MaterialDialog.Builder(this.activity)
                .content(R.string.message_please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }

    @Override
    protected SellingReportResponse doInBackground(String... args) {
        SellingReportResponse sellingReportResponse = new SellingReportResponse();;

        if(NetworkingTool.isInternetConnected(activity)) {
            String jsonSellingReportResponse = WebServiceHelper.postMessage(GlobalURL.GET_SELLING_REPORT_URL(), args[0], null);
            if (jsonSellingReportResponse != null) {
                sellingReportResponse = GsonHelper.fromJson(jsonSellingReportResponse, SellingReportResponse.class);
            }else{
                sellingReportResponse.setStatus(GlobalConstant.FAILED);
                sellingReportResponse.setErrorMessage(activity.getString(R.string.message_request_failed));
            }
        }else{
            sellingReportResponse.setStatus(GlobalConstant.FAILED);
            sellingReportResponse.setErrorMessage(activity.getString(R.string.message_connection_failed));
        }
        return sellingReportResponse;
    }

    @Override
    protected void onPostExecute(SellingReportResponse result) {
        super.onPostExecute(result);
        if (this.materialDialog.isShowing()){
            this.materialDialog.dismiss();
        }
        resultStatus = result.getStatus();
        resultMessage = result.getErrorMessage();

        SellingReportActivity.sellingReportTask = null;

        gotoResultPage();
    }

    private void gotoResultPage(){
        Bundle bundle = new Bundle();
        bundle.putString(ResultPageActivity.RESULT_STATUS_KEY, resultStatus);
        bundle.putString(ResultPageActivity.RESULT_MESSAGE_KEY, resultMessage);
        bundle.putString(ResultPageActivity.UID_TASK_KEY, SellingReportActivity.uidTask);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        stackBuilder.addParentStack(ResultPageActivity.class);
        ApplicationTool.goTo(activity, ResultPageActivity.class, stackBuilder, bundle);
    }
}
