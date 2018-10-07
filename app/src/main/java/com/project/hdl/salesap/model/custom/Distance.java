package com.project.hdl.salesap.model.custom;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class Distance {
    @SerializedName("text")
    private String text;
    @SerializedName("value")
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
