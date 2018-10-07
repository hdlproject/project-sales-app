package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.tool.networking.BaseRequest;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class LoginRequest extends BaseRequest{
    @SerializedName("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
