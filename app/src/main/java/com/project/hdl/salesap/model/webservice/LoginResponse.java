package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.tool.networking.BaseResponse;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("token")
    private String token;
    @SerializedName("synchronize_list")
    private String synchronize_list;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSynchronize_list() {
        return synchronize_list;
    }

    public void setSynchronize_list(String synchronize_list) {
        this.synchronize_list = synchronize_list;
    }
}
