package com.example.hieudo.phuocnguyenintern.ui.home.homeScreen;

import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.Site;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.NavigationService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.RetrofitClient;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeScreenPresenter implements HomeScreenContract.Presenter{

    private GoogleApiClient googleApiClient;
    private HomeScreenContract.View view;

    public void setView(HomeScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void callListSite(Retrofit retrofit, NavigationService navigationService) {
        retrofit = RetrofitClient.getInstance();
        navigationService = retrofit.create(NavigationService.class);
        Call<Site> call = navigationService.getSites();
        call.enqueue(new Callback<Site>() {
            @Override
            public void onResponse(Call<Site> call, Response<Site> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    view.showListSite(response.body().getItems());
                }
            }
            @Override
            public void onFailure(Call<Site> call, Throwable t) {
                view.Toast("Network failure");
            }
        });
    }

    @Override
    public void signOutFacebook() {
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);
        FirebaseAuth.getInstance().signOut();
        view.deleteData();
    }

    @Override
    public void signOutGoogle() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(status ->
                view.deleteData()
        );
    }


}
