package com.example.hieudo.phuocnguyenintern.ui.mapScreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Direction.DirectionExample;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Marker.MarkerLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Polyline.PolylineLocation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.googleMap.GoogleMapExample;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity implements MapActivityContract.View, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;

    private Map<String, Marker> listMarker = new HashMap<>();
    private AutocompleteSessionToken token;
    private Location mLastKnownLocation;

    private static final String TAG = "Google Map";
    MarkerOptions markerOptions;
    MarkerLocation markerLocation;
    PolylineLocation polylineLocation;
    private String markerID, polylineID;
    private View mapView;
    private String markerName;

    MapActivityPresenter presenter;

    DatabaseReference RootRef, markerDatabase, polylineDatabase;
    @BindView(R.id.searchBar)
    MaterialSearchBar materialSearchBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);
        Places.initialize(MapActivity.this, getString(R.string.google_map_api));
        placesClient = Places.createClient(this);

        initPresenter();
        initFirebase();
        searchBar();
    }

    private void searchBar() {
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    // navigation
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.closeSearch();
                }
            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("vn")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();
                presenter.placesClient(placesClient, predictionsRequest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {


                if (position >= predictionList.size()) {
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);
                new Handler().postDelayed(() -> materialSearchBar.clearSuggestions(), 1500);
                String placeID = selectedPrediction.getPlaceId();
                List<Place.Field> placeField = Arrays.asList(Place.Field.LAT_LNG);
                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeID, placeField).build();
                presenter.fetchPlaces(placesClient, fetchPlaceRequest, mLastKnownLocation);
            }
            @Override
            public void OnItemDeleteListener(int position, View v) {
            }
        });
    }

    private void addPolylineToFirebase(String  startLat, String startLng, String endLat, String endLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        polylineLocation = new PolylineLocation(polylineID, startLat, startLng, endLat, endLng);
        builder.setTitle("Add Polyline to firebase")
                .setMessage("Are you sure?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            presenter.addPolylineToFirebase(polylineDatabase,polylineLocation);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    private void initFirebase() {
        //firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        RootRef = firebaseDatabase.getReference();
        markerDatabase = RootRef.child("MarkerLocation");
        polylineDatabase = RootRef.child("PolylineDatabase");
        token = AutocompleteSessionToken.newInstance();
    }

    private void initPresenter() {
        presenter = new MapActivityPresenter();
        presenter.setView(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        {
            for (Map.Entry<String, Marker> entry : listMarker.entrySet()) {
                if (listMarker.containsValue(marker)) {
                    markerID = entry.getKey();
                }
            }
        }
        DatabaseReference markerRef = markerDatabase.child(markerID);
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("Add location")
                .setMessage("Insert name");
        View view = getLayoutInflater().inflate(R.layout.dialog_marker, null);
        builder.setView(view);
        final EditText editText = view.findViewById(R.id.markerDialog_name);
        editText.setText(marker.getTitle());
        builder.setPositiveButton("OK", (dialog, which) -> {
            markerLocation = new MarkerLocation(editText.getText().toString(), markerID, marker.getPosition().latitude, marker.getPosition().longitude);
            presenter.updateMarker(markerLocation, markerRef, marker);


        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle("Add location")
                .setMessage("Insert name");
        View view = getLayoutInflater().inflate(R.layout.dialog_marker, null);
        builder.setView(view);
        final EditText editText = (EditText) view.findViewById(R.id.markerDialog_name);
        builder.setPositiveButton("OK", (dialog, which) -> {
            markerName = editText.getText().toString();
            presenter.addMarker(latLng, markerName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        fetchData();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(MapActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(MapActivity.this, locationSettingsResponse ->
                presenter.getDeviceLocation(mFusedLocationProviderClient)
        );

        task.addOnFailureListener(MapActivity.this, e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                try {
                    resolvableApiException.startResolutionForResult(MapActivity.this, 50);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mMap.setOnMyLocationButtonClickListener(() -> {
            if (materialSearchBar.isSuggestionsVisible()) {
                materialSearchBar.clearSuggestions();
            }
            if (materialSearchBar.isSearchOpened()) {
                materialSearchBar.closeSearch();
            }
            return false;
        });
    }

    private void fetchData() {
        presenter.fetchMarker(markerDatabase);
    }

    @Override
    public void showPlaces(List<AutocompletePrediction> predictionsList) {
        predictionList = predictionsList;
        List<String> suggestionsList = new ArrayList<>();
        for (int i = 0; i < predictionList.size(); i++) {
            AutocompletePrediction prediction = predictionList.get(i);
            suggestionsList.add(prediction.getFullText(null).toString());
        }
        materialSearchBar.updateLastSuggestions(suggestionsList);
        if (!materialSearchBar.isSuggestionsVisible()) {
            materialSearchBar.showSuggestionsList();
        }
    }

    @Override
    public void toast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveCamera(LatLng place) {
        //search range
        float DEFAULT_ZOOM = 18;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, DEFAULT_ZOOM));
    }

    @Override
    public void showMarker(GoogleMapExample listMarker) {
        mMap.clear();
        for (int i = 0; i < listMarker.getResults().size(); i++) {
            Double lat = listMarker.getResults().get(i).getGeometry().getLocation().getLat();
            Double lng = listMarker.getResults().get(i).getGeometry().getLocation().getLng();
            String placeName = listMarker.getResults().get(i).getName();
            String vicinity = listMarker.getResults().get(i).getVicinity();
            LatLng latLng = new LatLng(lat, lng);
            Log.e(TAG, placeName);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(placeName + ": " + vicinity));
        }
    }

    @Override
    public void showAddedMarker(LatLng latLng, String markerName) {
        markerOptions = new MarkerOptions()
                .position(latLng)
                // .icon(BitmapDescriptorFactory.fromResource(R.drawable.answers_with_accepted))
                .draggable(true)
                .title(markerName);
        mMap.addMarker(markerOptions);
        markerID = markerDatabase.push().getKey();
        listMarker.put(markerID, mMap.addMarker(markerOptions));
        markerLocation = new MarkerLocation(markerName, markerID, latLng.latitude, latLng.longitude);
        markerDatabase.child(markerID).setValue(markerLocation);
    }

    @Override
    public void showFetchedMarker(MarkerLocation markerLocation) {
        LatLng markerLatLng = new LatLng(markerLocation.getLatitude(), markerLocation.getLongitude());
        markerOptions = new MarkerOptions()
                .position(markerLatLng)
                .title(markerLocation.getMarkerName());
        listMarker.put(markerLocation.getId(), mMap.addMarker(markerOptions));
    }

    @Override
    public void showUpdatedMarker(Marker marker, String markerName) {
        marker.setTitle(markerName);
    }

    @Override
    public void showDeviceLocation(Location location) {
        mLastKnownLocation = location;
    }

    @Override
    public void showPolyline(DirectionExample directionExample) {
        mMap.clear();
        ArrayList<LatLng> listSteps = new ArrayList<>();
        int sizeSteps = directionExample.getRoutes().get(0).getLegs().get(0).getSteps().size();
        for (int i = 0; i < sizeSteps; i++) {
            double startLat = directionExample.getRoutes().get(0).getLegs().get(0).getSteps().get(i).getStartLocation().getLat();
            double startLng = directionExample.getRoutes().get(0).getLegs().get(0).getSteps().get(i).getStartLocation().getLng();
            double endLat = directionExample.getRoutes().get(0).getLegs().get(0).getSteps().get(i).getEndLocation().getLat();
            double endLng = directionExample.getRoutes().get(0).getLegs().get(0).getSteps().get(i).getEndLocation().getLng();
            LatLng endLatLng = new LatLng(endLat, endLng);
            LatLng startLatLng = new LatLng(startLat, startLng);
            listSteps.add(startLatLng);
            listSteps.add(endLatLng);
            Polyline polyline = mMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(startLatLng, endLatLng)
                    .color(Color.RED));
            polyline.setStartCap(new RoundCap());
        }
        String endTitle = directionExample.getRoutes().get(0).getLegs().get(0).getEndAddress();
        double endLat = directionExample.getRoutes().get(0).getLegs().get(0).getEndLocation().getLat();
        double endLng = directionExample.getRoutes().get(0).getLegs().get(0).getEndLocation().getLng();
        String startTitle = directionExample.getRoutes().get(0).getLegs().get(0).getEndAddress();
        double startLat = directionExample.getRoutes().get(0).getLegs().get(0).getEndLocation().getLat();
        double startLng = directionExample.getRoutes().get(0).getLegs().get(0).getEndLocation().getLng();

        MarkerOptions markerOptionsEnd = new MarkerOptions().position(new LatLng(endLat, endLng)).title(endTitle);
        MarkerOptions markerOptionsStart = new MarkerOptions().position(new LatLng(startLat, startLng)).title(startTitle);

        mMap.addMarker(markerOptionsEnd);
        mMap.addMarker(markerOptionsStart);
        moveCamera(new LatLng(endLat,endLng));
    }

    @Override
    public void showFetchedPlace(String endFetchedLat, String endFetchedLng) {
        addPolylineToFirebase(String.valueOf(mLastKnownLocation.getLatitude()),String.valueOf(mLastKnownLocation.getLongitude()),endFetchedLat,endFetchedLng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == RESULT_OK) {
                presenter.getDeviceLocation(mFusedLocationProviderClient);
            }
        }
    }

    @OnClick(R.id.googleMap_btnFind)
    void onClick(View view) {
        toast(String.valueOf(mLastKnownLocation.getLongitude()));
        toast("Searching Restaurants...");
        LatLng currentMarkerLocation = mMap.getCameraPosition().target;
        new Handler().postDelayed(() -> {
            double latitude = currentMarkerLocation.latitude;
            double longitude = currentMarkerLocation.longitude;
            presenter.getResponse("restaurant", latitude, longitude);
        }, 2000);
    }
}
