package com.example.hieudo.phuocnguyenintern.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.di.component.ApplicationComponent;
import com.example.hieudo.phuocnguyenintern.di.component.DaggerApplicationComponent;
import com.example.hieudo.phuocnguyenintern.di.module.ContextModule;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.models.localModels.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class BaseApplication extends Application implements GoogleApiClient.OnConnectionFailedListener{
    User user;
    static  BaseApplication instance;
    private GoogleApiClient googleApiClient;
    private SharedPreferences sharedPreferences;

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        applicationComponent.injectApplication(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage((FragmentActivity) getApplicationContext(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    public static BaseApplication get(Activity activity){
        return (BaseApplication) activity.getApplication();
    }
    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    void setUser (User user){
        this.user  =  user;

    }

    User getUser (){
       return this.user;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
