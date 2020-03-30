package com.example.hieudo.phuocnguyenintern.di.module;

import com.example.hieudo.phuocnguyenintern.di.ActivityScope;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.HomeService;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeFragmentContract;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeFragmentPresenter;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeScreenModule {
    private HomeFragmentContract.View view;

    public HomeScreenModule(HomeFragmentContract.View view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    HomeFragmentPresenter providePresenter(HomeService homeService, HomeFragmentContract.View view){
        return new HomeFragmentPresenter(homeService,view);
    }
}
