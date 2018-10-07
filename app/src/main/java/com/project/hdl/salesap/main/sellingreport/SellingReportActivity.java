package com.project.hdl.salesap.main.sellingreport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.vision.barcode.Barcode;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.global.GlobalData;
import com.project.hdl.salesap.global.GlobalURL;
import com.project.hdl.salesap.main.barcodescanner.BarcodeScannerActivity;
import com.project.hdl.salesap.main.maps.MapsActivity;
import com.project.hdl.salesap.model.ReportD;
import com.project.hdl.salesap.model.ReportH;
import com.project.hdl.salesap.model.Task;
import com.project.hdl.salesap.model.dao.ReportDDataAccess;
import com.project.hdl.salesap.model.dao.ReportHDataAccess;
import com.project.hdl.salesap.model.dao.TaskDataAccess;
import com.project.hdl.salesap.model.webservice.SellingReportRequest;
import com.project.hdl.salesap.model.webservice.SellingReportResponse;
import com.project.hdl.salesap.service.RequestDataScheduler;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.application.LocationHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;
import com.project.hdl.salesap.tool.database.DatabaseTool;
import com.project.hdl.salesap.tool.database.GsonHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hendra.dl on 26/08/2017.
 */

public class SellingReportActivity extends AppCompatActivity implements View.OnClickListener {

    //constant
    public static final String UID_TASK_KEY = "UID_TASK";
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    //layout component
    SellingReportAdapter sellingReportAdapter;
    RecyclerView recyclerView;
    ImageView addListImage, deleteAllListImage, submitImage;
    public static EditText totalCountText, totalPriceText;

