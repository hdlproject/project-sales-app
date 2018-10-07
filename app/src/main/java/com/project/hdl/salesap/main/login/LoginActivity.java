package com.project.hdl.salesap.main.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.project.hdl.salesap.R;
import com.project.hdl.salesap.global.GlobalConstant;
import com.project.hdl.salesap.main.welcome.WelcomeActivity;
import com.project.hdl.salesap.tool.application.ApplicationTool;
import com.project.hdl.salesap.tool.database.SharedPreferencesHelper;


/**
 * Created by hendra.dl on 20/08/2017.
 */

public class LoginActivity extends AppCompatActivity {

    //declare layout component
    EditText loginIdText, passwordText;
    CheckBox showPasswordCheck;
    Button loginButton, exitButton;
    TextView signupLabel;

    Activity activity;
    public static LoginTask loginTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        activity = this;

        //initiate layout component
        loginIdText = (EditText) findViewById(R.id.loginIdText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        showPasswordCheck = (CheckBox) findViewById(R.id.showPasswordCheck);
        loginButton = (Button) findViewById(R.id.loginButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        signupLabel = (TextView) findViewById(R.id.signupLabel);

        //bypass login if already logged
        if(checkLoggedIn()){
            ApplicationTool.goTo(activity, WelcomeActivity.class);
        }

        loginTask = new LoginTask(this);

        showPasswordCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordText.setTransformationMethod(null);
                } else {
                    passwordText.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        //on click action
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                doLogin();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                new MaterialDialog.Builder(LoginActivity.this)
                        .title(R.string.label_info)
                        .content(R.string.message_exit_alert)
                        .positiveText(R.string.label_yes)
                        .negativeText(R.string.label_no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                doExit();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        signupLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignup();
            }
        });

    }

    private void doExit() {
        System.exit(0);
    }

    private void doLogin() {
        if(loginTask == null){
            loginTask = new LoginTask(this);
        }
        loginTask.execute(loginIdText.getText().toString(), passwordText.getText().toString());
    }

    private void doSignup() {
        //coming soon
    }

    private boolean checkLoggedIn(){
        return SharedPreferencesHelper.getBoolean(activity, GlobalConstant.LOGGED_IN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //minimize
        moveTaskToBack(true);
    }
}
