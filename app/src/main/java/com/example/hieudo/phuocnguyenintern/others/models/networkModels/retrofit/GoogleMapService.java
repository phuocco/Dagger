package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.googleMap.GoogleMapExample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapService {
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyBaehEJrZy_frNyJ3jWfWtnjLJ6uI_h0wE")
    Call<GoogleMapExample> getNearbyPlaces(
            @Query("type") String type,
            @Query("location") String location,
            @Query("radius") int radius);

}
// https://maps.googleapis.com/maps/api/place/nearbysearch/json?sensor=true&key=AIzaSyDmQVdedpq9b9LQ1TCQeMW-oXeYjbNohz8