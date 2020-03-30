package com.example.hieudo.phuocnguyenintern.di.module;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context providesContext(){
        return context;
    }
}
