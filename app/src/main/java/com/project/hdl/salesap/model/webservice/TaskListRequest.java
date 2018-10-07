package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.tool.networking.BaseRequest;

/**
 * Created by hendra.dl on 29/08/2017.
 */

public class TaskListRequest extends BaseRequest{
    @SerializedName("last_dtm_upd")
    private String lastDtmUpd;

    public String getLastDtmUpd() {
        return lastDtmUpd;
    }

    public void setLastDtmUpd(String lastDtmUpd) {
        this.lastDtmUpd = lastDtmUpd;
    }
}
