package com.example.hieudo.phuocnguyenintern.di.module;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.qualifier.ActivityContext;
import com.example.hieudo.phuocnguyenintern.di.scope.ActivityScope;
import com.example.hieudo.phuocnguyenintern.ui.home.newHome.NewHomeFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class NewHomeFragmentContextModule {
    private NewHomeFragment newHomeFragment;

    public Context context;

    public NewHomeFragmentContextModule(NewHomeFragment newHomeFragment){
        this.newHomeFragment = newHomeFragment;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext(){
        return context;
    }


}
