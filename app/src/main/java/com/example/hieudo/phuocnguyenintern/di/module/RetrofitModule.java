package com.example.hieudo.phuocnguyenintern.di.module;

import com.example.hieudo.phuocnguyenintern.di.ApplicationScope;
import com.example.hieudo.phuocnguyenintern.others.constants.API;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @ApplicationScope
    Retrofit getRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public HomeService provideHomeService(Retrofit retrofit){
        return retrofit.create(HomeService.class);
    }
}
