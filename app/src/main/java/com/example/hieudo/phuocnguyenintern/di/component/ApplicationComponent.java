package com.example.hieudo.phuocnguyenintern.di.component;

import android.app.Application;
import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.di.ApplicationScope;
import com.example.hieudo.phuocnguyenintern.di.module.AppModule;
import com.example.hieudo.phuocnguyenintern.di.module.ContextModule;
import com.example.hieudo.phuocnguyenintern.di.module.RetrofitModule;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@ApplicationScope
@Component (modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    HomeService getHomeService();
    @ApplicationContext
    Context getContext();
    void injectApplication(Application application);
}
