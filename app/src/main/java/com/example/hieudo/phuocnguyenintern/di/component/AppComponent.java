package com.example.hieudo.phuocnguyenintern.di.component;

import android.app.Application;

import com.example.hieudo.phuocnguyenintern.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {AppModule.class})
public interface AppComponent {
    Application application();
    
}
