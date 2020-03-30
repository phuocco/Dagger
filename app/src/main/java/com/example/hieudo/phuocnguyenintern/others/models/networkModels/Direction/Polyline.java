package com.example.hieudo.phuocnguyenintern.others.models.networkModels.Direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Polyline {
    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
