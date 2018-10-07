package com.project.hdl.salesap.main.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.model.webservice.ChangePasswordRequest;
import com.project.hdl.salesap.tool.database.GsonHelper;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    //layout component
    Button changePasswordButton;
    EditText oldPasswordText, newPasswordText, confNewPasswordText;

    Activity activity;
    public static ChangePasswordTask changePasswordTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        activity = this;

        //init layout component
        oldPasswordText = (EditText) findViewById(R.id.oldPasswordText);
        newPasswordText = (EditText) findViewById(R.id.newPasswordText);
        confNewPasswordText = (EditText) findViewById(R.id.confNewPasswordText);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOldPasswordValid()){
                    if(isNewPasswordConfirmed()){
                        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                        changePasswordRequest.setLoginId(SharedPreferencesHelper.getString(activity, GlobalConstant.LOGIN_ID));
                        changePasswordRequest.setImei("");
                        changePasswordRequest.setPassword(newPasswordText.getText().toString());
                        String jsonChangePasswordRequest = GsonHelper.toJson(changePasswordRequest);
                        changePasswordTask.execute(jsonChangePasswordRequest);
                    }
                }
            }
        });

        changePasswordTask = new ChangePasswordTask(activity);
    }

    private boolean isOldPasswordValid(){
        if(SharedPreferencesHelper.getString(activity, GlobalConstant.PASSWORD)
                .equals(oldPasswordText.getText().toString())){
            return true;
        }else{
            return false;
        }
    }

    private boolean isNewPasswordConfirmed(){
        if(newPasswordText.getText().toString().equals(confNewPasswordText.getText().toString())){
            return true;
        }else{
            return false;
        }
    }
}
