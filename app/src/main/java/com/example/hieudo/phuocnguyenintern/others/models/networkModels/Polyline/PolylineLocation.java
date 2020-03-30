package com.example.hieudo.phuocnguyenintern.others.models.networkModels.Polyline;

public class PolylineLocation {
    private String id;
    private String startLat;
    private String startLng;
    private String endLat;
    private String endLng;

    public PolylineLocation(String id, String startLat, String startLng, String endLat, String endLng) {
        this.id = id;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLng() {
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLng() {
        return endLng;
    }

    public void setEndLng(String endLng) {
        this.endLng = endLng;
    }
}
