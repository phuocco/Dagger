package com.example.hieudo.phuocnguyenintern.ui.home.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hieudo.phuocnguyenintern.R;
import com.example.hieudo.phuocnguyenintern.others.adapters.NaviAdapter;
import com.example.hieudo.phuocnguyenintern.others.adapters.NavigationAdapter;
import com.example.hieudo.phuocnguyenintern.others.constants.Constants;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.Navigation;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.NavigationSite.SiteItem;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.NavigationService;
import com.example.hieudo.phuocnguyenintern.others.models.networkModels.retrofit.RetrofitClient;
import com.example.hieudo.phuocnguyenintern.ui.auth.authScreen.AuthScreenActivity;
import com.example.hieudo.phuocnguyenintern.ui.base.BaseActivity;
import com.example.hieudo.phuocnguyenintern.ui.home.homeFragment.HomeScreenFragment;
import com.example.hieudo.phuocnguyenintern.ui.mapScreen.MapActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;

public class HomeScreenActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, HomeScreenContract.View {
    private ArrayList<Navigation> navigationArrayList = new ArrayList<>();
    @Nullable
    @BindView(R.id.nav_rvSites)
    RecyclerView recyclerViewNav;
    @BindView(R.id.nav_rlSettings)
    RelativeLayout rlSettings;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    int pos = 0;
    private boolean isSite = false;
    private String social;
    @BindView(R.id.nav_tvTitle)
    TextView tvTitle;
    @BindView(R.id.nav_ivTitle)
    ImageView ivTitle;
    @BindView(R.id.nav_rlTitle)
    RelativeLayout rvTitle;
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private Context mContext;
    private HomeScreenPresenter presenter;
    Retrofit retrofit;
    NavigationService navigationService;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new HomeScreenFragment());
        init();
        initPresenter();
        initRetrofit();
        getToken();
        addRecyclerView();
        presenter.callListSite(retrofit, navigationService);
    }

    private void initRetrofit() {
        retrofit = RetrofitClient.getInstance();
        navigationService = retrofit.create(NavigationService.class);
    }

    private void addRecyclerView() {
        if (recyclerViewNav != null) {
            NavigationAdapter navigationAdapter = new NavigationAdapter(navigationArrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerViewNav.setLayoutManager(layoutManager);
            recyclerViewNav.setAdapter(navigationAdapter);
            navigationAdapter.notifyDataSetChanged();
        }
    }

    private void initPresenter() {

        presenter = new HomeScreenPresenter();
        presenter.setView(this);
    }

    private void init() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        pos = 0;
    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeScreenActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });
    }

    @OnClick({R.id.nav_tvAllSites, R.id.nav_rlSettings, R.id.nav_rlMap, R.id.nav_rlTitle})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_tvAllSites:
                isSite = true;
                drawerLayout.closeDrawer(Gravity.LEFT);
                replaceFragment(HomeScreenFragment.newInstance("sites", "site", true));
                break;
            case R.id.nav_rlSettings:
                rlSettings.setOnClickListener(v -> {
                    if ("facebook".equals(social)) {
                        presenter.signOutFacebook();
                    } else if ("google".equals(social)) {
                        presenter.signOutGoogle();
                    } else {
                        deleteData();
                    }
                });
                break;
            case R.id.nav_rlMap:
                startActivity(new Intent(HomeScreenActivity.this, MapActivity.class));
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_rlTitle:
                deleteData();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void Toast(String content) {
        Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMapActivity() {
        startActivity(new Intent(HomeScreenActivity.this, MapActivity.class));
    }

    @Override
    public void toAuthScreen() {
        Intent intent = new Intent(HomeScreenActivity.this, AuthScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void replaceFragment(String position, String name, boolean isSite) {
        replaceFragment(HomeScreenFragment.newInstance(position, name, isSite));
    }

    @Override
    public void showListSite(List<SiteItem> items) {
        NaviAdapter naviAdapter = new NaviAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        assert recyclerViewNav != null;
        recyclerViewNav.setLayoutManager(layoutManager);
        recyclerViewNav.setAdapter(naviAdapter);
        naviAdapter.notifyDataSetChanged();
        naviAdapter.setNavItemClickListener((position) -> {
            if (pos != position) {
                isSite = false;
                pos = position;
                drawerLayout.closeDrawer(Gravity.LEFT);
                replaceFragment(HomeScreenFragment.newInstance(items.get(position).getApiSiteParameter(), items.get(position).getName(), isSite));
            }
        });

    }

    @Override
    public void deleteData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(HomeScreenActivity.this, AuthScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
