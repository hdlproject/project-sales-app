package com.project.hdl.salesap.main.waypoints;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.main.login.LoginActivity;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.model.custom.Leg;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.networking.NetworkingTool;
import com.project.hdl.salesap.tool.networking.WebServiceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class WaypointsTask extends AsyncTask<String, Void, List<Leg>> {
    Activity activity;
    String messageWait;
    MaterialDialog materialDialog;

    public WaypointsTask(Activity activity){
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
    protected List<Leg> doInBackground(String... args) {
        if(NetworkingTool.isInternetConnected(activity)) {
            String jsonWaypointResponse = WebServiceHelper.getMessage(args[0], null);
            try {
                JSONObject jsonObject = new JSONObject(jsonWaypointResponse);
                JSONArray jsonRoutes = jsonObject.getJSONArray("routes");
                JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
                JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                List<Leg> legList = new ArrayList<>();
                for(int i=0; i<jsonLegs.length(); i++){
                    Leg leg = GsonHelper.fromJson(jsonLegs.getJSONObject(i).toString(), Leg.class);
                    legList.add(leg);
                }
                return legList;
            } catch (JSONException je) {
                je.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Leg> result) {
        super.onPostExecute(result);
        if (this.materialDialog.isShowing()){
            this.materialDialog.dismiss();
        }
        WaypointsActivity.waypointsTask = null;

        int duration = 0;
        int distance = 0;
        if(result != null){
            for(Leg leg : result){
                duration = duration + leg.getDuration().getValue();
                distance = distance + leg.getDistance().getValue();
            }
        }
        new MaterialDialog.Builder(activity)
                .title(R.string.label_info)
                .content(R.string.message_waypoints_notification, duration, distance)
                .positiveText(R.string.label_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ApplicationTool.goTo(activity, WelcomeActivity.class);
                    }
                })
                .show();
    }
}
