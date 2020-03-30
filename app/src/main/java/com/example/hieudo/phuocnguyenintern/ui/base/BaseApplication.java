package com.example.hieudo.phuocnguyenintern.ui.base;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.hieudo.phuocnguyenintern.R;
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
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage((FragmentActivity) getApplicationContext(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
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
