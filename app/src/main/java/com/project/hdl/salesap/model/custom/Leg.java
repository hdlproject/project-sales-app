package com.project.hdl.salesap.model.custom;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendra.dl on 10/09/2017.
 */

public class Leg {
    @SerializedName("distance")
    private Distance distance;
    @SerializedName("duration")
    private Duration duration;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
