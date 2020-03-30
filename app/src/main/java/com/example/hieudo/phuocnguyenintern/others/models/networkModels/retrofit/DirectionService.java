package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Direction.DirectionExample;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.googleMap.GoogleMapExample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionService {
    @GET("/maps/api/directions/json?sensor=true&key=AIzaSyBaehEJrZy_frNyJ3jWfWtnjLJ6uI_h0wE")
    Call<DirectionExample> getMapDirection(
            @Query("origin") String origin,
            @Query("destination") String destination
    );
    @GET("/maps/api/directions/json?origin=New Delhi&destination=Jaipur&key=AIzaSyBaehEJrZy_frNyJ3jWfWtnjLJ6uI_h0wE")
    Call<DirectionExample> getDirection();
}