    Activity activity;
    public static String uidTask;
    ReportH reportH;
    public static List<ReportD> reportDList;
    private static long totalPrice;
    private static int totalCount;
    public static SellingReportTask sellingReportTask;
    Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_report_activity);
        activity = this;

        //initiate layout component
        addListImage = (ImageView) findViewById(R.id.addListImage);
        submitImage = (ImageView) findViewById(R.id.submitImage);
        deleteAllListImage = (ImageView) findViewById(R.id.deleteAllListImage);
        totalCountText = (EditText) findViewById(R.id.totalCountText);
        totalPriceText = (EditText) findViewById(R.id.totalPriceText);
        addListImage.setOnClickListener(this);
        submitImage.setOnClickListener(this);
        deleteAllListImage.setOnClickListener(this);
        sellingReportAdapter = new SellingReportAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.sellingReportListAdapter);

        totalCount = 0;
        totalPrice = 0;
        totalCountText.setText(Integer.toString(totalCount));
        totalPriceText.setText(Long.toString(totalPrice));

        uidTask = getIntent().getExtras().getString(UID_TASK_KEY);

        initList();
        addListItem();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sellingReportAdapter);

        sellingReportTask = new SellingReportTask(this);
        if(!RequestDataScheduler.isTaskReqSchedulerPause()){
            RequestDataScheduler.pauseTaskReqScheduler();
        }
    }

    @Override
    public void onClick(View view) {
        int button = view.getId();
        switch (button) {
            case R.id.addListImage:
                addListItem();
                break;
            case R.id.deleteAllListImage:
                new MaterialDialog.Builder(SellingReportActivity.this)
                        .title(R.string.label_info)
                        .content(R.string.message_delete_all_alert)
                        .positiveText(R.string.label_yes)
                        .negativeText(R.string.label_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                deleteAllListItem();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.submitImage:
                if (isLocationValid()) {
                    barcodeValidator();
                }else{
                    new MaterialDialog.Builder(SellingReportActivity.this)
                            .title(R.string.label_info)
                            .content(R.string.message_loc_not_valid_alert)
                            .positiveText(R.string.label_ok)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                break;
        }
    }

    private void initList() {
        reportDList = new ArrayList<>();
        reportH = new ReportH();
        reportH.setUid_task(uidTask);
        reportH.setUid_report_h(DatabaseTool.getUID());
    }

    private void addListItem() {
        ReportD initReportD = new ReportD();
        initReportD.setUid_report_d(DatabaseTool.getUID());
        initReportD.setUid_report_h(reportH.getUid_report_h());
        reportDList.add(reportDList.size(), initReportD);
        sellingReportAdapter.notifyDataSetChanged();

        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(sellingReportAdapter.getItemCount() - 1);
        }
    }

    private void deleteAllListItem() {
        reportDList.clear();
        sellingReportAdapter.notifyDataSetChanged();
        recalculateTotalOnThread();
    }

    public static void recalculateTotal() {
        totalCount = 0;
        totalPrice = 0;
        int tmpCount;
        int tmpPrice;
        for (ReportD reportD : SellingReportActivity.reportDList) {
            if ("".equalsIgnoreCase(reportD.getProduct_count()) || reportD.getProduct_count() == null) {
                tmpCount = 0;
            } else {
                tmpCount = Integer.valueOf(reportD.getProduct_count());
            }
            if ("".equalsIgnoreCase(reportD.getProduct_price()) || reportD.getProduct_price() == null) {
                tmpPrice = 0;
            } else {
                tmpPrice = Integer.valueOf(reportD.getProduct_price());
            }
            totalCount = totalCount + tmpCount;
            totalPrice = totalPrice + (tmpCount * tmpPrice);
        }
        totalCountText.setText(Integer.toString(totalCount));
        totalPriceText.setText(Long.toString(totalPrice));
    }

    public static void recalculateTotalOnThread() {
        //run new thread to calculate
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                totalPriceText.post(new Runnable() {
                    @Override
                    public void run() {
                        recalculateTotal();
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    private void doSave() {
        //save reportH
        reportH.setDtm_submit(DatabaseTool.getCurrentDtmMillis());
        reportH.setTotal_count(Integer.toString(totalCount));
        reportH.setTotal_price(Long.toString(totalPrice));
        ReportHDataAccess.addOrReplace(activity, reportH);

        //save reportD
        ReportDDataAccess.addOrReplace(activity, reportDList);
    }

    private void doSubmit() {
        SellingReportRequest sellingReportRequest = new SellingReportRequest();
        sellingReportRequest.setReportH(reportH);
        sellingReportRequest.setReportDList(reportDList);
        sellingReportRequest.setLoginId(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
        sellingReportRequest.setImei("");

        String jsonSellingReportRequest = GsonHelper.toJson(sellingReportRequest);

        if(sellingReportTask == null){
            sellingReportTask = new SellingReportTask(this);
        }
        sellingReportTask.execute(jsonSellingReportRequest);

    }

    private boolean isLocationValid() {
        location = LocationHelper.getLocation(activity);
        Double lat = getIntent().getExtras().getDouble(MapsActivity.LAT_MAP_KEY);
        Double lon = getIntent().getExtras().getDouble(MapsActivity.LON_MAP_KEY);

        float[] distance = new float[2];

//        if(location != null){
//            //get distance in meter
//            Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat, lon, distance );
//
//            if(distance[0] <= GlobalData.VALID_SUBMIT_DISTANCE){
//                return true;
//            }else{
//                return false;
//            }
//        }else{
//            return false;
//        }
        return true;
    }

    private void barcodeValidator(){
        // launch barcode activity.
        Intent intent = new Intent(this, BarcodeScannerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(BarcodeScannerActivity.AutoFocus, true);
        bundle.putBoolean(BarcodeScannerActivity.UseFlash, false);
        intent.putExtras(bundle);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeScannerActivity.BarcodeObject);
//                    statusMessage.setText(R.string.barcode_success);
//                    barcodeValue.setText(barcode.displayValue);
                    Task task = TaskDataAccess.getOneByUid(activity, uidTask);
                    if(barcode.displayValue.equalsIgnoreCase(task.getCust_name())){
                        doSave();
                        doSubmit();
                    }
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
//                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
//                statusMessage.setText(String.format(getString(R.string.barcode_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if(RequestDataScheduler.isTaskReqSchedulerPause()){
            RequestDataScheduler.continueTaskReqScheduler();
        }
        super.onBackPressed();
    }
}
