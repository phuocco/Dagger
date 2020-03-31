package com.example.hieudo.phuocnguyenintern.di.module;

import com.example.hieudo.phuocnguyenintern.di.scope.ApplicationScope;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class RetrofitModule {
    @Provides
    @ApplicationScope
    HomeService getHomeService(Retrofit retrofit) {
        return retrofit.create(HomeService.class);
    }

    @Provides
    @ApplicationScope
    Retrofit getRetrofit(OkHttpClient okHttpClient) {
        
    }
}
