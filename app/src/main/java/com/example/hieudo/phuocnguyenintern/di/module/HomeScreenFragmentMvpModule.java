package com.example.hieudo.phuocnguyenintern.di.module;

import com.example.hieudo.phuocnguyenintern.di.scopes.ActivityScope;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeScreenFragmentMvpModule {
    private final HomeFragmentContract.View view;

    public HomeScreenFragmentMvpModule(HomeFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    HomeFragmentContract.View provideView(){
        return view;
    }
}
