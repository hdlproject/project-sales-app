package com.project.hdl.salesap.tool.networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class BaseRequest {
    @SerializedName("login_id")
    String loginId;

    @SerializedName("imei")
    String imei;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}