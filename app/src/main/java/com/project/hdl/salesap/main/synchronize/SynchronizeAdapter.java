package com.project.hdl.salesap.main.synchronize;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.model.webservice.UserRequest;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hendra.dl on 04/09/2017.
 */

public class SynchronizeAdapter extends ArrayAdapter<String>{
    private Activity activity;
    private List<String> synchronizeList;
    public static HashMap<String, String> labelStatusMap;

    public SynchronizeAdapter(Activity activity, int resource, int textViewResourceId, List<String> objects) {
        super(activity, resource, textViewResourceId, objects);
        this.activity = activity;
        this.synchronizeList = objects;
        this.labelStatusMap = new HashMap<>();
        initMap();
    }

    private void initMap(){
        for(String sync : synchronizeList){
            labelStatusMap.put(sync, activity.getString(R.string.label_wait_status));
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.synchronize_item, parent, false);
        TextView label = (TextView)view.findViewById(R.id.synchronizeLabel);
        TextView status = (TextView)view.findViewById(R.id.synchronizeStatus);
        label.setText(synchronizeList.get(position));
        status.setText(labelStatusMap.get(synchronizeList.get(position)));

        return view;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return synchronizeList.get(position);
    }

    @Override
    public int getCount() {
        return synchronizeList.size();
    }
}
