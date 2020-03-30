package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;


import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.Site;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NavigationService {

//    @GET("/sites")
//    Call<List<Site>> getSites();

    @GET("sites?key=UE7EJ*9vN3mwxVyPWNlqvA((")
    Call<Site> getSites();


}
