package com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit;

import com.example.hieudo.phuocnguyenintern.others.constants.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .baseUrl(API.URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

}
