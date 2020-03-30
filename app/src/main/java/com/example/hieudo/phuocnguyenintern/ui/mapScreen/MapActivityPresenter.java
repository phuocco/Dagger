package com.example.hieudo.phuocnguyenintern.ui.mapScreen;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Direction.DirectionExample;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Marker.MarkerLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Polyline.PolylineLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.googleMap.GoogleMapExample;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.DirectionService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.GoogleMapService;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivityPresenter implements MapActivityContract.Presenter {

    private MapActivityContract.View view;

    public void setView(MapActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void placesClient(PlacesClient placesClient, FindAutocompletePredictionsRequest predictionsRequest) {
        placesClient.findAutocompletePredictions(predictionsRequest)
                .addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.getResult() != null) {
                            view.showPlaces(task.getResult().getAutocompletePredictions());
                        } else {
                            view.toast("Fetching fail");
                        }
                    }
                });
    }

    @Override
    public void fetchPlaces(PlacesClient placesClient, FetchPlaceRequest fetchPlaceRequest, Location mLastKnownLocation) {
        placesClient.fetchPlace(fetchPlaceRequest)
                .addOnSuccessListener(fetchPlaceResponse -> {
                    view.moveCamera(fetchPlaceResponse.getPlace().getLatLng());
                    view.showFetchedPlace(String.valueOf(fetchPlaceResponse.getPlace().getLatLng().latitude), (String.valueOf(fetchPlaceResponse.getPlace().getLatLng().longitude)));
                    String destination = String.valueOf(fetchPlaceResponse.getPlace().getLatLng().latitude).concat(",").concat(String.valueOf(fetchPlaceResponse.getPlace().getLatLng().longitude));
                    String origin = mLastKnownLocation.getLatitude() + "," + mLastKnownLocation.getLongitude();
                    Log.e(Constants.TAG,origin);
                    Log.e(Constants.TAG,String.valueOf(fetchPlaceResponse.getPlace().getLatLng().latitude).concat(",").concat(String.valueOf(fetchPlaceResponse.getPlace().getLatLng().longitude)));
                    getPolyline(origin,destination);
                }).addOnFailureListener(e -> {
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                apiException.printStackTrace();
                int statusCode = apiException.getStatusCode();
                Log.i(Constants.TAG, "Place not found: " + e.getMessage());
                Log.i(Constants.TAG, "Status code: " + statusCode);
            }
        });
    }

    @Override
    public void getPolyline(String origin, String destination) {
        String urlDirection = "https://maps.googleapis.com/";
        Retrofit retrofitDirection = new Retrofit.Builder()
                .baseUrl(urlDirection)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DirectionService directionService = retrofitDirection.create(DirectionService.class);
        Call<DirectionExample> call = directionService.getMapDirection(origin, destination);
        call.enqueue(new Callback<DirectionExample>() {
            @Override
            public void onResponse(Call<DirectionExample> call, Response<DirectionExample> response) {
                if (response.isSuccessful()){
                    view.showPolyline(response.body());
                }
            }

            @Override
            public void onFailure(Call<DirectionExample> call, Throwable t) {

            }
        });

    }

    @Override
    public void addPolylineToFirebase(DatabaseReference polylineDatabase, PolylineLocation polylineLocation) {
        String polylineID = polylineDatabase.push().getKey();
        assert polylineID != null;
        polylineDatabase.child(polylineID).setValue(polylineLocation);
    }

    @Override
    public void getDeviceLocation(FusedLocationProviderClient mFusedLocationProviderClient) {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            view.moveCamera(new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude()));
                            view.showDeviceLocation(task.getResult());
                        } else {
                            LocationRequest locationRequest = LocationRequest.create();
                            locationRequest.setInterval(10000);
                            locationRequest.setFastestInterval(5000);
                            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    if (locationResult == null) {
                                        return;
                                    }
                                    view.moveCamera(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()));

                                }
                            };

                            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                        }
                    } else {
                        view.toast("Unable to get last location");
                    }
                });
    }

    @Override
    public void getResponse(String type, double latitude, double longitude) {
        String url = "https://maps.googleapis.com/maps/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleMapService googleMapService = retrofit.create(GoogleMapService.class);
        Call<GoogleMapExample> call = googleMapService.getNearbyPlaces(type, latitude + "," + longitude, Constants.PROXIMITY_RADIUS);

        call.enqueue(new Callback<GoogleMapExample>() {
            @Override
            public void onResponse(Call<GoogleMapExample> call, Response<GoogleMapExample> response) {
                if (response.isSuccessful()) {
                    view.showMarker(response.body());
                }
            }

            @Override
            public void onFailure(Call<GoogleMapExample> call, Throwable t) {
                view.toast("Network error!");
            }
        });


    }

    @Override
    public void addMarker(LatLng latLng, String markerName) {
        view.showAddedMarker(latLng, markerName);
    }

    @Override
    public void fetchMarker(DatabaseReference markerDatabase) {
        markerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    view.showFetchedMarker(postSnapshot.getValue(MarkerLocation.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void updateMarker(MarkerLocation markerLocation, DatabaseReference markerRef, Marker marker) {
        markerRef.setValue(markerLocation);
        view.showUpdatedMarker(marker,markerLocation.getMarkerName());

    }



}
