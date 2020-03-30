package com.example.hieudo.phuocnguyenintern.di.module;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.qualifier.ActivityContext;
import com.example.hieudo.phuocnguyenintern.di.scopes.ActivityScope;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeScreenContextModule {
    private HomeScreenFragment homeScreenFragment;
    public Context context;

    public HomeScreenContextModule(HomeScreenFragment homeScreenFragment) {
        this.homeScreenFragment = homeScreenFragment;
    }

    @Provides
    @ActivityScope
    public HomeScreenFragment providesHomeScreenFragment(){
        return homeScreenFragment;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext(){
        return context;
    }
}
