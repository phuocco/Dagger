package com.example.hieudo.phuocnguyenintern.di.component;


import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.ActivityContext;
import com.example.hieudo.phuocnguyenintern.di.ActivityScope;
import com.example.hieudo.phuocnguyenintern.di.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.di.module.ContextModule;
import com.example.hieudo.phuocnguyenintern.di.module.HomeScreenModule;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;

import dagger.Component;

@ActivityScope
@Component(modules = {HomeScreenModule.class, ContextModule.class},
dependencies = ApplicationContext.class)

public interface HomeScreenComponent {
@ActivityContext
    Context getContext();
void injectHomeScreen(HomeScreenFragment homeScreenFragment);
}
