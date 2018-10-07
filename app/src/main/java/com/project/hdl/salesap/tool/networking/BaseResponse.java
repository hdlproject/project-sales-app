package com.project.hdl.salesap.tool.networking;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendra.dl on 22/08/2017.
 */

public class BaseResponse {
    @SerializedName("status")
    String status;
    @SerializedName("error_message")
    String errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
