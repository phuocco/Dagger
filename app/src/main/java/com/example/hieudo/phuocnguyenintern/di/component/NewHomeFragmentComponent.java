package com.example.hieudo.phuocnguyenintern.di.component;

import android.content.Context;

import com.example.hieudo.phuocnguyenintern.di.module.AdapterModule;
import com.example.hieudo.phuocnguyenintern.di.module.NewHomeFragmentContextModule;
import com.example.hieudo.phuocnguyenintern.di.qualifier.ApplicationContext;
import com.example.hieudo.phuocnguyenintern.di.scope.ActivityScope;
import com.example.hieudo.phuocnguyenintern.ui.home.newHome.NewHomeFragment;

import dagger.Component;

@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface NewHomeFragmentComponent {
    @ApplicationContext
    Context getContext();
    void injectNewHomeFragment(NewHomeFragment newHomeFragment);
}
