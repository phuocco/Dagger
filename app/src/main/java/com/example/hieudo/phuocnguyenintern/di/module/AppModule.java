package com.example.hieudo.phuocnguyenintern.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application app;

    public AppModule(Application app) {
        this.app = app;
    }
    @Singleton
    @Provides public Application provideApplication(){
        return app;
    }
}

