package com.example.hieudo.phuocnguyenintern.di.component;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.module.AdapterModule;
import com.example.hieudo.phuocnguyenintern.di.module.HomeScreenFragmentMvpModule;
import com.example.hieudo.phuocnguyenintern.di.qualifier.ActivityContext;
import com.example.hieudo.phuocnguyenintern.di.scopes.ActivityScope;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;

import dagger.Component;

@ActivityScope
@Component( modules = {AdapterModule.class, HomeScreenFragmentMvpModule.class})
public interface HomeScreenFragmentComponent {

    @ActivityContext
    Context getContext();
    void injectHomeScreenFragment(HomeScreenFragment homeScreenFragment);
}
