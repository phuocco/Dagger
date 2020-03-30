package com.example.hieudo.phuocnguyenintern.ui.mapScreen;

import android.location.Location;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Direction.DirectionExample;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Marker.MarkerLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Polyline.PolylineLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.googleMap.GoogleMapExample;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface MapActivityContract {
    interface View {
        void showPlaces(List<AutocompletePrediction> predictionList);
        void toast(String content);
        void moveCamera(LatLng place);
        void showMarker(GoogleMapExample listMarker);
        void showAddedMarker(LatLng latLng, String markerName);
        void showFetchedMarker(MarkerLocation markerLocation);
        void showUpdatedMarker(Marker marker, String markerName);
        void showDeviceLocation(Location location);
        void showPolyline(DirectionExample directionExample);
        void showFetchedPlace(String endLat, String endLng);
    }

    interface Presenter {
        void placesClient(PlacesClient placesClient, FindAutocompletePredictionsRequest predictionsRequest);
        void fetchPlaces(PlacesClient placesClient, FetchPlaceRequest fetchPlaceRequest, Location mLastKnownLocation);
        void getDeviceLocation(FusedLocationProviderClient mFusedLocationProviderClient);
        void getResponse(String type, double latitude, double longitude);
        void addMarker(LatLng latLng, String markerName);
        void fetchMarker(DatabaseReference markerDatabase);
        void updateMarker(MarkerLocation markerLocation, DatabaseReference markerRef, Marker marker);
        void getPolyline(String origin, String destination);
        void addPolylineToFirebase(DatabaseReference polylineDatabase, PolylineLocation polylineLocation);
    }
}
