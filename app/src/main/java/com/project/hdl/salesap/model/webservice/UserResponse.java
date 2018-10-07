package com.project.hdl.salesap.model.webservice;

import com.google.gson.annotations.SerializedName;
import com.project.hdl.salesap.model.User;
import com.project.hdl.salesap.tool.networking.BaseResponse;

/**
 * Created by hendra.dl on 04/09/2017.
 */

public class UserResponse extends BaseResponse {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
