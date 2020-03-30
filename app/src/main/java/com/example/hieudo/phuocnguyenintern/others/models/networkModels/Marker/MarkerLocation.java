package com.example.hieudo.phuocnguyenintern.others.models.networkModels.Marker;

public class MarkerLocation {
    private String markerName;
    private String id;
    private double latitude;
    private double longitude;


    public MarkerLocation(String markerName, String id, double latitude, double longitude) {
        this.markerName = markerName;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MarkerLocation() {
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
