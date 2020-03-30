package com.example.hieudo.phuocnguyenintern.di.component;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.module.ContextModule;
import com.example.hieudo.phuocnguyenintern.di.module.RetrofitModule;
import com.example.hieudo.phuocnguyenintern.di.qualifier.ActivityContext;
import com.example.hieudo.phuocnguyenintern.di.qualifier.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.di.scopes.ApplicationScope;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseApplication;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {
    HomeService getHomeService();

    @ApplicationContext
    Context getContext();

    void injectApplication(BaseApplication baseApplication);
}
